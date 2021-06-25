package com.project.restaurantmanager.UI.Employee;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.project.restaurantmanager.Controller.FirebaseMessageService;
import com.project.restaurantmanager.Controller.SharedPreferencesHandler;
import com.project.restaurantmanager.Model.EmployeeActivity;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;

import com.project.restaurantmanager.UI.Employee.AccountFragmentModules.change_password;
import com.project.restaurantmanager.UI.Employee.AccountFragmentModules.manage_account_fragment;
import com.project.restaurantmanager.UI.Employee.AccountFragmentModules.manage_address;

public class account_fragment extends Fragment {
    FragmentManager fragmentManager;
    Fragment fragment;
    String[] name = new String[]{"Manage Address","Change Password","Account Setting","Log Out"};
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_account_fragment,container,false);


        ListView listView = view.findViewById(R.id.emp_acc_list);

       CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        TextView name = view.findViewById(R.id.emp_acc_name_text);
        name.setText(MainActivity.sharedPreferences.getName());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        EmployeeActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.employee_container, new manage_address(),null).commit();
                        break;
                    case 1:
                        EmployeeActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.employee_container,new change_password(),null).commit();
                        break;
                    case 2:
                        EmployeeActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.employee_container, new manage_account_fragment(), null).commit();
                        break;

                    case 3:
                        SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(getContext());
                        sharedPreferencesHandler.setFlag(false);
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        dashboard_fragment.tno=0;
                        FirebaseMessageService.removeTopic("employee",sharedPreferencesHandler.getId());
                        NotificationManager nMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        nMgr.cancelAll();
                        /*To Prevent Unauthorized Login*/
                        getActivity().finish();
                        break;
                }
            }
        });
        return view;
    }
    /* List Needs Adapter For Gathering Data,
     Here CustomAdapter Takes Data From name String
     And Bind It To ListView
     */
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listview_account_layout,null);

            TextView settingName = convertView.findViewById(R.id.list_TextView);
            ImageView settingNextImage = convertView.findViewById(R.id.list_imageView);

            settingNextImage.setImageResource(R.drawable.ic_next);
            settingName.setText(name[position]);

            return convertView;
        }
    }
}

