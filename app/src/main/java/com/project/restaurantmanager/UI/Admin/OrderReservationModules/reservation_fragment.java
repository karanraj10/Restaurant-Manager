package com.project.restaurantmanager.UI.Admin.OrderReservationModules;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.Orders;
import com.project.restaurantmanager.Data.Reservation;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import static com.project.restaurantmanager.Controller.DatabaseHandler.RESERVATION_LIST_ADMIN;

public class reservation_fragment extends Fragment {
    View view;
    List<Reservation> reservations = new ArrayList<>();
    PopupMenu popup;
    CustomAdapter customAdapter;



    private List<Reservation> reservationArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_allreservtion_fragment, container, false);
;

        final ImageButton filter = view.findViewById(R.id.admin_reservation_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //registering popup with OnMenuItemClickListene
                popup.show();//showing popup menu
            }
        });

        SearchView searchView = view.findViewById(R.id.admin_reservation_search);

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

        popup = new PopupMenu(getContext(),filter);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.admin_reservation_filter_menu, popup.getMenu());

        final ListView listView = view.findViewById(R.id.admin_reservation_linearlayout);
        customAdapter = new CustomAdapter();

        DatabaseHandler handler = new DatabaseHandler(RESERVATION_LIST_ADMIN, getContext()) {
            @Override
            public void writeCode(String response) throws Exception {

                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    reservationArrayList.add(new Reservation(object.getString("rdate"), object.getString("starttime"),
                            object.getString("endtime"), object.getString("guest"), object.getString("deposit"),
                            object.getString("tno")));
                }

                reservations.addAll(reservationArrayList);
                customAdapter.filter("");
                listView.setAdapter(customAdapter);

                popup.getMenu().getItem(1).setChecked(true);

                @SuppressLint("SimpleDateFormat")
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        try {
                            Date today = new Date();
                            reservations.clear();
                            switch (item.getItemId()) {
                                case R.id.older:
                                   for (int i = 0; i < reservationArrayList.size(); i++) {
                                        Date date = sdf.parse(reservationArrayList.get(i).getRdate());
                                        if (today.after(date) &&  today.getDate()!=date.getDate()) {
                                            reservations.add(reservationArrayList.get(i));
                                        }
                                    }
                                    customAdapter.notifyDataSetChanged();
                                    break;
                                case R.id.today:
                                    for (int i = 0; i < reservationArrayList.size(); i++) {
                                        Date date = sdf.parse(reservationArrayList.get(i).getRdate());
                                        Log.d("reservation", "onMenuItemClick:"+today+" "+date);
                                        if (today.getDate()==date.getDate()) {
                                            reservations.add(reservationArrayList.get(i));
                                        }
                                    }
                                    customAdapter.notifyDataSetChanged();
                                    break;
                                case R.id.newer:

                                    for (int i = 0; i < reservationArrayList.size(); i++) {
                                        Date date = sdf.parse(reservationArrayList.get(i).getRdate());
                                        if (today.before(date)) {
                                            reservations.add(reservationArrayList.get(i));
                                        }
                                    }
                                    customAdapter.notifyDataSetChanged();
                                    break;

                            }
                        } catch (Exception e) {

                        }
                        return true;
                    }
                });
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

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return reservations.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "SetTextI18n"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listview_yourreservation_layout, parent, false);

            TextView resdateV = convertView.findViewById(R.id.listview_yourreservation_date);
            TextView amountV = convertView.findViewById(R.id.listview_yourreservation_amount);
            TextView starttimeV = convertView.findViewById(R.id.listview_yourreservation_starttime);
            TextView endtimeV = convertView.findViewById(R.id.listview_yourreservation_endtime);
            TextView tabnoV = convertView.findViewById(R.id.listview_yourreservation_tno);
            TextView guestV = convertView.findViewById(R.id.listview_yourreservation_guests);

            resdateV.setText("Reservation Date: " + reservations.get(position).getRdate());
            amountV.setText("Amount: ₹ " + (int) Double.parseDouble(reservations.get(position).getDeposit()));
            starttimeV.setText("Start Time: " + reservations.get(position).getStarttime());
            endtimeV.setText("End Time: " + reservations.get(position).getEndtime());
            tabnoV.setText("Table No: " + reservations.get(position).getTno());
            guestV.setText("Guests: " + reservations.get(position).getGuests());

            return convertView;
        }

        public void filter(String charText){
            reservations.clear();
            if (charText.length()==0){
                reservations.addAll(reservationArrayList);
            }
            else {
                for (Reservation model : reservationArrayList){
                    if (model.getRdate().contains(charText)){
                        reservations.add(model);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
