package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Controller.FoodSQLite;
import com.project.restaurantmanager.Data.FoodItems;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.DELETE_FOOD_ITEM_ADMIN;
import static com.project.restaurantmanager.Controller.DatabaseHandler.FOOD_ITEMLIST_CUSTOMER;

public class manage_food extends Fragment {
    private View view;
    private List<FoodItems> foodItemsList = new ArrayList<>();
    private ListView foodListView;
    CustomAdapter customAdapter = new CustomAdapter();
    FoodSQLite db;
    int rid = Integer.parseInt(AdminActivity.sharedPreferencesHandler.getId());
    public static Bitmap bitmapDB;
    public static String nameDB, priceDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_manage_food, container, false);

        foodListView = view.findViewById(R.id.admin_food_listView);

        db = new FoodSQLite(getContext());
        foodItemsList = db.getFoodData(rid);

        foodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String imagebase64 = foodItemsList.get(position).getImage();
                byte[] imagedecoded = Base64.decode(imagebase64, Base64.DEFAULT);
                bitmapDB = BitmapFactory.decodeByteArray(imagedecoded, 0, imagedecoded.length);
                nameDB = foodItemsList.get(position).getName();
                priceDB = foodItemsList.get(position).getPrice().toString();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container, new modify_item_fragment(), null).commit();
                return true;
            }
        });

        View footerview = getLayoutInflater().inflate(R.layout.footerview_manage_food,null);

        footerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container, new add_food_fragment(), null).commit();
            }
        });

        foodListView.setAdapter(customAdapter);
        getFoodItems(customAdapter);

        foodListView.addFooterView(footerview);

        return view;
    }

    void getFoodItems(final CustomAdapter customAdapter)
    {
        DatabaseHandler handler = new DatabaseHandler(FOOD_ITEMLIST_CUSTOMER, getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {
                foodItemsList.clear();
                JSONArray jsonArray = new JSONArray(response);


                SQLiteDatabase database = db.getWritableDatabase();
                database.delete("foods","rid=?",new String[] {AdminActivity.sharedPreferencesHandler.getId()});

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    db.insertFoodData(object.getInt("ino"),object.getString("name"),object.getInt("price"),
                            object.getString("image"),rid);
                }
                foodItemsList = db.getFoodData(rid);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public Map<String, String> params() {
                Map<String, String> map = new HashMap<>();
                map.put("rid", AdminActivity.sharedPreferencesHandler.getId());
                return map;
            }
        };
        handler.execute();
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return foodItemsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.listview_admin_manage_orders, parent, false);

            ImageView image = convertView.findViewById(R.id.admin_manage_food_image);
            TextView name = convertView.findViewById(R.id.admin_manage_food_name);
            TextView price = convertView.findViewById(R.id.admin_manage_food_price);
            ImageView delete = convertView.findViewById(R.id.admin_manage_food_deletebutton);
            delete.setId(position);

            String imagebase64 = foodItemsList.get(position).getImage();
            byte[] imagedecoded = Base64.decode(imagebase64, Base64.DEFAULT);
            Bitmap decodedimage = BitmapFactory.decodeByteArray(imagedecoded, 0, imagedecoded.length);

            image.setImageBitmap(decodedimage);
            name.setText(foodItemsList.get(position).getName());
            price.setText((int) Math.round(foodItemsList.get(position).getPrice()) + " ₹");


            delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        @SuppressLint("ResourceType") int id = v.getId();
                        final String namefordelete = foodItemsList.get(id).getName();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("You really want to remove '" + namefordelete + "'?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHandler databaseHandler = new DatabaseHandler(DELETE_FOOD_ITEM_ADMIN, getContext()) {
                                    @Override
                                    public void writeCode(String response) throws Exception {
                                        Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();
                                        getFoodItems(customAdapter);
                                    }

                                    @Override
                                    public Map<String, String> params() {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("name", namefordelete);
                                        return map;
                                    }
                                };
                                databaseHandler.execute();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });


            return convertView;

        }
    }
}
