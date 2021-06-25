package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.os.Bundle;
import android.util.Log;
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
import com.project.restaurantmanager.Controller.SharedPreferencesHandler;
import com.project.restaurantmanager.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.INSERT_TABLE_ADMIN;

public class add_table_fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_table_add_modify,container,false);

        final TextInputEditText capacity = view.findViewById(R.id.admin_add_table_capacity);
        Button add = view.findViewById(R.id.admin_add_table_RegisterBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler databaseHandler = new DatabaseHandler(INSERT_TABLE_ADMIN,getContext()) {
                    @Override
                    public void writeCode(String response) throws  Exception {
                        JSONObject object = new JSONObject(response);
                        Log.d("ffff", "writeCode: "+response);
                        if(object.getString("result").equals("pass"))
                        {
                            Toast.makeText(getContext(),"Table Added Successfully!",Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Something Went Wrong!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();
                        map.put("capacity",capacity.getText().toString());
                        Log.d("ridadmin", "writeCode: "+new SharedPreferencesHandler(getContext()).getRid());
                        map.put("Rid",new SharedPreferencesHandler(getContext()).getId());
                        return map;
                    }
                };
                databaseHandler.execute();
            }
        });

        return view;
    }
}
