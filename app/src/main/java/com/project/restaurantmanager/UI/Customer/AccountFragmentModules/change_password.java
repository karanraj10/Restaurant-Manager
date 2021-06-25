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

public class change_password extends Fragment {
    String passwordfirst;
    String passwordsecond;
    TextInputEditText password1;
    TextInputEditText password2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.customer_changepassword_fragment,container,false);

        Button update = view.findViewById(R.id.cust_acc_changepass_button);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TextInputEditText password1 = view.findViewById(R.id.cust_acc_changepass_password1_edit);
                    final TextInputEditText password2 = view.findViewById(R.id.cust_acc_changepass_password2_edit);
                    passwordfirst=password1.getText().toString();
                    passwordsecond=password2.getText().toString();

                    if(passwordfirst.equals(passwordsecond)) {

                        DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.UPDATE_PASSWORD_CUSTOMER_EMPLOYEE,getContext()) {
                            @Override
                            public void writeCode(String response) throws JSONException {
                                response=response.trim();
                                Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                password1.setText("");
                                password2.setText("");
                            }
                            @Override
                            public Map<String, String> params() {
                                Map<String, String> map = new HashMap<>();
                                map.put("password",passwordfirst);
                                map.put("email", MainActivity.sharedPreferences.getEmail());
                                map.put("post",MainActivity.sharedPreferences.getPost());
                                return map;
                            }
                        };
                        databaseHandler.execute();
                    } else{
                        Toast.makeText(getActivity().getApplicationContext(),"Both Password Must be Same",Toast.LENGTH_SHORT).show();
                    }

                }
            });

        return view;
    }
}
