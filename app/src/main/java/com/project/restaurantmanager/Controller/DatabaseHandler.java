package com.project.restaurantmanager.Controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;


import java.util.Map;


public abstract class DatabaseHandler {

    String link;
    Context context;
    RequestQueue queue;


    //Links

    public static final String BASE_URL = "http://192.168.0.114/PHP/";

    public static final String REGISTER_RESTAURANT_CHECK = BASE_URL + "RegisterRestaurantCheck.php";
    public static final String REGISTER_RESTAURANT = BASE_URL + "RegisterRestaurant.php";
    public static final String REGISTER_EMPLOYEE = BASE_URL + "RegisterEmployee.php";
    public static final String EMPLOYEE_LIST_ADMIN = BASE_URL + "EmployeeListAdmin.php";
    public static final String ACCEPT_ONLINE_ORDER_ADMIN = BASE_URL + "AcceptOnlineOrderAdmin.php";
    public static final String ADMIN_ONLINE_ORDER_LINK = BASE_URL + "OnlineOrderListAdmin.php";
    public static final String RESERVATION_LIST_ADMIN = BASE_URL + "ReservationListAdmin.php";
    public static final String INVOICE_TO_CUSTOMER_ADMIN = BASE_URL + "InvoiceToCustomerAdmin.php";
    public static final String UPDATE_FINISH_ORDER_EMPLOYEE = BASE_URL + "UpdateFinishOrderEmployee.php";
    public static final String WELCOME_MAIL_CUSTOMER = BASE_URL + "WelcomeMailCustomer.php";
    public static final String ORDER_ITEMLIST_EMPLOYEE_DASHBOARD = BASE_URL + "OrderItemListEmployeeDashboard.php";
    public static final String OFFLINE_ORDER_ITEMLIST_ADMIN = BASE_URL + "OfflineOrderItemListAdmin.php";
    public static final String INSERT_NEW_ODER_EMPLOYEE = BASE_URL + "InsertNewOrderEmployee.php";
    public static final String TABLE_LIST_EMPLOYEE_DASHBOARD = BASE_URL + "TableListEmployeeDashboard.php";
    public static final String ORDER_LIST_CUSTOMER = BASE_URL + "OrderListCustomer.php";
    public static final String RESERVATION_LIST_CUSTOMER = BASE_URL + "ReservationListCustomer.php";
    public static final String FOOD_ITEMLIST_CUSTOMER = BASE_URL + "FoodItemListCustomer.php";
    public static final String LOGIN = BASE_URL + "Login.php";
    public static final String REGISTER_CUSTOMER = BASE_URL + "RegisterCustomer.php";
    public static final String INSERT_NEW_RESERVATION_CUSTOMER = BASE_URL + "InsertNewReservationCustomer.php";
    public static final String RESET_PASSWORD_CUSTOMER = BASE_URL + "ResetPasswordCustomer.php";
    public static final String INSERT_RESERVATION_CHECK_CUSTOMER = BASE_URL + "InsertReservationCheckCustomer.php";
    public static final String UPDATE_ACCOUNT_SETTING_EMPLOYEE_CUSTOMER = BASE_URL + "UpdateAccountSettingCustomerEmployee.php";
    public static final String CONTACT_US = BASE_URL + "ContactUs.php";
    public static final String UPDATE_ADDRESS_CUSTOMER_EMPLOYEE = BASE_URL + "UpdateAddressCustomerEmployee.php";
    public static final String UPDATE_PASSWORD_CUSTOMER_EMPLOYEE = BASE_URL + "UpdatePasswordCustomerEmployee.php";
    public static final String INSERT_NEW_ORDER_CUSTOMER = BASE_URL + "InsertNewOrderCustomer.php";
    public static final String RESET_PASSWORD_MAIL_CUSTOMER = BASE_URL + "ResetPasswordMailCustomer.php";
    public static final String ORDER_LIST_ADMIN = BASE_URL + "OrderListAdmin.php";
	public static final String OFFLINE_ORDER_LIST_ADMIN = BASE_URL + "OfflineOrderListAdmin.php";
	public static final String DELETE_FOOD_ITEM_ADMIN = BASE_URL + "DeleteFoodItemAdmin.php";
	public static final String TABLE_LIST_ADMIN_EMPLOYEE = BASE_URL + "TableListAdminEmployee.php";
	public static final String  INSERT_TABLE_ADMIN = BASE_URL + "InsertTableAdmin.php";
	public static final String DELETE_TABLE_ADMIN = BASE_URL + "DeleteTableAdmin.php";
	public static final String UPDATE_TABLE_ADMIN = BASE_URL + "UpdateTableAdmin.php";
	public static final String DELETE_EMPLOYEE_ADMIN = BASE_URL + "DeleteEmployeeAdmin.php";
	public static final String INSERT_FOODITEM_ADMIN = BASE_URL + "InsertFoodItemAdmin.php";
	public static final String UPDATE_FOODITEM_ADMIN = BASE_URL + "UpdateFoodItemAdmin.php";
	public static final String SEND_NOTIFICATION = BASE_URL + "SendNotification.php";
	public static final String UPDATE_FOODITEM_SERVED_EMPLOYEE = BASE_URL + "UpdateFoodItemServedEmployee.php";
	public static final String RESTAURANT_LIST_CUSTOMER = BASE_URL + "RestaurantListCustomer.php";
	public static final String RESTAURANT_OPEN_CLOSE_STATUS = BASE_URL + "RestaurantOpenCloseStatus.php";
    public static final String RESTAURANT_OPEN_CLOSE = BASE_URL + "RestaurantOpenCloseAdmin.php";

    public DatabaseHandler(String link, Context context) {
        this.link = link;
        this.context = context;
    }

    public void execute(){
        queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    writeCode(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("LUND", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("LUND", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params();
            }
        };
        /* To Prevent Double Post Request */
        request.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.getCache().clear();
        queue.add(request);
    }
    public abstract void writeCode(String response) throws  Exception;
    public abstract Map<String, String> params();
}
