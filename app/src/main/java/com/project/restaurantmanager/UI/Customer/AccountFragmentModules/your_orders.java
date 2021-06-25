package com.project.restaurantmanager.UI.Customer.AccountFragmentModules;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.Orders;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class your_orders extends Fragment {
    List<Orders> orders =new ArrayList<>();
    FrameLayout empty_layout ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_accountyourorders_fragment,container,false);

        empty_layout = view.findViewById(R.id.empty_orders);

        final ListView listView = view.findViewById(R.id.cust_acc_yourorder_listView);
        listView.setDividerHeight(20);
        DatabaseHandler handler = new DatabaseHandler(DatabaseHandler.ORDER_LIST_CUSTOMER,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        JSONArray array1 = object.getJSONArray("items");

                        String[] item = new String[array1.length()];
                        String[] qty = new String[array1.length()];

                        for (int k = 0; k < array1.length(); k++) {
                            JSONObject items = array1.getJSONObject(k);
                            item[k] = items.getString("item");
                            qty[k] = items.getString("qty");
                        }
                        orders.add(new Orders(object.getString("date"), object.getString("totalamount"), item, qty, object.getString("rname")));

                        empty_layout.setVisibility(View.INVISIBLE);
                        CustomAdapter arrayAdapter = new CustomAdapter();
                        listView.setAdapter(arrayAdapter);
                    }
                }
                catch (Exception e)
                {
                    empty_layout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public Map<String, String> params() {
                Map<String,String> mapDB= new HashMap<>();
                mapDB.put("username", MainActivity.sharedPreferences.getUsername());
                return mapDB;
            }
        };
        handler.execute();
        return view;
    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return orders.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View view = getLayoutInflater().inflate(R.layout.listview_yourorders_layout,parent,false);

            TextView amount = view.findViewById(R.id.listview_yourorders_amount);
            TextView date = view.findViewById(R.id.listview_yourorders_date);
            TextView items = view.findViewById(R.id.listview_yourorders_items);
            TextView rname = view.findViewById(R.id.listview_yourorders_restaurant);
            items.setText("Items : ");

            amount.setText("₹ "+(int)Double.parseDouble(orders.get(position).getAmount()));
            date.setText("Date : "+orders.get(position).getDate());
            rname.setText(orders.get(position).getRestaurant());

            String[] strings = orders.get(position).getItems();
            String[] strings1 = orders.get(position).getQuantity();

              for(int i=0;i<strings.length;i++)
              {
                  if(i==strings.length-1)
                  {
                      items.append(strings1[i]+"x "+strings[i]+" . ");
                  }
                  else {
                      items.append(strings1[i]+"x "+strings[i]+" , ");
                  }
              }

            return view;
        }
    }
}
