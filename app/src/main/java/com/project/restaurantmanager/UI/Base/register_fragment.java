package com.project.restaurantmanager.UI.Base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.Constants;
import com.project.restaurantmanager.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.project.restaurantmanager.Controller.DatabaseHandler.LOGIN;
import static com.project.restaurantmanager.Controller.DatabaseHandler.REGISTER_CUSTOMER;
import static com.project.restaurantmanager.Controller.DatabaseHandler.WELCOME_MAIL_CUSTOMER;

public class register_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_fragment,container,false);

        final TextInputEditText username = view.findViewById(R.id.reg_username);
        final TextInputEditText name = view.findViewById(R.id.reg_name);
        final TextInputEditText email = view.findViewById(R.id.reg_email);
        final TextInputEditText password = view.findViewById(R.id.reg_password);
        final TextInputEditText mobile = view.findViewById(R.id.reg_contact);
        final TextInputEditText address = view.findViewById(R.id.reg_address);
        final ProgressBar progressBar = view.findViewById(R.id.signup_progress_circular);
        final Button signupBtn = view.findViewById(R.id.reg_RegisterBtn);
        

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);


                StringRequest request = new StringRequest(Request.Method.POST, REGISTER_CUSTOMER, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject object = new JSONObject(response);
                            progressBar.setVisibility(View.GONE);
                            progressBar.setIndeterminate(false);
                            Thread.sleep(100);
                            Log.d("log1", "writeCode: " + response);

                            if (object.getInt("error") == 1) {
                                Toast.makeText(getContext(), Constants.getError1, Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().popBackStack();
                                email.setText("");
                                username.setText("");
                                name.setText("");
                                password.setText("");
                                mobile.setText("");
                                address.setText("");
                            } else if (object.getInt("error") == 2) {
                                Toast.makeText(getContext(), Constants.getError2, Toast.LENGTH_SHORT).show();
                                email.setText("");
                                username.setText("");
                                name.setText("");
                                password.setText("");
                                mobile.setText("");
                                address.setText("");
                            } else if (object.getInt("error") == 3) {
                                Toast.makeText(getContext(), Constants.getError3, Toast.LENGTH_SHORT).show();
                                username.setText("");
                            } else if (object.getInt("error") == 4) {
                                Toast.makeText(getContext(), Constants.getError4, Toast.LENGTH_SHORT).show();
                                email.setText("");
                            } else if (object.getInt("error") == 5) {
                                Toast.makeText(getContext(), Constants.getError5, Toast.LENGTH_SHORT).show();
                                email.setText("");
                            }
                        }
                        catch (Exception e)
                        {
                            Log.d("repo", "onResponse: "+response);
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("username", Objects.requireNonNull(username.getText()).toString());
                        map.put("email", Objects.requireNonNull(email.getText()).toString());
                        map.put("password", Objects.requireNonNull(password.getText()).toString());
                        map.put("name", Objects.requireNonNull(name.getText()).toString());
                        map.put("contact", Objects.requireNonNull(mobile.getText()).toString());
                        map.put("address", Objects.requireNonNull(address.getText()).toString());
                        Log.d("lol", "params: "+map);
                        return map;
                    }
                };

                List<String> values = new ArrayList<>();
                values.add(username.getText().toString());
                values.add(name.getText().toString());
                values.add(email.getText().toString());
                values.add(password.getText().toString());
                values.add(mobile.getText().toString());
                values.add(address.getText().toString());

                if(values.contains(""))
                {
                    Toast.makeText(getContext(), Constants.getError0, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    progressBar.setIndeterminate(false);
                }
                else
                {
                    request.setRetryPolicy(new DefaultRetryPolicy(
                            500000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.getCache().clear();
                    queue.add(request);
                    values.clear();
                }


            }
        });


        return view;
    }

}
