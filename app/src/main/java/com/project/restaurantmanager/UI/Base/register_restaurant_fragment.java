package com.project.restaurantmanager.UI.Base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.Constants;
import com.project.restaurantmanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.REGISTER_RESTAURANT;
import static com.project.restaurantmanager.Controller.DatabaseHandler.REGISTER_RESTAURANT_CHECK;

public class register_restaurant_fragment extends Fragment {

    String[] locations = {"Baroda", "Anand", "VVNagar", "Ahemdabad","Nadiad","Surat","Bharuch","Bhuj","Rajkot","Bhavnagar"};
    int FILE_SELECT_CODE = 023432;
    ImageView imageView;

    public static Map<String,String> mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_restaurant_fragment1, container, false);

        mData= new HashMap<>();

        final Spinner location = view.findViewById(R.id.reg_res_location);

        imageView = view.findViewById(R.id.reg_res_next);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, locations);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(arrayAdapter);

        final TextInputEditText username = view.findViewById(R.id.reg_res_username);
        final TextInputEditText email = view.findViewById(R.id.reg_res_email);
        final TextInputEditText password = view.findViewById(R.id.reg_res_password);
        final TextInputEditText mobile = view.findViewById(R.id.reg_res_contact);
        final TextInputEditText address = view.findViewById(R.id.reg_res_address);
        final TextInputEditText gstin = view.findViewById(R.id.reg_res_gstin);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DatabaseHandler handler = new DatabaseHandler(REGISTER_RESTAURANT_CHECK, getContext()) {
//                    @Override
//                    public void writeCode(String response) throws JSONException, InterruptedException, Exception {
//                        if (new JSONObject(response).getInt("error") == 3) {
//                            username.setText("");
//                            Toast.makeText(getContext(), Constants.getError3, Toast.LENGTH_SHORT).show();
//                        } else if(new JSONObject(response).getInt("error") == 5) {
//                            email.setText("");
//                            Toast.makeText(getContext(), Constants.getError5, Toast.LENGTH_SHORT).show();
//                        }else if (new JSONObject(response).getInt("error") == 1) {
//
//                            mData.put("username",username.getText().toString());
//                            mData.put("email",email.getText().toString());
//                            mData.put("password",password.getText().toString());
//                            mData.put("mobile",mobile.getText().toString());
//                            mData.put("address",address.getText().toString());
//                            mData.put("gstin",gstin.getText().toString());
//                            mData.put("location",location.getSelectedItem().toString());
//
//                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.base_container,new register_restaurant_fragment2() ,null).commit();
//                        }
//
//                    }
//                    @Override
//                    public Map<String, String> params() {
//                        Map<String, String> map = new HashMap<>();
//                        map.put("username", username.getText().toString());
//                        map.put("email", email.getText().toString());
//                        return map;
//                    }
//                };

                List<String> values = new ArrayList<>();
                values.add(username.getText().toString());
                values.add(email.getText().toString());
                values.add(password.getText().toString());
                values.add(mobile.getText().toString());
                values.add(address.getText().toString());
                values.add(gstin.getText().toString());

                if(values.contains(""))
                {
                    Toast.makeText(getContext(), Constants.getError0, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mData.put("username",username.getText().toString());
                            mData.put("email",email.getText().toString());
                            mData.put("password",password.getText().toString());
                            mData.put("mobile",mobile.getText().toString());
                            mData.put("address",address.getText().toString());
                            mData.put("gstin",gstin.getText().toString());
                            mData.put("location",location.getSelectedItem().toString());
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.base_container,new register_restaurant_fragment2() ,null).commit();
                }
            }
        });

        return view;
    }


}
