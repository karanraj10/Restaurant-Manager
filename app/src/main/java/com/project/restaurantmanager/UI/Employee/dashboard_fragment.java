package com.project.restaurantmanager.UI.Employee;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.EmployeeActivity;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Customer.FoodReservationFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.SEND_NOTIFICATION;
import static com.project.restaurantmanager.Controller.DatabaseHandler.UPDATE_FINISH_ORDER_EMPLOYEE;
import static com.project.restaurantmanager.UI.Employee.menu_fragment.order_table;

public class dashboard_fragment extends Fragment {
    private List<String> tablenumbers = new ArrayList<>();
    public static int tno ;
    private boolean visibilityFlag;
     View view;
     TextView name;
     LinearLayout items;
     Button finish;
     LinearLayout layoutOuter;
     ColorStateList setTextColor;
     List<Boolean> buttonStatus;
     
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.employee_dashboard_fragment,container,false);
        tno = 0;
        layoutOuter = view.findViewById(R.id.emp_dash_listview);

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.emp_dash_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layoutOuter.removeAllViews();
                onResume();
                refreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Subh Sharuaat

        layoutOuter.removeAllViews();
        DatabaseHandler handler = new DatabaseHandler(DatabaseHandler.TABLE_LIST_EMPLOYEE_DASHBOARD,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException, InterruptedException {
                JSONArray jsonArray =new JSONArray(response);
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    tablenumbers.add(object.getString("tno"));
                    if(object.getString("ono").equals("0"))
                    {
                        dynamic_views(i);
                        order_table.remove(object.getString("tno"));
                    }
                    else
                    {
                        order_table.put(object.getString("tno"),object.getString("ono"));
                        dynamic_views(i);
                    }
                }
            }
            @Override
            public Map<String, String> params() {
                Map<String,String > map = new HashMap<>();
                map.put("Rid", EmployeeActivity.sharedPreferencesHandler.getRid()+"");
                return map;
            }
        };
        handler.execute();

    }

    private void dynamic_views(final int i){
        visibilityFlag=false;

        final RelativeLayout layoutInner = new RelativeLayout(getContext());
        layoutInner.setId(i);
        final RelativeLayout.LayoutParams layoutForInner = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//Layout Parameters for Each Cart Layouts
        layoutForInner.setMargins(10,10,10,10);//Parameter for Each Cart Layout
        layoutInner.setBackgroundResource(R.drawable.border_layout);
        layoutInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id =  v.getId();
                tno = Integer.parseInt(tablenumbers.get(id));
                EmployeeActivity.bottomNavigationView.setSelectedItemId(R.id.Menu);
                Log.d("LUND", ""+tno);
            }
        });
     
        name = new TextView(getContext());
        name.setId(i + 200);
        RelativeLayout.LayoutParams layoutForName = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForName.leftMargin = 20;
        layoutForName.topMargin = 40;
        name.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        name.setTextSize(1, 18);
        name.setText("Table Number : "+tablenumbers.get(i));
        setTextColor = name.getTextColors();


        items = new LinearLayout(getContext());//Creating TextView For Showing items
        items.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams layoutForitems = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForitems.leftMargin = 20;//Layout And Parametes For items
        layoutForitems.topMargin = 175;
        layoutForitems.bottomMargin=50;
        //items.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        items.setId(i + 300);
        //items.setLineSpacing((float)1.0,(float)1.2);


        finish = new Button(getContext());
        finish.setId(i+400);
        RelativeLayout.LayoutParams layoutForfinish = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForfinish.addRule(RelativeLayout.ALIGN_PARENT_END);//Layout And Parametes For finish
        layoutForfinish.setMargins(30,30,30,30);
        finish.setBackgroundResource(R.drawable.button_shape);
        finish.setText("FINISH");
        finish.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        finish.setTextColor(getResources().getColor(R.color.colorWhite));
        finish.setVisibility(View.INVISIBLE);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") final int id = v.getId()-400;

                if(buttonStatus.contains(false))
                {
                    Toast.makeText(getContext(),"Please Serve All Food",Toast.LENGTH_SHORT).show();
                }
                else {

                    TextView nam = view.findViewById(id + 200);
                    LinearLayout item = view.findViewById(id + 300);

                    DatabaseHandler handler2 = new DatabaseHandler(UPDATE_FINISH_ORDER_EMPLOYEE, getContext()) {
                        @Override
                        public void writeCode(String response) throws Exception {
                            Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public Map<String, String> params() {
                            Map<String, String> map = new HashMap<>();
                            map.put("tno", tablenumbers.get(id) + "");
                            return map;
                        }
                    };
                    handler2.execute();

                    item.removeAllViews();

                    order_table.remove(tablenumbers.get(id));

                    layoutInner.setBackgroundColor(Color.WHITE);
                    nam.setTextColor(setTextColor);
                    v.setVisibility(View.INVISIBLE);
                }
            }

        });


        if(order_table.containsKey(tablenumbers.get(i)))
        {
            final int id = items.getId();
            final String oid = order_table.get(tablenumbers.get(i));

          DatabaseHandler handler = new DatabaseHandler(DatabaseHandler.ORDER_ITEMLIST_EMPLOYEE_DASHBOARD,getContext()) {
                @Override
                public void writeCode(String response) throws JSONException {
                    JSONArray array = new JSONArray(response);
                    LinearLayout items = view.findViewById(id);

                   buttonStatus = new ArrayList<>();

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);

                        buttonStatus.add(i,false);

                        ToggleButton toggleButton = new ToggleButton(getContext());
                        RelativeLayout.LayoutParams layoutFortoggle = new RelativeLayout.LayoutParams
                                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        layoutFortoggle.bottomMargin=20;

                        toggleButton.setTag(i);
                        toggleButton.setBackgroundResource(R.drawable.dashboard_togglebutton);

                        toggleButton.setText("   "+object.getString("qty")+" × "+object.getString("name")+"  ");
                        toggleButton.setTextOn("   "+object.getString("name")+"   ");

                        if(object.getString("status").equals("1"))
                        {
                            toggleButton.setChecked(true);
                            toggleButton.setTextColor(Color.WHITE);
                            toggleButton.setEnabled(false);
                        }

                        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                                if(isChecked)
                                {
                                    buttonStatus.set((Integer) buttonView.getTag(),true);
                                    buttonView.setTextColor(Color.WHITE);
                                    DatabaseHandler databaseHandler = new DatabaseHandler(UPDATE_FOODITEM_SERVED_EMPLOYEE,getContext()) {
                                        @Override
                                        public void writeCode(String response) throws Exception {

                                        }
                                        @Override
                                        public Map<String, String> params() {
                                            Map<String,String> map = new HashMap<>();
                                            map.put("ono",buttonView.getTag()+"");
                                            map.put("name",buttonView.getText().toString().trim());
                                            map.put("Rid", FoodReservationFragment.mSelectedRid+"");
                                            return map;
                                        }
                                    };
                                    databaseHandler.execute();

                                    buttonView.setEnabled(false);
                                }
                            }
                        });
                        items.addView(toggleButton,layoutFortoggle);
                    }
                }
                @Override
                public Map<String, String> params() {
                    Map<String,String > map = new HashMap<>();
                    map.put("ono",oid+"");
                    return map;
                }
            };
            handler.execute();


            finish.setVisibility(View.VISIBLE);
            layoutInner.setBackgroundResource(R.drawable.table_layout);
            finish.setBackgroundResource(R.drawable.table_button);
            finish.setTextColor(Color.WHITE);
            name.setTextColor(Color.WHITE);

        }

        layoutInner.addView(name, layoutForName);//Adding Name TextView To Relative Layout(PARENT)
        layoutInner.addView(items, layoutForitems);
        layoutInner.addView(finish, layoutForfinish);//Adding items TextView To Relative Layout (ALL WITH LAYOUT PARAMETERS)
        layoutOuter.addView(layoutInner, layoutForInner);

    }
}
