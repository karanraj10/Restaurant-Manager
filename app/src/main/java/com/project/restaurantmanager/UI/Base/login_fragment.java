package com.project.restaurantmanager.UI.Base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.Model.CustomerActivity;
import com.project.restaurantmanager.Model.EmployeeActivity;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class login_fragment extends Fragment {
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.login_fragment,container,false);

       /*Toast For Password Reset*/
       if(MainActivity.resetflag){
           Toast.makeText(getContext(),"Password Reset Successfully",Toast.LENGTH_SHORT).show();
           MainActivity.resetflag=false;
       }

       final TextInputEditText username = view.findViewById(R.id.login_username);
       final TextInputEditText password = view.findViewById(R.id.login_password);

       final Button loginBtn = view.findViewById(R.id.login_LoginBtn);

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(final View v) {

               if(username.getText().toString().isEmpty()){
                   TextInputLayout emailLayout = view.findViewById(R.id.login_textlayout_1);
                   emailLayout.setError("");
                   TextInputLayout passwordLayout = view.findViewById(R.id.login_textlayout_2);
                   passwordLayout.setError("Enter Username And Password");
               }

               DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.LOGIN,getContext()) {
                   @Override
                   public void writeCode(String response) throws JSONException {
                       Log.d("LUND", ""+response);
                       JSONObject object = new JSONObject(response);
                       String error,post,name,mobile,address,email,id;
                       error=object.getString("error");

                       if(Integer.parseInt(error)==2){
                           TextInputLayout emailLayout = view.findViewById(R.id.login_textlayout_1);
                           emailLayout.setError("  ");
                           TextInputLayout passwordLayout = view.findViewById(R.id.login_textlayout_2);
                           passwordLayout.setError(" Wrong Username Or Password");
                       }
                       else if(Integer.parseInt(error)==0){
                           TextInputLayout emailLayout = view.findViewById(R.id.login_textlayout_1);
                           emailLayout.setError("  ");
                           TextInputLayout passwordLayout = view.findViewById(R.id.login_textlayout_2);
                           passwordLayout.setError("Please Provide Information!");
                       }
                       else if(Integer.parseInt(error)==1){
                           post = object.getString("post");
                           name = object.getString("name");
                           mobile=object.getString("mobile");
                           address = object.getString("address");
                           email = object.getString("email");
                           id = object.getString("id");
                           if (post.equals("Customer")) {
                               MainActivity.sharedPreferences.setUsername(username.getText().toString());
                               MainActivity.sharedPreferences.setFlag(true);
                               MainActivity.sharedPreferences.setrName(name);
                               MainActivity.sharedPreferences.setEmail(email);
                               MainActivity.sharedPreferences.setPost(post);
                               MainActivity.sharedPreferences.setMobile(mobile);
                               MainActivity.sharedPreferences.setAddress(address);
                               MainActivity.sharedPreferences.setId(id);

                               registerTopic("customer");

                               intent = new Intent(getActivity(), CustomerActivity.class);

                           } else if(post.equals("Admin")) {
                               MainActivity.sharedPreferences.setUsername(username.getText().toString());
                               MainActivity.sharedPreferences.setFlag(true);
                               MainActivity.sharedPreferences.setrName(name);
                               MainActivity.sharedPreferences.setEmail(email);
                               MainActivity.sharedPreferences.setPost(post);
                               MainActivity.sharedPreferences.setMobile(mobile);
                               MainActivity.sharedPreferences.setAddress(address);
                               MainActivity.sharedPreferences.setId(id);

                               registerTopic("admin");

                               intent = new Intent(getActivity(), AdminActivity.class);

                           }
                           else if(post.equals("Employee")){
                               MainActivity.sharedPreferences.setFlag(true);
                               MainActivity.sharedPreferences.setrName(name);
                               MainActivity.sharedPreferences.setEmail(email);
                               MainActivity.sharedPreferences.setUsername(username.getText().toString());
                               MainActivity.sharedPreferences.setPost(post);
                               MainActivity.sharedPreferences.setMobile(mobile);
                               MainActivity.sharedPreferences.setAddress(address);
                               MainActivity.sharedPreferences.setRid(object.getString("rid"));
                               MainActivity.sharedPreferences.setId(id);

                               registerTopic("employee");

                               intent = new Intent(getActivity(), EmployeeActivity.class);

                           }
                           getActivity().startActivity(intent);

                       }
                   }
                   @Override
                   public Map<String, String> params() {
                       Map<String, String> map = new HashMap<>();
                       map.put("username", Objects.requireNonNull(username.getText()).toString());
                       map.put("password", Objects.requireNonNull(password.getText()).toString());
                       return map;
                   }
               };
               databaseHandler.execute();

           }
       });

       TextView resetTextView = view.findViewById(R.id.login_reset);

       resetTextView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MainActivity.fragmentManager.beginTransaction().addToBackStack(null).
                       replace(R.id.base_container,new resetpassword_fragment(),null).commit();
           }
       });
        return view;
    }

    private void registerTopic(String user) {
        final String topic = user + MainActivity.sharedPreferences.getId();
        FirebaseMessaging.getInstance().subscribeToTopic(topic);
    }
}
