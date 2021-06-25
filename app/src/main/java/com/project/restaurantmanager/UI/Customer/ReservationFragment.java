package com.project.restaurantmanager.UI.Customer;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Customer.TableFragmentModules.Recyclerviewadapter_Table;
import com.razorpay.Checkout;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

import static com.project.restaurantmanager.UI.Customer.TableFragmentModules.Recyclerviewadapter_Table.startTimeDB;

public class ReservationFragment extends Fragment {
    List<String> timeList=new ArrayList<>();
    public static String dateDB,GuestsDB,tableNoDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.customer_table_fragment,container,false);
        GuestsDB="1";
        Checkout.preload(getContext());
        final Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE,-1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE,15);


        HorizontalCalendar horizontalCalendar= new HorizontalCalendar.Builder(view,R.id.cust_reservation_calendar)
                .range(startDate,endDate)
                .datesNumberOnScreen(5)
                .build();

        dateDB= String.format("%1$tY-%1$tm-%1$td",horizontalCalendar.getSelectedDate());

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSelected(Calendar date, int position) {
                date.add(Calendar.DAY_OF_MONTH,1);
                dateDB = String.format("%1$tY-%1$tm-%1$td", date);
                Log.d("date", "onDateSelected: "+dateDB);
            }
        });

        for (int i = FoodReservationFragment.mStime; i <= FoodReservationFragment.mEtime; i++) {
            timeList.add(String.format("%02d", i)+":00");
        }

        RecyclerView recyclerView = view.findViewById(R.id.cust_reservation_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Recyclerviewadapter_Table recyclerviewadapter = new Recyclerviewadapter_Table(getContext(),timeList);
        recyclerView.setAdapter(recyclerviewadapter);


        final TextView guests = view.findViewById(R.id.cust_table_text3);
        ElegantNumberButton numberButton = view.findViewById(R.id.cust_reservation_elegant);
        numberButton.setRange(1,10);
        numberButton.setNumber("1");
        guests.setText("1");
        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                guests.setText(newValue+"");
                GuestsDB=newValue+"";
            }
        });

        Log.d("LODO", new Date()+"");
        final Button bookBtn = view.findViewById(R.id.cust_reservation_book_btn);

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                currentTime.add(Calendar.HOUR_OF_DAY,0);

                if(dateDB==null){
                    Toast.makeText(getContext(),"Please Correct Details!",Toast.LENGTH_SHORT).show();
                }
                else if(currentTime.get(Calendar.HOUR_OF_DAY) >= Integer.parseInt(startTimeDB.substring(0, 2))&&
                        Integer.parseInt(dateDB.substring(8,10))==currentTime.get(Calendar.DAY_OF_MONTH)) {
                    Toast.makeText(getContext(),"Time Reversal Is Not Possible",Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.INSERT_RESERVATION_CHECK_CUSTOMER, getContext()) {
                        @Override
                        public void writeCode(String response) throws JSONException {
                            if (response.equals("error") || response == null) {
                                Toast.makeText(getContext(), "Table Not Availalbe", Toast.LENGTH_SHORT).show();
                                dateDB = null;
                            } else {
                                tableNoDB = response;
                                MainActivity.paymentMethod = "res";
                                startPayment();
                            }
                        }
                        @Override
                        public Map<String, String> params() {
                            Map<String, String> map = new HashMap<>();
                            map.put("StartTime", startTimeDB);
                            map.put("EndTime", Recyclerviewadapter_Table.endTimeDB);
                            map.put("Date", dateDB);
                            map.put("Guest", GuestsDB);
                            map.put("Rid",FoodReservationFragment.mSelectedRid+"");
                            return map;
                        }
                    };
                    databaseHandler.execute();
                }
            }
        });
        return view;
    }

    public void startPayment() {
        DatabaseHandler databaseHandlerForRes = new DatabaseHandler(DatabaseHandler.INSERT_NEW_RESERVATION_CUSTOMER, getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {
                Log.d("ffff", "writeCode: "+response);
                Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public Map<String, String> params() {
                Map<String, String> map = new HashMap<>();
                map.put("StartTime", Recyclerviewadapter_Table.startTimeDB);
                map.put("EndTime", Recyclerviewadapter_Table.endTimeDB);
                map.put("Date", dateDB);
                map.put("Guest", GuestsDB);
                map.put("Email", MainActivity.sharedPreferences.getEmail());
                map.put("Tno", ReservationFragment.tableNoDB);
                map.put("Rid", FoodReservationFragment.mSelectedRid+"");
                return map;
            }
        };
        databaseHandlerForRes.execute();
    }

}
