package com.project.restaurantmanager.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.restaurantmanager.Controller.SharedPreferencesHandler;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Employee.account_fragment;
import com.project.restaurantmanager.UI.Employee.dashboard_fragment;
import com.project.restaurantmanager.UI.Employee.menu_fragment;


public class EmployeeActivity extends AppCompatActivity {
   public static FragmentManager fragmentManager;
   public static BottomNavigationView bottomNavigationView;
   public static SharedPreferencesHandler sharedPreferencesHandler;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        sharedPreferencesHandler = new SharedPreferencesHandler(this);

        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.employee_container,new dashboard_fragment(),null).commit();

        bottomNavigationView = findViewById(R.id.emplyoeeactivity_bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.Dashboard:
                        fragment = new dashboard_fragment();
                        break;
                    case R.id.Menu:
                        fragment = new menu_fragment();
                        break;
                    case R.id.Account:
                        fragment = new account_fragment();
                        break;
                    default:
                }
                fragmentManager.beginTransaction().add(R.id.employee_container,fragment,null).commit();
                return true;
            }
        });
    }
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        switch (bottomNavigationView.getSelectedItemId()) {
            case R.id.Dashboard:
                finishAffinity();
                break;
            case R.id.Menu:
                bottomNavigationView.setSelectedItemId(R.id.Dashboard);
                break;
            case R.id.Account:
                if (count == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.Dashboard);
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            default:
        }
    }

}
