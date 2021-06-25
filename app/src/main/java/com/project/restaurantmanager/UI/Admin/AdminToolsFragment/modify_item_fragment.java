package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.UPDATE_FOODITEM_ADMIN;
import static com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_food.bitmapDB;
import static com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_food.nameDB;
import static com.project.restaurantmanager.UI.Admin.AdminToolsFragment.manage_food.priceDB;


public class modify_item_fragment extends Fragment {
    View view;
    int FILE_SELECT_CODE =3242;
    String encoded;
    ImageView imageView;
    Boolean flag=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_food_add_modify,container,false);

        TextView textView = view.findViewById(R.id.admin_add_food_text1);
        textView.setText("Modify Item");

        final Button modfiy = view.findViewById(R.id.admin_add_food_RegisterBtn);
        modfiy.setText("Modify");
        modfiy.setEnabled(false);


        imageView= view.findViewById(R.id.admin_add_food_imageV);
       imageView.setImageBitmap(bitmapDB);
       final TextInputEditText name = view.findViewById(R.id.admin_add_food_name);
       name.setText(nameDB);  name.setEnabled(false);
       final TextInputEditText price = view.findViewById(R.id.admin_add_food_price);
       price.setText(priceDB);

        Button selector = view.findViewById(R.id.admin_add_food_select);

        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                Intent chooser = Intent.createChooser(intent, "Choose a Picture");
                startActivityForResult(chooser,FILE_SELECT_CODE);

                modfiy.setEnabled(true);
                flag=true;
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                modfiy.setEnabled(true);
            }
        });

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap image = drawable.getBitmap();
        image = Bitmap.createScaledBitmap(image,256,256,false);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] arry = outputStream.toByteArray();

        final String local  = Base64.encodeToString(arry,0,arry.length,Base64.DEFAULT);


        modfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){encoded=local;}
                DatabaseHandler databaseHandler = new DatabaseHandler(UPDATE_FOODITEM_ADMIN,getContext()) {
                    @Override
                    public void writeCode(String response) throws Exception {
                        Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    @Override
                    public Map<String, String> params() {

                        Map<String,String> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("image",encoded);
                        Log.d("image", "params: "+encoded);
                        return map;
                    }
                };
                databaseHandler.execute();
            }
        });



        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==FILE_SELECT_CODE)
        {
            if(resultCode==-1) {
                Uri uri = data.getData();

                imageView.setImageURI(uri);


                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

                Bitmap image = drawable.getBitmap();
                image = Bitmap.createScaledBitmap(image,256,256,false);

                imageView.setImageBitmap(image);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                byte[] arry = outputStream.toByteArray();
                encoded = Base64.encodeToString(arry,0,arry.length,Base64.DEFAULT);


            }
        }
    }
}
