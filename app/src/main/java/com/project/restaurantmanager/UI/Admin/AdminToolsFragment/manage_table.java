package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.project.restaurantmanager.Controller.DatabaseHandler.DELETE_TABLE_ADMIN;
import static com.project.restaurantmanager.Controller.DatabaseHandler.TABLE_LIST_ADMIN_EMPLOYEE;

public class manage_table extends Fragment {
    private View view;
    private Button delete;
    private TextView tableno,capacity;
    private LinearLayout layoutOuter;
    private List<String> tablenoList;
    private List<String> capacityList;
    public static String tablenoformodify,capacityformodify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_manage_table,container,false);

        layoutOuter = view.findViewById(R.id.admin_manage_table_linearlayout);

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.admin_manage_table_refreshlayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                refreshLayout.setRefreshing(false);
            }
        });

        ImageView addtable = view.findViewById(R.id.admin_manage_table_imgv);
        addtable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,new add_table_fragment(),null).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutOuter.removeAllViews();
        final RelativeLayout addtableLayout = view.findViewById(R.id.admin_manage_table_relativelayout);
        addtableLayout.setVisibility(View.INVISIBLE);
        capacityList = new ArrayList<>();
        tablenoList = new ArrayList<>();

        DatabaseHandler handler = new DatabaseHandler(TABLE_LIST_ADMIN_EMPLOYEE, getContext()) {
            @Override
            public void writeCode(String response) throws Exception {
                JSONArray array = new JSONArray(response);
                Log.d("ffff", "writeCode: "+response);
                if(array.getJSONObject(0).getInt("error")==1) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        tablenoList.add(object.getString("tno"));
                        capacityList.add(object.getString("capacity"));
                        dynamic_view(i);
                    }
                }

                addtableLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public Map<String, String> params() {
                Map<String,String > map = new HashMap<>();
                map.put("Rid", AdminActivity.sharedPreferencesHandler.getId()+"");
                return map;
            }
        };
        handler.execute();

    }
    private void dynamic_view(int i)
    {
        RelativeLayout layoutInner = new RelativeLayout(getContext());
        layoutInner.setId(i);
        RelativeLayout.LayoutParams layoutForInner = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, 300);
        layoutForInner.setMargins(10, 10, 10, 10);
        layoutInner.setPadding(20,20,20,20);
        layoutInner.setBackgroundResource(R.drawable.border_layout);

        layoutInner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int id = v.getId();
                tablenoformodify = ((TextView)view.findViewById(id+100)).getText().toString().substring(15);
                capacityformodify = ((TextView) view.findViewById(id+200)).getText().toString().substring(11);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,new modfiy_table_fragment(),null).commit();
                return true;
            }
        });

        tableno = new TextView(getContext());
        tableno.setId(i + 100);
        RelativeLayout.LayoutParams layoutFortableno = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutFortableno.leftMargin = 20;
        layoutFortableno.topMargin = 20;
        tableno.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        tableno.setTextSize(1, 15);
        tableno.setTextColor(getResources().getColor(R.color.colorPrimary));
        tableno.setText("Table Number : "+tablenoList.get(i));

        capacity = new TextView(getContext());
        capacity.setId(i + 200);
        RelativeLayout.LayoutParams layoutForcapacity = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForcapacity.leftMargin = 20;
        layoutForcapacity.topMargin = 20;
        layoutForcapacity.addRule(RelativeLayout.BELOW,i+100);
        capacity.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        capacity.setTextSize(1, 15);
        capacity.setText("Capacity : "+capacityList.get(i));

        delete = new Button(getContext());
        delete.setId(i + 300);
        delete.setBackgroundResource(R.drawable.ic_delete);
        RelativeLayout.LayoutParams layoutFordelete = new RelativeLayout.LayoutParams
                (35, 35);
        layoutFordelete.rightMargin = 20;
        layoutFordelete.topMargin = 20;
        layoutFordelete.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                @SuppressLint("ResourceType") int id = v.getId()-300;
                final String tnofordelete = ((TextView)view.findViewById(id+100)).getText().toString().substring(15);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("You really want to remove '"+tnofordelete+"'?");
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler databaseHandler = new DatabaseHandler(DELETE_TABLE_ADMIN,getContext()) {
                            @Override
                            public void writeCode(String response) throws Exception {
                                Toast.makeText(getContext(),response.trim(), Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                            @Override
                            public Map<String, String> params() {
                                Map<String, String> map = new HashMap<>();
                                map.put("tno", tnofordelete);
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

        layoutInner.addView(delete,layoutFordelete);
        layoutInner.addView(tableno, layoutFortableno);
        layoutInner.addView(capacity, layoutForcapacity);

        layoutOuter.addView(layoutInner, layoutForInner);
    }
}
