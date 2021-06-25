package com.project.restaurantmanager.UI.Employee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.FoodItems;
import com.project.restaurantmanager.Model.EmployeeActivity;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.UI.Employee.dashboard_fragment.tno;

public class menu_fragment extends Fragment {
    private List<FoodItems> items=new ArrayList<>();
    private Map<String,String> orderItemName = new HashMap<>();
    private Map<String,String> orderItemQty = new HashMap<>();
    private ElegantNumberButton numberButton;//Quantity Button
    private TextView price;//Price TextView
    private TextView name;//Name TextView
    private View view;
    private String oid = "0";
    public static Map<String,String> order_table ;
    int k = 0;

    static {
        order_table=new HashMap<>();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.employee_menu_fragment,container,false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(order_table.containsKey(""+ tno))
        {
            oid=order_table.get(""+ tno);
        }

        final DatabaseHandler handler = new DatabaseHandler(DatabaseHandler.FOOD_ITEMLIST_CUSTOMER,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {

                JSONArray jsonArray = new JSONArray(response);

                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    items.add(new FoodItems(object.getString("name"),object.getDouble("price"),object.getString("image")));
                    dynamic_views(i);
                }
            }
            @Override
            public Map<String, String> params() {
                Map<String,String > map = new HashMap<>();
                map.put("rid", EmployeeActivity.sharedPreferencesHandler.getRid()+"");
                return map;
            }
        };
        handler.execute();

        final Button orderButton = view.findViewById(R.id.emp_menu_orderbtn);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gnos = new Gson();
                final String names = gnos.toJson(orderItemName);
                final String qtys = gnos.toJson(orderItemQty);

                if (tno == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Please Select Table");
                    builder.setPositiveButton("Tables", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EmployeeActivity.bottomNavigationView.setSelectedItemId(R.id.Dashboard);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    if(orderItemName.isEmpty()&&orderItemQty.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Please Select At Least One Item");
                        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else {

                        DatabaseHandler handler1 = new DatabaseHandler(DatabaseHandler.INSERT_NEW_ODER_EMPLOYEE, getContext()) {
                            @Override
                            public void writeCode(String response) throws JSONException {
                                JSONObject object = new JSONObject(response);

                                if(object.getString("result").equals("fail"))
                                {
                                    orderItemName=new HashMap<>();
                                    orderItemQty=new HashMap<>();
                                    Toast.makeText(getContext(),"Something Went Wrong!",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    orderItemName=new HashMap<>();
                                    orderItemQty=new HashMap<>();
                                    k=0;
                                    Toast.makeText(getContext(),"Ordered!",Toast.LENGTH_SHORT).show();
                                    order_table.put("" + tno, object.getString("oid"));
                                    oid=object.getString("oid");
                                    Log.d("LUND", "order_table " + order_table);
                                }
                            }
                            @Override
                            public Map<String, String> params() {
                                Map<String, String> map = new HashMap<>();
                                map.put("orderNo", oid);
                                map.put("orderName", names);
                                map.put("orderQty", qtys);
                                map.put("orderTno", "" + tno);
                                map.put("orderEmail", MainActivity.sharedPreferences.getEmail());
                                map.put("orderTax","" + 10.00);
                                map.put("orderDiscount",""+ 0.00);
                                map.put("Rid",EmployeeActivity.sharedPreferencesHandler.getRid()+"");
                                return map;
                            }
                        };
                        handler1.execute();
                    }
                }
            }
        });
    }

    private void dynamic_views(final int i){
        LinearLayout layoutOuter = view.findViewById(R.id.emp_menu_listview);

            final RelativeLayout layoutInner = new RelativeLayout(getContext());
            layoutInner.setId(i);
            final RelativeLayout.LayoutParams layoutForInner = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.MATCH_PARENT, 200);//Layout Parameters for Each Cart Layouts
            layoutForInner.setMargins(10,10,10,10);//Parameter for Each Cart Layout
            layoutInner.setBackgroundResource(R.drawable.border_layout);

            numberButton = new ElegantNumberButton(getContext());
            numberButton.setId(i + 100);
            RelativeLayout.LayoutParams layoutForNumberButton = new RelativeLayout.LayoutParams(200, 70);
            layoutForNumberButton.addRule(RelativeLayout.ALIGN_PARENT_END);
            layoutForNumberButton.rightMargin = 20;
            layoutForNumberButton.addRule(RelativeLayout.CENTER_VERTICAL);
            numberButton.setRange(0, 10);
            /*TextView For Displaying Each Item's Name
                  ID Starts From 200,201....
                 */
            name = new TextView(getContext());
            name.setId(i + 200);
            RelativeLayout.LayoutParams layoutForName = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutForName.leftMargin = 20;
            layoutForName.topMargin = 20;
            name.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            name.setTextSize(1, 18);
            name.setText(items.get(i).getName());
            String id = numberButton.getId() + "";


            price = new TextView(getContext());//Creating TextView For Showing Price
            RelativeLayout.LayoutParams layoutForPrice = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutForPrice.leftMargin = 20;//Layout And Parametes For Price
            layoutForPrice.topMargin = 100;
            price.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            price.setTextSize(1, 12);
            price.setText("₹" + items.get(i).getPrice());
            price.setId(i + 300);

            numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    int id = (int) view.getId() - 100;

                    if(orderItemName.isEmpty())
                    {
                        orderItemName.put(k+"",""+items.get(id).getName());
                        orderItemQty.put(k+"",""+newValue);
                        k++;
                    }
                    else{
                        boolean flag=false;
                        for (int j = 0; j < orderItemName.size(); j++)
                        {
                            if(orderItemName.get(j+"").equals(""+items.get(id).getName())) {
                                orderItemQty.put(j + "", "" + newValue);
                                j=orderItemName.size();
                                flag=false;
                            }
                            else{
                                flag=true;
                            }
                        }
                        if(flag)
                        {
                            orderItemName.put(k + "", "" + items.get(id).getName());
                            orderItemQty.put(k + "", "" + newValue);
                            k++;
                        }
                    }
                }
            });
            layoutInner.addView(name, layoutForName);//Adding Name TextView To Relative Layout(PARENT)
            layoutInner.addView(numberButton, layoutForNumberButton);//Adding Elegant Number Button To Relative Layout
            layoutInner.addView(price, layoutForPrice);//Adding Price TextView To Relative Layout (ALL WITH LAYOUT PARAMETERS)
            layoutOuter.addView(layoutInner, layoutForInner);
        }
}

