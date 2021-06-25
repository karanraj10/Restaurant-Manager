package com.project.restaurantmanager.UI.Admin.OrderReservationModules;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.project.restaurantmanager.Model.AdminActivity;
import com.project.restaurantmanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class generate_report extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate_report, container, false);

        final WebView view1 = view.findViewById(R.id.webview_report);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int monthi = new Date().getMonth();
        int year = new Date().getYear()+1900;

        String month = String.format("%02d",monthi);

        String monthstart = year+"-"+month+"-01";

        Date date = new Date();
        String today = simpleDateFormat.format(date);

        view1.loadUrl("http://192.168.0.114/PHP/TempletRestaurantMonthlyReport.php?rid="
                + AdminActivity.sharedPreferencesHandler.getId()
                +"&start="+monthstart+"&end="+today);

        Button print = view.findViewById(R.id.webview_report_button);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWebPrintJob(view1);
            }
        });


        return view;
    }

    private void createWebPrintJob(WebView webView) {

        //create object of print manager in your device
        PrintManager printManager = (PrintManager) getActivity().getSystemService(Context.PRINT_SERVICE);

        //create object of print adapter
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        //provide name to your newly generated pdf file
        String jobName = getString(R.string.app_name) + " Print Test";

        //open print dialog
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }
}
