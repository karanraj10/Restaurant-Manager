package com.project.restaurantmanager.UI.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Controller.FirebaseMessageService;
import com.project.restaurantmanager.Controller.SharedPreferencesHandler;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.Model.MainActivity;
import com.project.restaurantmanager.R;
import com.project.restaurantmanager.UI.Admin.AccountFragmentModules.manage_account_fragment;
import com.project.restaurantmanager.UI.Customer.AccountFragmentModules.change_password;
import com.project.restaurantmanager.UI.Customer.AccountFragmentModules.help_fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.RESTAURANT_OPEN_CLOSE;
import static com.project.restaurantmanager.Controller.DatabaseHandler.RESTAURANT_OPEN_CLOSE_STATUS;

public class account_fragment extends Fragment {
    private View view;
    private int status;
    private String[] names = new String[]{"Change Password","Account Setting", "Help","Log Out"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_account_fragment,container,false);

        final ListView listView = view.findViewById(R.id.admin_acc_list);


        final CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        final ToggleButton toggleButton = view.findViewById(R.id.admin_acc_toggle);

        DatabaseHandler databaseHandler = new DatabaseHandler(RESTAURANT_OPEN_CLOSE_STATUS,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException, InterruptedException, Exception {
                JSONObject object = new JSONObject(response);
                if(object.getInt("status")==1)
                {
                    toggleButton.setChecked(true);
                }
                else
                {
                    toggleButton.setChecked(false);
                }
            }
            @Override
            public Map<String, String> params() {
                Map<String,String> map = new HashMap<>();
                map.put("rno",AdminActivity.sharedPreferencesHandler.getId());
                return map;
            }
        };
        databaseHandler.execute();

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    setStatus(1);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).
                            setTitle("Closing Restaurant").
                            setMessage("You really want to close your restaurant?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setStatus(0);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    buttonView.setChecked(true);
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        final TextView name = view.findViewById(R.id.admin_acc_name_text);
        name.setText(MainActivity.sharedPreferences.getUsername());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        AdminActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.admin_container,new change_password(),null).commit();
                        break;
                    case 1:
                        AdminActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.admin_container, new manage_account_fragment(), null).commit();
                        break;
                    case 2:
                        AdminActivity.fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.admin_container, new help_fragment(),null).commit();
                        break;
                    case 3:
                        SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(getContext());
                        sharedPreferencesHandler.setFlag(false);
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(intent);
                        FirebaseMessageService.removeTopic("admin",sharedPreferencesHandler.getId());
                        NotificationManager nMgr = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        nMgr.cancelAll();
                        /*To Prevent Unauthorized Login*/
                        getActivity().finish();
                        break;
                }
            }
        });
        return view;
    }
    void setStatus(final int status)
    {
        DatabaseHandler handler = new DatabaseHandler(RESTAURANT_OPEN_CLOSE,getContext()) {
            @Override
            public void writeCode(String response) throws JSONException, InterruptedException, Exception {

            }

            @Override
            public Map<String, String> params() {
                Map<String,String> map = new HashMap<>();
                map.put("rno",AdminActivity.sharedPreferencesHandler.getId());
                map.put("status",status+"");
                return map;
            }
        };
        handler.execute();

    }


    /* List Needs Adapter For Gathering Data,
     Here CustomAdapter Takes Data From name String
     And Bind It To ListView
     */
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listview_account_layout,null);

            TextView settingName = convertView.findViewById(R.id.list_TextView);
            ImageView settingNextImage = convertView.findViewById(R.id.list_imageView);

            settingNextImage.setImageResource(R.drawable.ic_next);
            settingName.setText(names[position]);

            return convertView;
        }
    }
}
