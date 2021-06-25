package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.Constants;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.REGISTER_EMPLOYEE;


public class add_employee_fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_employee_register,container,false);


        final TextInputEditText birthdate = view.findViewById(R.id.admin_emp_reg_bdate);
        final TextInputEditText username = view.findViewById(R.id.admin_emp_reg_username);
        final TextInputEditText email = view.findViewById(R.id.admin_emp_reg_email);
        final TextInputEditText name = view.findViewById(R.id.admin_emp_reg_name);
        final TextInputEditText contact = view.findViewById(R.id.admin_emp_reg_contact);
        final TextInputEditText address = view.findViewById(R.id.admin_emp_reg_address);
        final TextInputEditText salary = view.findViewById(R.id.admin_emp_reg_salary);
        final ProgressBar progressBar = view.findViewById(R.id.admin_emp_signup_progress_circular);
        progressBar.setVisibility(View.INVISIBLE);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        birthdate.setText(""+year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },Calendar.getInstance().getWeekYear()-18,Calendar.MONTH,Calendar.DAY_OF_MONTH).show();
            }
        });

        Button signup = view.findViewById(R.id.admin_emp_reg_RegisterBtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                progressBar.setIndeterminate(true);
                DatabaseHandler databaseHandler = new DatabaseHandler(REGISTER_EMPLOYEE,getContext()) {
                    @Override
                    public void writeCode(String response) throws Exception {
                        Log.d("ffff", "writeCode: "+response);

                        JSONObject object = new JSONObject(response);

                        Toast.makeText(getContext(), Constants.getError1, Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                        email.setText("");username.setText("");name.setText("");address.setText("");
                    }

                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();

                        map.put("username",username.getText().toString());
                        map.put("name",name.getText().toString());
                        map.put("contact",contact.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("address",address.getText().toString());
                        map.put("birthday",birthdate.getText().toString());
                        map.put("salary",salary.getText().toString());
                        map.put("Rid", AdminActivity.sharedPreferencesHandler.getId());

                        return map;
                    }
                };
                databaseHandler.execute();
            }
        });


        return view;
    }
}
