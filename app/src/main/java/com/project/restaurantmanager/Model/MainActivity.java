package com.project.restaurantmanager.Model;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.project.restaurantmanager.Controller.SharedPreferencesHandler;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Base.base_fragment;


public class MainActivity extends AppCompatActivity{
    public static FragmentManager fragmentManager;
    public static boolean resetflag=false;/* For Toasting Password Reset (IMPORTANT)*/
    public static SharedPreferencesHandler sharedPreferences;
    public static String paymentMethod;
    public static Context context;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=MainActivity.this;

        /*Creating Preferences And If Preferences Are Set Skipping this Activity*/
        sharedPreferences = new SharedPreferencesHandler(MainActivity.this);
        if (sharedPreferences.getFlag()&&sharedPreferences.getPost().equals("Customer")) {
            Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
            intent.putExtras(getIntent());
            MainActivity.this.startActivity(intent);
        }
         else if (sharedPreferences.getFlag()&&sharedPreferences.getPost().equals("Employee")) {
            Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
            intent.putExtras(getIntent());
            MainActivity.this.startActivity(intent);
        }
         else if (sharedPreferences.getFlag()&&sharedPreferences.getPost().equals("Admin")) {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            intent.putExtras(getIntent());
            MainActivity.this.startActivity(intent);
        }
         else {
            /*Skipping This if Already Logged in*/
            setContentView(R.layout.activity_main);

            /*For Showing Internet Error*/
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo == null) {
                Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
            }

            /*First Fragment To Container*/
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.base_container, new base_fragment(), null).commit();
        }
    }

    @Override
    public void onBackPressed() {
        /*For Going Back to Previous Fragment Without Conflict*/
        if(getFragmentManager().getBackStackEntryCount()==0) {
            super.onBackPressed();
        }
    }

}
