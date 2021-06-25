package com.project.restaurantmanager.UI.Customer.AccountFragmentModules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class manage_account_fragment extends Fragment {

    TextInputEditText name, mobile;
    String uname, umobile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.customer_accountsetting_fragment,container, false);

        name = view.findViewById(R.id.cust_acc_manage_name_edit);
        name.setText(MainActivity.sharedPreferences.getName());

        mobile = view.findViewById(R.id.cust_acc_manage_mobile_edit);
        mobile.setText(MainActivity.sharedPreferences.getMobile());

        Button update = view.findViewById(R.id.cust_acc_manage_update_button);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = name.getText().toString();
                umobile = mobile.getText().toString();

                MainActivity.sharedPreferences.setrName(uname);
                MainActivity.sharedPreferences.setMobile(umobile);


                DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.UPDATE_ACCOUNT_SETTING_EMPLOYEE_CUSTOMER,getContext()) {
                    @Override
                    public void writeCode(String response) throws JSONException {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();
                        map.put("name",uname);
                        map.put("mobile",umobile);
                        map.put("email", MainActivity.sharedPreferences.getEmail());
                        map.put("post",MainActivity.sharedPreferences.getPost());
                        return map;
                    }
                };
                databaseHandler.execute();
            }
        });
        return view;
    }
}
