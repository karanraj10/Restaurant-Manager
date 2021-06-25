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

public class help_fragment extends Fragment {
    TextInputEditText query;
    String str_query;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_account_help_fragment, container, false);
        query = view.findViewById(R.id.cust_acc_help_query_edit);

        Button send = view.findViewById(R.id.cust_acc_help_send_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_query = query.getText().toString();

                Toast.makeText(getContext(),"Sent!",Toast.LENGTH_SHORT).show();
                DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.CONTACT_US,getContext()) {
                    @Override
                    public void writeCode(String response) throws JSONException {
                    }

                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();
                        map.put("message",str_query);
                        map.put("email", MainActivity.sharedPreferences.getEmail());
                        return map;
                    }
                };
                databaseHandler.execute();
            }
        });
        return view;
    }
}
