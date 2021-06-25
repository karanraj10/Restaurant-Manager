package com.project.restaurantmanager.UI.Admin.CheckOutModules;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.restaurantmanager.Controller.DatabaseHandler;
import com.project.restaurantmanager.R;

import java.util.HashMap;
import java.util.Map;

import static com.project.restaurantmanager.Controller.DatabaseHandler.INVOICE_TO_CUSTOMER_ADMIN;
import static com.project.restaurantmanager.UI.Admin.checkout_fragment.ono;

public class invoice_fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_invoice_fragment,container,false);

        WebView webView = view.findViewById(R.id.admin_invoice_webview);

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://192.168.0.114/PHP/InvoiceTempletForWebViewAdmin.php?ono="+ono+"+&email="+"");
        return view;
    }
}
