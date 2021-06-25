package com.project.restaurantmanager.UI.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Admin.OrderReservationModules.generate_report;
import com.project.restaurantmanager.UI.Admin.OrderReservationModules.orders_fragment;
import com.project.restaurantmanager.UI.Admin.OrderReservationModules.reservation_fragment;

public class order_reservation_fragment extends Fragment {
    Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_res,null);


        ImageView order = view.findViewById(R.id.order_image);
        ImageView reservation = view.findViewById(R.id.reservation_image);
//        ImageView report = view.findViewById(R.id.generate_image);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new orders_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,fragment,null).commit();
            }
        });

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new reservation_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,fragment,null).commit();
            }
        });

//        report.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragment = new generate_report();
//                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.admin_container,fragment,null).commit();
//            }
//        });



        return view;
    }
}
