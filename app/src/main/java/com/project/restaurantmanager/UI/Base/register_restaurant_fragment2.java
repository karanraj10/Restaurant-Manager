package com.project.restaurantmanager.UI.Base;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Data.Constants;
import com.project.restaurantmanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.REGISTER_RESTAURANT;
import static com.project.restaurantmanager.UI.Base.register_restaurant_fragment.*;


public class register_restaurant_fragment2 extends Fragment {

    private static final int FILE_SELECT_CODE = 02013 ;
    TextView to;
    TextInputLayout start,end;
    TextInputEditText startime;
    TextInputEditText endtime;
    TextInputEditText name;
    CheckBox allowreservation;
    ImageButton imageView;
    int ropt,stime,etime;

    Button signup;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private String encoded ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_restaurant_fragment2, container, false);

        allowreservation = view.findViewById(R.id.reg_res_allowres);

        to = view.findViewById(R.id.reg_res_tolabel);
        start = view.findViewById(R.id.reg_res_textlayout_8);
        end = view.findViewById(R.id.reg_res_textlayout_9);

        startime = view.findViewById(R.id.reg_res_starttime);
        startime.setActivated(false);
        endtime = view.findViewById(R.id.reg_res_endtime);

        name = view.findViewById(R.id.reg_res_name);

        signup = view.findViewById(R.id.reg_res_RegisterBtn);

        imageView = view.findViewById(R.id.reg_res_image);

        allowReservation();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                Intent chooser = Intent.createChooser(intent, "Choose a Picture");
                startActivityForResult(chooser, FILE_SELECT_CODE);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler handler = new DatabaseHandler(REGISTER_RESTAURANT, getContext()) {
                    @Override
                    public void writeCode(String response) throws  Exception {
                        JSONObject object = new JSONObject(response);
                        if (object.getInt("error") == 0) {
                            Toast.makeText(getContext(), Constants.getError0, Toast.LENGTH_SHORT).show();
                        } else if (object.getInt("error") == 1) {
                            Toast.makeText(getContext(), Constants.getError1, Toast.LENGTH_SHORT).show();
                            getActivity().recreate();
                        } else if (object.getInt("error") == 2) {
                            Toast.makeText(getContext(), Constants.getError2, Toast.LENGTH_SHORT).show();
                        } else if (object.getInt("error") == 3) {
                            Toast.makeText(getContext(), Constants.getError3, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public Map<String, String> params() {
                        Map<String, String> map = new HashMap<>();

                        map.put("username", mData.get("username"));
                        map.put("password", mData.get("password"));
                        map.put("email", mData.get("email"));
                        map.put("name", name.getText().toString());
                        map.put("contact", mData.get("mobile"));
                        map.put("address", mData.get("address"));
                        map.put("location", mData.get("location"));
                        map.put("GSTIN", mData.get("gstin"));
                        map.put("image", encoded);
                        map.put("roption", String.valueOf(ropt));

                        if(ropt==1) {
                            map.put("starttime", stime+"");
                            map.put("endtime", etime+"");
                        }
                        else
                        {
                            map.put("starttime", "0");
                            map.put("endtime", "0");
                        }

                        Log.d("gggg", "params: "+map.toString());

                        return map;
                    }
                };

                List<String> values = new ArrayList<>();
                values.add(encoded);
                values.add(name.getText().toString());

                if(values.contains(""))
                {
                    Toast.makeText(getContext(), Constants.getError0, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    handler.execute();
                    values.clear();
                }
            }
        });

        return view;
    }

    void allowReservation()
    {
        startime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                stime=hourOfDay;
                                startime.setText(String.format("%02d", hourOfDay)+ ":" + String.format("%02d",0));
                            }
                        }, mHour, 00, true);
                timePickerDialog.show();
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                etime=hourOfDay;
                                endtime.setText(String.format("%02d", hourOfDay)+ ":" + String.format("%02d",0));
                            }
                        }, mHour, 00, true);
                timePickerDialog.show();
            }
        });

        allowreservation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ropt=1;
                    to.setVisibility(View.VISIBLE);
                    start.setVisibility(View.VISIBLE);
                    end.setVisibility(View.VISIBLE);
                }
                else
                {
                    ropt=0;
                    to.setVisibility(View.INVISIBLE);
                    start.setVisibility(View.INVISIBLE);
                    end.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == -1) {
                Uri uri = data.getData();

                imageView.setImageURI(uri);

                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

                Bitmap image = drawable.getBitmap();
                image = Bitmap.createScaledBitmap(image, 256, 256, false);

                imageView.setImageBitmap(image);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] arry = outputStream.toByteArray();

                encoded = Base64.encodeToString(arry, 0, arry.length, Base64.DEFAULT);

            }
        }

    }

}
