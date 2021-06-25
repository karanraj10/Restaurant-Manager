package com.project.restaurantmanager.UI.Employee.AccountFragmentModules;

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

public class manage_address extends Fragment {
    TextInputEditText address;
    String uaddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_accountaddress_fragment,container,false);
        Button update = view.findViewById(R.id.emp_acc_address_update_button);
        address = view.findViewById(R.id.emp_acc_address_edit);
        address.setText(MainActivity.sharedPreferences.getAddress());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uaddress = address.getText().toString();
                MainActivity.sharedPreferences.setAddress(uaddress);

                DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.UPDATE_ADDRESS_CUSTOMER_EMPLOYEE,getContext()) {
                    @Override
                    public void writeCode(String response) throws JSONException {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();
                        map.put("address",uaddress);
                        map.put("email",MainActivity.sharedPreferences.getEmail());
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
