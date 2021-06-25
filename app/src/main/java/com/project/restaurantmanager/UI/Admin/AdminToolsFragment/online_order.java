package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.ACCEPT_ONLINE_ORDER_ADMIN;
import static com.project.restaurantmanager.Controller.DatabaseHandler.SEND_NOTIFICATION;

public class online_order extends Fragment {
    String addressString;
    private List<String> onoString = new ArrayList<>();
    private List<String> cidString = new ArrayList<>();
    private List<String> itemString = new ArrayList<>();
    View view;
    TextView ono;
    TextView address;
    TextView items;
    Button finish;
    LinearLayout layoutOuter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_online_order_fragment, container, false);

        layoutOuter = view.findViewById(R.id.admin_onlineorder_linearlayout);

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.admin_onlineorder_refresh);
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
        layoutOuter.removeAllViews();
        DatabaseHandler handler = new DatabaseHandler(DatabaseHandler.ADMIN_ONLINE_ORDER_LINK, getContext()) {
            @Override
            public void writeCode(String response) throws Exception {
                JSONArray jsonArray = new JSONArray(response);
                for (int k = 0; k < jsonArray.length(); k++) {

                    JSONObject object = jsonArray.getJSONObject(k);

                    cidString.add(object.getString("Cid"));
                    onoString.add(object.getString("Ono"));
                    addressString = object.getString("Address");

                    JSONArray itemss  =  object.getJSONArray("Items");


                    for(int j = 0;j<itemss.length();j++)
                    {
                        JSONObject itemparticular = itemss.getJSONObject(j);
                        itemString.add(itemparticular.getString("Qty") +" x " + itemparticular.getString("Name"));
                    }

                    dynamic_views(k);


                    addressString = null;
                    itemString.clear();
                }
            }
            @Override
            public Map<String, String> params() {
                Map<String,String > map = new HashMap<>();
                map.put("Rid", AdminActivity.sharedPreferencesHandler.getId());
                return map;
            }
        };
        handler.execute();
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void dynamic_views(int i) {


        RelativeLayout layoutInner = new RelativeLayout(getContext());
        layoutInner.setId(i);
        RelativeLayout.LayoutParams layoutForInner = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);//Layout Parameters for Each Cart Layouts
        layoutForInner.setMargins(10, 10, 10, 10);//Parameter for Each Cart Layout
        layoutInner.setBackgroundResource(R.drawable.border_layout);

        ono = new TextView(getContext());
        ono.setId(i + 200);
        RelativeLayout.LayoutParams layoutForono = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForono.leftMargin = 20;
        layoutForono.topMargin = 20;
        ono.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        ono.setTextSize(1, 18);
        ono.setText(onoString.get(i));
        ono.setTextColor(getResources().getColor(R.color.colorPrimary));


        address = new TextView(getContext());
        address.setId(i + 300);
        RelativeLayout.LayoutParams layoutForaddress = new RelativeLayout.LayoutParams
                (500, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForaddress.leftMargin = 20;
        layoutForaddress.topMargin = 20;
        layoutForaddress.addRule(RelativeLayout.BELOW,(i+200));
        address.setLines(4);
        address.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        address.setText("Address :\n"+addressString);

        items = new TextView(getContext());
        RelativeLayout.LayoutParams layoutForitems = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForitems.leftMargin = 20;
        layoutForitems.topMargin = 20;
        layoutForitems.bottomMargin = 20;
        layoutForitems.addRule(RelativeLayout.BELOW,i+300);
        items.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        items.setId(i + 400);
        items.setLineSpacing((float) 1.0, (float) 1.2);
        items.setText("Items : \n");

        for(int p = 0;p<itemString.size();p++)
        {
            items.append(itemString.get(p)+"  \n");
        }

        finish = new Button(getContext());
        finish.setId(i + 500);
        RelativeLayout.LayoutParams layoutForfinish = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForfinish.addRule(RelativeLayout.ALIGN_PARENT_END);//Layout And Parametes For finish
        layoutForfinish.setMargins(30, 30, 30, 30);
        finish.setBackgroundResource(R.drawable.button_shape);
        finish.setText("FINISH");
        finish.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        finish.setTextColor(Color.WHITE);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ResourceType") final int id = v.getId()-500;
                final TextView textView = view.findViewById(id+200);
                RelativeLayout relativeLayout = view.findViewById(id);
                layoutOuter.removeView(relativeLayout);
                DatabaseHandler databaseHandler = new DatabaseHandler(ACCEPT_ONLINE_ORDER_ADMIN,getContext()) {
                    @Override
                    public void writeCode(String response) throws JSONException, InterruptedException, Exception {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                        sendNotification("customer",cidString.get(id));
                    }
                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();
                        map.put("ono", String.valueOf(textView.getText()));
                        return map;
                    }
                };
                databaseHandler.execute();
            }
        });

        layoutInner.addView(ono, layoutForono);//Adding Name TextView To Relative Layout(PARENT)
        layoutInner.addView(items, layoutForitems);
        layoutInner.addView(address, layoutForaddress);
        layoutInner.addView(finish, layoutForfinish);//Adding items TextView To Relative Layout (ALL WITH LAYOUT PARAMETERS)
        layoutOuter.addView(layoutInner, layoutForInner);
    }

    private void sendNotification(final String user, final String  id)
    {

        DatabaseHandler handler = new DatabaseHandler(SEND_NOTIFICATION,getContext()) {
                    @Override
                    public void writeCode(String response) throws JSONException, InterruptedException, Exception {
                        Log.d("sendNotificaiton", "writeCode: "+response);
                    }

                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();
                        map.put("message","Your order is out for delivery...");
                        map.put("topic",user+id);
                        return map;
                    }
                };
                handler.execute();

    }
}

