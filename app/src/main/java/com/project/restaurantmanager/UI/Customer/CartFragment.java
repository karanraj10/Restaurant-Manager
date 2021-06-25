package com.project.restaurantmanager.UI.Customer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.UI.Customer.FoodItemListFragment.buttonflag;
import static com.project.restaurantmanager.UI.Customer.FoodItemListFragment.nameForCart;
import static com.project.restaurantmanager.UI.Customer.FoodItemListFragment.priceForCart;


public class CartFragment extends Fragment {
    private View viewForAll;//Baap Of All
    private ElegantNumberButton numberButton;//Quantity Button
    private TextView price;//Price TextView
    private TextView name;//Name TextView
    private Button placeOrder;//Order Button

    private ListView cartListView;
    public  static ImageView cartEmptyimageView;
    public static TextView cartEmptytextView;

    private int i;//For Main Foor Loop

    private Double total = 0.00, grandTotal;//All Bill Item Variables
    public static final Double tax = 10.00, discount = 0.00;
    public static Map<String, String> map = new HashMap<>();//One Variable Per One Session of App For Storing Quantity Of Particular Item
    private TextView itemTotalTextView, taxTextView, discountTextView, grandTotalTextView; //All Bill TextViews
    public static Map<String, String> mapDB = new HashMap<>();
    private  RelativeLayout relativeLayout;
    public static boolean clearCart;
    public static List<String> quantity = new ArrayList<>();//For Storing All Cart Item's Quantity

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewForAll = inflater.inflate(R.layout.customer_cart_fragment, container, false);

        cartListView = viewForAll.findViewById(R.id.cart_card_listView);
        cartListView.setDividerHeight(20);

        final CustomAdapter customAdapter = new CustomAdapter();

        cartListView.setAdapter(customAdapter);

        if(nameForCart.isEmpty())
        {

        }

        itemTotalTextView = viewForAll.findViewById(R.id.cust_cart_itemtotal);
        taxTextView = viewForAll.findViewById(R.id.cust_cart_tax);
        discountTextView = viewForAll.findViewById(R.id.cust_cart_discount);
        grandTotalTextView = viewForAll.findViewById(R.id.cust_cart_total);


        for(int i=0;i<priceForCart.size();i++)
        {
            total  += priceForCart.get(i);
        }

        itemTotalTextView.setText(total+"");
        taxTextView.setText(tax+"");
        discountTextView.setText(discount+"");

        grandTotal=total - (total / 100 * discount);
        grandTotal= grandTotal + (grandTotal / 100 * tax);
        grandTotalTextView.setText(grandTotal+"");

        placeOrder = viewForAll.findViewById(R.id.cust_cart_order_button);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearCart=true;
                Gson gson = new Gson();

                final String quantityDB = gson.toJson(quantity);
                final String nameDB = gson.toJson(nameForCart);


                nameForCart.clear();
                priceForCart.clear();
                quantity.clear();
                buttonflag.clear();

                customAdapter.notifyDataSetChanged();
                relativeLayout.setVisibility(View.INVISIBLE);

                mapDB.put("email", MainActivity.sharedPreferences.getEmail());
                mapDB.put("Rid",FoodReservationFragment.mSelectedRid+"");
                mapDB.put("amount", total + "");
                mapDB.put("tax", tax + "");
                mapDB.put("discount", discount + "");
                mapDB.put("totalamount", grandTotal + "");
                mapDB.put("itemname", nameDB);
                mapDB.put("quantity", quantityDB);

                MainActivity.paymentMethod="cart";
                startPayment();
            }
        });

        return viewForAll;
    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return nameForCart.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.listview_cart_layout,parent,false);

            if(!nameForCart.isEmpty()){
                cartEmptyimageView = viewForAll.findViewById(R.id.cart_card_empty_image);
                cartEmptyimageView.setVisibility(View.GONE);
                cartEmptytextView= viewForAll.findViewById(R.id.cart_card_empty_text);
                cartEmptytextView.setVisibility(View.GONE);
                relativeLayout = viewForAll.findViewById(R.id.cust_cart_order_relativeLayout);
                relativeLayout.setVisibility(View.VISIBLE);
            }

            final TextView nameTextView = convertView.findViewById(R.id.customer_cart_listview_nameLabel);
            final TextView priceTextView = convertView.findViewById(R.id.customer_cart_listview_priceLabel);
            final ElegantNumberButton quantityButton = convertView.findViewById(R.id.customer_cart_listview_quantityButton);
            quantityButton.setRange(0,10);

            quantityButton.setId(position);
            priceTextView.setId(position+1000);

            try
            {
               quantityButton.setNumber(quantity.get(position));
            }
            catch (IndexOutOfBoundsException ob)
            {
                quantityButton.setNumber("1");
                quantity.add(quantityButton.getId(),"1");
            }



            final View finalConvertView = convertView;
            quantityButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                    if(newValue == 0)
                    {
                        total = total - priceForCart.get(view.getId() * oldValue);
                        nameForCart.remove(view.getId());
                        priceForCart.remove(view.getId());
                        quantity.remove(view.getId());
                        buttonflag.set(view.getId(),false);

                        notifyDataSetChanged();

                        if(nameForCart.isEmpty()){
                            relativeLayout.setVisibility(View.INVISIBLE);
                            cartEmptyimageView.setVisibility(View.VISIBLE);
                            cartEmptytextView.setVisibility(View.VISIBLE);
                            total=0.0;
                            grandTotal=0.0;
                            clearCart=true;
                        }

                        FoodItemListFragment.i=0;
                    }
                    else if(newValue>oldValue)
                    {
                        quantity.set(view.getId(),newValue+"");

                        total = (total - (priceForCart.get(view.getId()) * oldValue));
                        total = (total + (priceForCart.get(view.getId()) * newValue));
                        @SuppressLint("ResourceType") TextView price = finalConvertView.findViewById(view.getId()+1000);
                        price.setText(priceForCart.get(view.getId()) * newValue+"");

                    }
                    else if(oldValue>newValue)
                    {
                        quantity.set(view.getId(),newValue+"");

                        total = (total - (priceForCart.get(view.getId()) * oldValue));
                        total = (total + (priceForCart.get(view.getId()) * newValue));
                        @SuppressLint("ResourceType") TextView price = finalConvertView.findViewById(view.getId()+1000);
                        price.setText(priceForCart.get(view.getId()) * newValue+"");
                    }

                    itemTotalTextView.setText(total+"");
                    taxTextView.setText(tax+"");
                    discountTextView.setText(discount+"");

                    grandTotal=total - (total / 100 * discount);
                    grandTotal= grandTotal + (grandTotal / 100 * tax);

                    grandTotalTextView.setText(grandTotal+"");
                }
            });

            nameTextView.setText(nameForCart.get(position));
            priceTextView.setText(priceForCart.get(position)+"");

            return convertView;
        }
    }

    public void startPayment() {
        final DatabaseHandler databaseHandlerForCart = new DatabaseHandler(DatabaseHandler.INSERT_NEW_ORDER_CUSTOMER, getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {
                Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public Map<String, String> params() {
                return mapDB;
            }
        };
        databaseHandlerForCart.execute();
    }
}

