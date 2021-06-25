package com.project.restaurantmanager.UI.Admin.AdminToolsFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.INSERT_FOODITEM_ADMIN;

public class add_food_fragment extends Fragment {
    View view;
    Button selector;
    int FILE_SELECT_CODE=023432;
    String encoded;
    ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_food_add_modify,container,false);

        imageView = view.findViewById(R.id.admin_add_food_imageV);
        final TextInputEditText name = view.findViewById(R.id.admin_add_food_name);
        final TextInputEditText price = view.findViewById(R.id.admin_add_food_price);

        selector = view.findViewById(R.id.admin_add_food_select);
        selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                Intent chooser = Intent.createChooser(intent, "Choose a Picture");
                startActivityForResult(chooser,FILE_SELECT_CODE);
            }
        });

        Button add = view.findViewById(R.id.admin_add_food_RegisterBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler handler = new DatabaseHandler(INSERT_FOODITEM_ADMIN,getContext()) {
                    @Override
                    public void writeCode(String response) throws  Exception {
                        JSONObject object = new JSONObject(response);
                        if(object.getString("result").equals("pass"))
                        {
                            getActivity().getSupportFragmentManager().popBackStack();
                            Toast.makeText(getContext(), "Item Succesfully Added!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public Map<String, String> params() {
                        Map<String,String> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("image",encoded);
                        map.put("Rid", AdminActivity.sharedPreferencesHandler.getId());
                        return map;
                    }
                };
                handler.execute();

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
