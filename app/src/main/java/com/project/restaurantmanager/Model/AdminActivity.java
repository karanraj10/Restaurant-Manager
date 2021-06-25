package com.project.restaurantmanager.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.restaurantmanager.Controller.SharedPreferencesHandler;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Admin.AdminToolsFragment.online_order;
import com.project.restaurantmanager.UI.Admin.account_fragment;
import com.project.restaurantmanager.UI.Admin.checkout_fragment;
import com.project.restaurantmanager.UI.Admin.order_reservation_fragment;
import com.project.restaurantmanager.UI.Admin.tools_fragment;

public class AdminActivity extends AppCompatActivity {
    public static SharedPreferencesHandler sharedPreferencesHandler;
    public static FragmentManager fragmentManager ;
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sharedPreferencesHandler  = new SharedPreferencesHandler(getApplicationContext());

        fragmentManager = getSupportFragmentManager();

        if (getIntent().getExtras() != null)
        {
            fragmentManager.beginTransaction().add(R.id.admin_container,new online_order(),null).commit();
        }
        else
        {
            fragmentManager.beginTransaction().add(R.id.admin_container,new checkout_fragment(),null).commit();
        }


        bottomNavigationView = findViewById(R.id.adminactivity_bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.ManagerReservation:
                        fragment = new order_reservation_fragment();
                        break;
                    case  R.id.ManageOrders:
                        fragment = new checkout_fragment();
                        break;
                    case R.id.ManageAccount:
                        fragment = new account_fragment();
                        break;
                    case R.id.AdminTools:
                        fragment = new tools_fragment();
                        break;
                    default:
                }
                fragmentManager.beginTransaction().add(R.id.admin_container, fragment, null).commit();
                return true;
            }

        });
    }

    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        switch (bottomNavigationView.getSelectedItemId()) {
            case R.id.ManageOrders:
                if (count == 0) {
                    finishAffinity();
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            case R.id.ManagerReservation:
                if (count == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.ManageOrders);
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            case R.id.AdminTools:
                if (count == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.ManageOrders);
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            case R.id.ManageAccount:
                if (count == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.ManageOrders);
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            default:
        }
    }
}

