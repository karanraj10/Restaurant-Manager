package com.project.restaurantmanager.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Customer.RestaurantListFragment;
import com.project.restaurantmanager.UI.Customer.FoodItemListFragment;
import com.project.restaurantmanager.UI.Customer.FoodReservationFragment;
import com.project.restaurantmanager.UI.Customer.TableFragmentModules.Recyclerviewadapter_Table;
import com.project.restaurantmanager.UI.Customer.AccountFragment;
import com.project.restaurantmanager.UI.Customer.CartFragment;
import com.project.restaurantmanager.UI.Customer.ReservationFragment;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.SEND_NOTIFICATION;
import static com.project.restaurantmanager.UI.Customer.CartFragment.map;
import static com.project.restaurantmanager.UI.Customer.CartFragment.mapDB;
import static com.project.restaurantmanager.UI.Customer.ReservationFragment.GuestsDB;
import static com.project.restaurantmanager.UI.Customer.ReservationFragment.dateDB;


public class CustomerActivity extends AppCompatActivity  implements PaymentResultListener {
    Fragment fragment;
    public static BottomNavigationView bottomNavigationView;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        /*For Showing Internet Error*/
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        }

        fragmentManager=getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.customer_container,new RestaurantListFragment(),null).commit();

        bottomNavigationView = findViewById(R.id.baseactivity_bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
                switch (menuItem.getItemId()){
                    case R.id.Delivery:
                        fragment = new RestaurantListFragment();
                        break;
                    case R.id.Cart:
                        fragment = new CartFragment();
                        break;
                    case R.id.Account:
                        fragment = new AccountFragment();
                        break;
                    default:
                }
                fragmentManager.beginTransaction().replace(R.id.customer_container,fragment,null).commit();
                return true;
            }
        });
    }
    @SuppressLint("CommitPrefEdits")
    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        switch (bottomNavigationView.getSelectedItemId()) {
            case R.id.Delivery:
                if (count == 0) {
                    finishAffinity();
                } else {
                    bottomNavigationView.setSelectedItemId(R.id.Delivery);
                    getSupportFragmentManager().popBackStack("foodreservation",FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                break;
            case R.id.Cart:
                bottomNavigationView.setSelectedItemId(R.id.Delivery);
                break;
            case R.id.Account:
                if (count == 0) {
                    bottomNavigationView.setSelectedItemId(R.id.Cart);
                } else {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            default:
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        if(MainActivity.paymentMethod!=null&&MainActivity.paymentMethod.equals("res")) {
            DatabaseHandler databaseHandlerForRes = new DatabaseHandler(DatabaseHandler.INSERT_NEW_RESERVATION_CUSTOMER, getApplicationContext()) {
                @Override
                public void writeCode(String response) throws JSONException {
                    Log.d("ffff", "writeCode: "+response);
                    Toast.makeText(getApplicationContext(), response.trim(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public Map<String, String> params() {
                    Map<String, String> map = new HashMap<>();
                    map.put("StartTime", Recyclerviewadapter_Table.startTimeDB);
                    map.put("EndTime", Recyclerviewadapter_Table.endTimeDB);
                    map.put("Date", dateDB);
                    map.put("Guest", GuestsDB);
                    map.put("Email", MainActivity.sharedPreferences.getEmail());
                    map.put("Tno", ReservationFragment.tableNoDB);
                    map.put("Rid", FoodReservationFragment.mSelectedRid+"");
                    return map;
                }
            };
            databaseHandlerForRes.execute();

        }else if(MainActivity.paymentMethod!=null&&MainActivity.paymentMethod.equals("cart")) {
            FoodItemListFragment.i=0;
            final DatabaseHandler databaseHandlerForCart = new DatabaseHandler(DatabaseHandler.INSERT_NEW_ORDER_CUSTOMER, getApplicationContext()) {
                @Override
                public void writeCode(String response) throws JSONException {
                    Toast.makeText(getApplicationContext(), response.trim(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public Map<String, String> params() {
                    return mapDB;
                }
            };

            databaseHandlerForCart.execute();
            CartFragment.cartEmptyimageView.setVisibility(View.VISIBLE);
            CartFragment.cartEmptytextView.setVisibility(View.VISIBLE);
            CartFragment.quantity.clear();
            FoodItemListFragment.i=0;
            FoodItemListFragment.nameForCart.clear();
            FoodItemListFragment.priceForCart.clear();
            map.clear();
            FoodItemListFragment.buttonflag.clear();

        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),"" + s,Toast.LENGTH_SHORT).show();
        dateDB=null;
        FoodItemListFragment.nameForCart.clear();
        FoodItemListFragment.priceForCart.clear();
        FoodItemListFragment.buttonflag.clear();
        CartFragment.quantity.clear();
        map.clear();
        FoodItemListFragment.i=0;
        ReservationFragment.tableNoDB=null;
    }
}
