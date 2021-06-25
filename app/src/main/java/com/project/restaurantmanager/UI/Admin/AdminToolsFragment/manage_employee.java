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

import static com.project.restaurantmanager.Controller.DatabaseHandler.DELETE_EMPLOYEE_ADMIN;
import static com.project.restaurantmanager.Controller.DatabaseHandler.EMPLOYEE_LIST_ADMIN;

public class manage_employee extends Fragment {
    private View view;
    private LinearLayout layoutOuter;
    private TextView username,name,email,salary,joindate,contact;
    private Button delete;
    private static List<String> usernameList;
    private static List<String> nameList;
    private static List<String> emailList;
    private static List<String> salaryList;
    private static List<String> joindateList;
    private static List<String> contactList;
    public static String usernameformodify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_manage_employee, container, false);

        layoutOuter = view.findViewById(R.id.admin_manage_employee_linearlayout);

        final SwipeRefreshLayout refreshLayout = view.findViewById(R.id.admin_emp_refreshlayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
                refreshLayout.setRefreshing(false);
            }
        });

        ImageView add_employee = view.findViewById(R.id.admin_manage_employee_imgv);
        add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,new add_employee_fragment(),null).commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutOuter.removeAllViews();

        usernameList = new ArrayList<>();
        nameList = new ArrayList<>();
        emailList = new ArrayList<>();
        salaryList = new ArrayList<>();
        joindateList = new ArrayList<>();
        contactList = new ArrayList<>();

        DatabaseHandler handler = new DatabaseHandler(EMPLOYEE_LIST_ADMIN,getContext()) {
            @Override
            public void writeCode(String response) throws Exception {
                RelativeLayout addemployee = view.findViewById(R.id.admin_manage_employee_relativelayout);
                addemployee.setVisibility(View.VISIBLE);
                Log.d("ffff", "writeCode: "+response);
               if(!response.equals("NULL"))
               {
                   JSONArray array = new JSONArray(response);
                   for(int i=0;i<array.length();i++)
                   {
                       JSONObject object = array.getJSONObject(i);
                       usernameList.add(object.getString("username"));
                       nameList.add(object.getString("name"));
                       emailList.add(object.getString("email"));
                       salaryList.add(object.getString("salary"));
                       joindateList.add(object.getString("joindate"));
                       contactList.add(object.getString("contact"));
                       dynamic_view(i);
                   }
               }

            }
            @Override
            public Map<String, String> params() {
                Map<String,String> map = new HashMap<>();
                map.put("Rid", AdminActivity.sharedPreferencesHandler.getId());
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
                (RelativeLayout.LayoutParams.MATCH_PARENT, 500);
        layoutForInner.setMargins(10, 10, 10, 10);
        layoutInner.setPadding(20,20,20,20);
        layoutInner.setBackgroundResource(R.drawable.border_layout);

        username = new TextView(getContext());
        username.setId(i + 100);
        RelativeLayout.LayoutParams layoutForusername = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForusername.leftMargin = 20;
        layoutForusername.topMargin = 20;
        layoutForusername.addRule(RelativeLayout.BELOW,i+200);
        username.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        username.setTextSize(1, 15);
        username.setText("Username : "+usernameList.get(i));

        name = new TextView(getContext());
        name.setId(i + 200);
        RelativeLayout.LayoutParams layoutForname = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForname.leftMargin = 20;
        layoutForname.topMargin = 20;
        name.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        name.setTextSize(1, 15);
        name.setText(nameList.get(i));
        name.setTextColor(getResources().getColor(R.color.colorPrimary));

        email = new TextView(getContext());
        email.setId(i + 300);
        RelativeLayout.LayoutParams layoutForemail = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForemail.leftMargin = 20;
        layoutForemail.topMargin = 20;
        layoutForemail.addRule(RelativeLayout.BELOW,i+100);
        email.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        email.setTextSize(1, 15);
        email.setText("Email : " +emailList.get(i));

        joindate = new TextView(getContext());
        joindate.setId(i + 400);
        RelativeLayout.LayoutParams layoutForjoindate = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForjoindate.leftMargin = 20;
        layoutForjoindate.topMargin = 20;
        layoutForjoindate.addRule(RelativeLayout.BELOW,i+300);
        joindate.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        joindate.setTextSize(1, 15);
        joindate.setText("JoinDate : "+joindateList.get(i));

        salary = new TextView(getContext());
        salary.setId(i + 500);
        RelativeLayout.LayoutParams layoutForsalary = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForsalary.leftMargin = 20;
        layoutForsalary.topMargin = 20;
        layoutForsalary.addRule(RelativeLayout.BELOW,i+400);
        salary.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        salary.setTextSize(1, 15);
        salary.setText("Salary : "+salaryList.get(i));

        contact = new TextView(getContext());
        contact.setId(i + 600);
        RelativeLayout.LayoutParams layoutForcontact = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutForcontact.leftMargin = 20;
        layoutForcontact.topMargin = 20;
        layoutForcontact.addRule(RelativeLayout.BELOW,i+500);
        contact.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        contact.setTextSize(1, 15);
        contact.setText("Contact : "+contactList.get(i));

        delete = new Button(getContext());
        delete.setId(i + 700);
        delete.setBackgroundResource(R.drawable.ic_delete);
        RelativeLayout.LayoutParams layoutFordelete = new RelativeLayout.LayoutParams
                (35, 35);
        layoutFordelete.rightMargin = 20;
        layoutFordelete.topMargin = 20;
        layoutFordelete.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                @SuppressLint("ResourceType") int id = v.getId()-700;
                final String usernamefordelete = ((TextView)view.findViewById(id+100)).getText().toString().substring(11);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("You really want to remove '"+usernamefordelete+"'?");
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler databaseHandler = new DatabaseHandler(DELETE_EMPLOYEE_ADMIN,getContext()) {
                            @Override
                            public void writeCode(String response) throws Exception {
                                Toast.makeText(getContext(),response, Toast.LENGTH_SHORT).show();
                                onResume();
                            }
                            @Override
                            public Map<String, String> params() {
                                Map<String, String> map = new HashMap<>();
                                map.put("username", usernamefordelete);
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

        layoutInner.addView(username, layoutForusername);
        layoutInner.addView(name, layoutForname);
        layoutInner.addView(email, layoutForemail);
        layoutInner.addView(salary, layoutForsalary);
        layoutInner.addView(joindate, layoutForjoindate);
        layoutInner.addView(contact, layoutForcontact);
        layoutInner.addView(delete, layoutFordelete);

        layoutOuter.addView(layoutInner, layoutForInner);
    }
}
