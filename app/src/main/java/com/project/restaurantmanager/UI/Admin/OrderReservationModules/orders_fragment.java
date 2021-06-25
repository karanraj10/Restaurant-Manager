package com.project.restaurantmanager.UI.Admin.OrderReservationModules;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.Orders;
import com.project.restaurantmanager.Data.Restaurant;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class orders_fragment extends Fragment {
    List<Orders> orders =new ArrayList<>();
    View view;
    ListView orderList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_allorders_fragment,null);

        orderList = view.findViewById(R.id.admin_order_list);

        final CustomAdapter customAdapter = new CustomAdapter();

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.admin_reservation_search);

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

        DatabaseHandler handler = new DatabaseHandler(DatabaseHandler.ORDER_LIST_ADMIN,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {
                JSONArray array = new JSONArray(response);
                for(int i=0;i<array.length();i++) {

                    JSONObject object = array.getJSONObject(i);
                    JSONArray array1 = object.getJSONArray("items");

                    String[] item = new String[array1.length()];
                    String[] qty = new String[array1.length()];

                    for(int k=0;k<array1.length();k++) {
                        JSONObject items = array1.getJSONObject(k);
                        item[k] = items.getString("item");
                        qty[k] = items.getString("qty");
                    }
                    orders.add(new Orders(object.getString("date"),object.getString("totalamount"),item,qty));
                }
                customAdapter.filter("");
                orderList.setAdapter(customAdapter);
            }
            @Override
            public Map<String, String> params() {
                Map<String,String> map = new HashMap<>();
                map.put("Rid", AdminActivity.sharedPreferencesHandler.getId());
                return map;
            }
        };
        handler.execute();

        return view;
    }
    class CustomAdapter extends BaseAdapter
    {
        List<Orders> list = new ArrayList<>();
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listview_admin_allorder_layout,null);

            TextView amount =convertView.findViewById(R.id.listview_allorders_amount);
            TextView date = convertView.findViewById(R.id.listview_allorder_date);
            TextView items = convertView.findViewById(R.id.listview_allorders_items);


            amount.setText("₹ "+(int)Double.parseDouble(list.get(position).getAmount()));
            date.setText("Date : "+list.get(position).getDate());

            String[] strings = list.get(position).getItems();
            String[] strings1 = list.get(position).getQuantity();

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

            return convertView;
        }

        public void filter(String charText){
            list.clear();
            if (charText.length()==0){
                list.addAll(orders);
            }
            else {
                for (Orders model : orders){
                    if (model.getDate().contains(charText)){
                        list.add(model);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }
}
