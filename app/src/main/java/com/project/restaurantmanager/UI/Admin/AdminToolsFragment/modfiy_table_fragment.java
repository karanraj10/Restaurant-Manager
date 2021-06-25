package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.UPDATE_TABLE_ADMIN;
import static com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_table.capacityformodify;
import static com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_table.tablenoformodify;

public class modfiy_table_fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.admin_table_add_modify,container,false);
        final Button modify = view.findViewById(R.id.admin_add_table_RegisterBtn);
        modify.setText("Modify");
        modify.setEnabled(false);

        TextView label = view.findViewById(R.id.admin_add_table_text1);
        label.setText("Modify Table");

        final TextInputEditText capacity = view.findViewById(R.id.admin_add_table_capacity);

        capacity.setText(capacityformodify);

        TextWatcher textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            public void afterTextChanged(Editable s) {
                modify.setEnabled(true);
            }
        };


        capacity.addTextChangedListener(textWatcher);


        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify.setEnabled(false);
                DatabaseHandler databaseHandler = new DatabaseHandler(UPDATE_TABLE_ADMIN,getContext()) {
                    @Override
                    public void writeCode(String response) throws Exception {
                        JSONObject object = new JSONObject(response);
                        if(object.getString("result").equals("pass"))
                        {
                            Toast.makeText(getContext(),"Table Modified Successfully!",Toast.LENGTH_SHORT).show();
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
                        return map;
                    }
                };
                databaseHandler.execute();
            }
        });

      return view;
    }
}
