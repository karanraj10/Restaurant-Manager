package com.project.restaurantmanager.UI.Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class resetpassword_fragment extends Fragment {

    String email,username;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.resetpassword_fragment,container,false);


        final TextInputEditText emailEditText = view.findViewById(R.id.reset_email);
        final TextInputEditText usernameEditText = view.findViewById(R.id.reset_username);
        final Button resetBtn = view.findViewById(R.id.reset_ResetBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email= emailEditText.getText().toString();
                username = usernameEditText.getText().toString();
                if(email.isEmpty()){
                    TextInputLayout layout = view.findViewById(R.id.reset_textlayout_1);
                    layout.setError("Enter Email");
                }
                else {
                    DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.RESET_PASSWORD_CUSTOMER, getContext()) {
                        @Override
                        public void writeCode(String response) throws JSONException {
                            JSONObject object = new JSONObject(response);
                            String result = object.getString("result");
                            if (result.equals("pass")) {
                                String post = object.getString("post");
                                MainActivity.resetflag = true;
                                MainActivity.fragmentManager.popBackStack();
                                sendMail(email, post);
                            } else if (result.equals("fail")) {
                                Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                            }
                            else if(result.equals("fail_user"))
                            {
                                Toast.makeText(getContext(), "Enter Correct Username!", Toast.LENGTH_SHORT).show();
                            }
                            else if(result.equals("fail_email"))
                            {
                                Toast.makeText(getContext(), "Enter Correct Email!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public Map<String, String> params() {
                            Map<String, String> map = new HashMap<>();
                            map.put("username",username);
                            map.put("email", email);
                            return map;
                        }

                    };
                    databaseHandler.execute();

                }
            }
        });
        return  view;
    }
    public void sendMail(final String email, final String post){

        DatabaseHandler databaseHandler = new DatabaseHandler(DatabaseHandler.RESET_PASSWORD_MAIL_CUSTOMER,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException {

            }
            @Override
            public Map<String, String> params() {
                Map<String,String > map = new HashMap<>();
                map.put("email",email);
                map.put("post",post);
                return map;
            }
        };
        databaseHandler.execute();
    }
}


