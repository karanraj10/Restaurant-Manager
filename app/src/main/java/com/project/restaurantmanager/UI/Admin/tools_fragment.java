package com.project.restaurantmanager.UI.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Admin.AccountFragmentModules.manage_account_fragment;
import com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_employee;
import com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_food;
import com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_table;
import com.project.restaurantmanager.UI.Admin.AdminToolsFragment.online_order;

public class tools_fragment extends Fragment {
    View view;
    Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_tools_fragment,container,false);

        ImageView online_order = view.findViewById(R.id.online_order_image_view);
        ImageView manage_employee = view.findViewById(R.id.online_order_image_view1);
        ImageView manage_table = view.findViewById(R.id.online_order_image_view2);
        ImageView manage_food = view.findViewById(R.id.online_order_image_view3);

        online_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new online_order();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,fragment,null).commit();
            }
        });

        manage_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new manage_employee();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,fragment,null).commit();
            }
        });

        manage_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new manage_table();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,fragment,null).commit();
            }
        });

        manage_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new manage_food();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,fragment,null).commit();
            }
        });


        return view;
    }
}
