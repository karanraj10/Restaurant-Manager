package com.project.restaurantmanager.UI.Admin;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Admin.CheckOutModules.invoice_fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.OFFLINE_ORDER_LIST_ADMIN;


public class checkout_fragment extends Fragment {
    View view;
    private List<String> table_numbers = new ArrayList<>();
    private List<String> order_numbers = new ArrayList<>();
    private Button Generate;
    private TextView items;
    private TextView name;
    private LinearLayout layoutOuter;
    public static int tno = 0;
    public static int ono = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_manageorders, container, false);

        layoutOuter = view.findViewById(R.id.admin_manageorders_linearlayout);

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.admin_manageorders_refresh);
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

        tno = 0;
        ono = 0;

        table_numbers.clear();
        order_numbers.clear();

        DatabaseHandler handler = new DatabaseHandler(OFFLINE_ORDER_LIST_ADMIN, getContext()) {
            @Override
            public void writeCode(String response) throws Exception {
                RelativeLayout layout = view.findViewById(R.id.admin_manageorders_relativelayout);
                try {
                    JSONArray array = new JSONArray(response);
                    layout.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        table_numbers.add(object.getString("Tno"));
                        order_numbers.add(object.getString("Ono"));
                        dyanamicviews(i);
                    }
                }
                catch (Exception e)
                {
                    layout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public Map<String, String> params() {
                Map<String,String > map = new HashMap<>();
                Log.d("HANUMAN", "writeCode:"+AdminActivity.sharedPreferencesHandler.getId());
                map.put("Rid", AdminActivity.sharedPreferencesHandler.getId()+"");
                return map;
            }
        };
        handler.execute();
    }

    private void dyanamicviews(final int i) {
        Log.d("HANUMAN", "writeCode:"+table_numbers);
        RelativeLayout layoutInner = new RelativeLayout(getContext());
        layoutInner.setId(i);
        RelativeLayout.LayoutParams layoutForInner = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//Layout Parameters for Each Cart Layouts
        layoutForInner.setMargins(10, 10, 10, 10);//Parameter for Each Cart Layout
        layoutInner.setBackgroundResource(R.drawable.border_layout);


        name = new TextView(getContext());
        name.setId(i + 200);
        RelativeLayout.LayoutParams layoutForName = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForName.leftMargin = 20;
        layoutForName.topMargin = 40;
        name.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        name.setTextSize(1, 18);
        name.setText("Table Number : " + table_numbers.get(i));

        items = new TextView(getContext());//Creating TextView For Showing items
        RelativeLayout.LayoutParams layoutForitems = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForitems.leftMargin = 20;//Layout And Parametes For items
        layoutForitems.topMargin = 175;
        layoutForitems.bottomMargin = 50;
        items.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        items.setId(i + 300);
        items.setLineSpacing((float) 1.0, (float) 1.2);

        final int id = items.getId();
        DatabaseHandler handler = new DatabaseHandler(DatabaseHandler.OFFLINE_ORDER_ITEMLIST_ADMIN,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {
                Log.d("LUND", "Array : "+response);
                JSONArray array = new JSONArray(response);
                TextView item = view.findViewById(id);

                for(int i=0;i<array.length();i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    item.append("\n\t"+object.getString("qty") + " ×\t" + object.getString("name") + " ");
                }
            }
            @Override
            public Map<String, String> params() {
                Map<String,String > map = new HashMap<>();
                map.put("ono",order_numbers.get(i)+"");
                return map;
            }
        };
        handler.execute();

        Generate = new Button(getContext());
        Generate.setId(i + 400);
        RelativeLayout.LayoutParams layoutForGenerate = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForGenerate.addRule(RelativeLayout.ALIGN_PARENT_END);//Layout And Parametes For Generate
        layoutForGenerate.setMargins(30, 30, 30, 30);
        Generate.setBackgroundResource(R.drawable.button_shape);
        Generate.setText("Generate");
        Generate.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        Generate.setTextColor(getResources().getColor(R.color.colorWhite));
        Generate.setVisibility(View.VISIBLE);
        layoutInner.setBackgroundResource(R.drawable.table_layout);
        Generate.setBackgroundResource(R.drawable.table_button);
        Generate.setTextColor(Color.WHITE);
        name.setTextColor(Color.WHITE);
        items.setTextColor(Color.WHITE);

        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") int id = v.getId()-400;
                ono = Integer.parseInt(order_numbers.get(id));
                AdminActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.admin_container,new invoice_fragment(),null).commit();
            }
        });

        layoutInner.addView(name, layoutForName);//Adding Name TextView To Relative Layout(PARENT)
        layoutInner.addView(items, layoutForitems);
        layoutInner.addView(Generate, layoutForGenerate);//Adding items TextView To Relative Layout (ALL WITH LAYOUT PARAMETERS)
        layoutOuter.addView(layoutInner, layoutForInner);
    }
}
