package com.project.restaurantmanager.UI.Customer;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Controller.FoodSQLite;
import com.project.restaurantmanager.Data.FoodItems;
import com.project.restaurantmanager.Model.CustomerActivity;
import com.project.restaurantmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FoodItemListFragment extends Fragment {
    public List<FoodItems> foodItemsList;
    View view;
    public static List<String> nameForCart = new ArrayList<>();
    public static List<Double> priceForCart = new ArrayList<>();
    public static List<Boolean> buttonflag = new ArrayList<>();
    public static int i = 0;
    CustomAdapter customAdapter;
    FoodSQLite db;
    ListView mFoodListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.customer_food_fragment, container, false);

        db = new FoodSQLite(getContext());

        mFoodListView= view.findViewById(R.id.cust_recyclerview_food_fragment);
        mFoodListView.setDividerHeight(20);
        mFoodListView.setTextFilterEnabled(true);

        customAdapter = new CustomAdapter();

        foodItemsList = db.getFoodData(FoodReservationFragment.mSelectedRid);

        customAdapter.filter("");
        mFoodListView.setAdapter(customAdapter);


        DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.FOOD_ITEMLIST_CUSTOMER, getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {
                JSONArray jsonArray = new JSONArray(response);

                SQLiteDatabase database = db.getWritableDatabase();
                String whereClause = "rid=?";
                String[] whereArgs = {FoodReservationFragment.mSelectedRid+""};
                database.delete("foods",whereClause,whereArgs);

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    db.insertFoodData(jsonObject.getInt("ino"),jsonObject.getString("name"),jsonObject.getInt("price"),
                            jsonObject.getString("image"), FoodReservationFragment.mSelectedRid);
                }

                foodItemsList = db.getFoodData(FoodReservationFragment.mSelectedRid);
                customAdapter.filter("");
                customAdapter.notifyDataSetChanged();
            }
            @Override
            public Map<String, String> params() {
                Map<String, String> map = new HashMap<>();
                map.put("rid", FoodReservationFragment.mSelectedRid + "");
                return map;
            }
        };

        databaseHandler.execute();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onResume() {
        super.onResume();

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.cust_listview_search);
        searchView.setQueryHint("Search Food");
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.filter(newText);
                return true;
            }
        });


    }

    public class CustomAdapter extends BaseAdapter {
        private FoodItems mFoodItems;
        private List<FoodItems> list  =new ArrayList<>();

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint({"InflateParams", "ViewHolder"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

                convertView = getLayoutInflater().inflate(R.layout.food_items_holder_layout, null);
                mFoodItems = list.get(position);

                final Button mAddButton = convertView.findViewById(R.id.food_item_add_button);
                final ImageView mImageView = convertView.findViewById(R.id.food_item_food_image);
                TextView mNameTextView = convertView.findViewById(R.id.food_item_food_name);
                TextView mPriceTextView = convertView.findViewById(R.id.food_item_food_price);

                mAddButton.setId(position);

                try
                {
                    if(buttonflag.get(position))
                    {
                        mAddButton.setText("ADDED");
                    }
                    else
                    {
                        mAddButton.setText("ADD");
                        mAddButton.setEnabled(true);
                    }
                }
                catch (IndexOutOfBoundsException ob)
                {
                    buttonflag.add(position, false);
                    mAddButton.setText("ADD");
                    mAddButton.setEnabled(true);
                }

                mImageView.setImageBitmap(getImage(mFoodItems));
                mNameTextView.setText(mFoodItems.getName());
                mPriceTextView.setText((int)Math.round(mFoodItems.getPrice())+" ₹");

                mAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = v.getId();

                        if(!buttonflag.get(position))
                        {
                            if (i > 9)
                            {
                                Toast.makeText(getContext(), "You Have Reached Your Limits", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                mAddButton.setEnabled(false);

                                buttonflag.set(position,true);

                                FoodItems foodItems = list.get(position);

                                nameForCart.add(i, foodItems.getName());

                                priceForCart.add(i, foodItems.getPrice());

                                Log.d("tag", "onClick: "+priceForCart.size());

                                mAddButton.setText("ADDED");

                                createSnakebar(mImageView);

                                i++;
                            }
                        }
                        else if (buttonflag.get(position))
                        {
                            mAddButton.setText("ADDED");
                            Toast.makeText(getContext(), "Item Already Exist In Your Cart!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return convertView;

        }

        public void filter(String charText){
            charText = charText.toLowerCase(Locale.getDefault());
            list.clear();
            if (charText.length()==0){
                list.addAll(foodItemsList);
            }
            else {
                for (FoodItems model : foodItemsList){
                    if (model.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)){
                        list.add(model);
                    }
                }
            }
            notifyDataSetChanged();
        }



        private Bitmap getImage(FoodItems ob) {
            String imagebase64 = ob.getImage();
            byte[] imagedecoded = Base64.decode(imagebase64, Base64.DEFAULT);
            Bitmap decodedimage = BitmapFactory.decodeByteArray(imagedecoded, 0, imagedecoded.length);

            return decodedimage;
        }

        private void createSnakebar(View view) {
            Snackbar snackbar = Snackbar
                    .make(view, " Item Added To Cart", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Go To Cart", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomerActivity.bottomNavigationView.setSelectedItemId(R.id.Cart);
                        }
                    });
            snackbar.setActionTextColor(Color.WHITE);
            snackbar.show();
        }

    }
}

