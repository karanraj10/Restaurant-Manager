package com.project.restaurantmanager.UI.Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;


public class base_fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.base_fragment,container,false);

        TextView registerTextView = view.findViewById(R.id.base_registerText);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.base_container,new register_fragment(),null).commit();
            }
        });

        Button loginBtn = view.findViewById(R.id.base_loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.base_container,new login_fragment(),null).commit();
            }
        });

        TextView partnerupTextView = view.findViewById(R.id.base_partnerupText);

        partnerupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.base_container,new register_restaurant_fragment(),null).commit();
            }
        });



        return view;
    }


}
