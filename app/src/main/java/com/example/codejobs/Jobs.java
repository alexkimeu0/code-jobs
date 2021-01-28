package com.example.codejobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Jobs extends AppCompatActivity {

    SweetAlertDialog errorDialog, successDialog, progressDialog;
    String URL = "https://jobs.github.com/positions.json";
    CardView cardView = findViewById(R.id.jobCard);

    TextView title = (TextView) findViewById(R.id.jobTitle);
    TextView date = findViewById(R.id.date);
    TextView location = findViewById(R.id.location);
    TextView type = findViewById(R.id.type);
    TextView description = findViewById(R.id.description);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jobs);

        // Initialize FastAndroid Networking
        AndroidNetworking.initialize(getApplicationContext());

        // Initialize Alert Dialogs
        errorDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        successDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        // Progress Dialog
        progressDialog.setTitle("Loading jobs");
        progressDialog.setTitleText("Keep calm");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // API call to fetch jobs
        AndroidNetworking.get(URL)
                        .setTag("get")
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("Jobs Results", response.toString());
                                try {
                                    // Loop through jobs
                                    for(int i=0; i< response.length(); i++){
                                        JSONObject jsonObject = response.getJSONObject(i);

                                        String title = jsonObject.getString("title");
                                        String type = jsonObject.getString("type");
                                        String company = jsonObject.getString("company");
                                        String location = jsonObject.getString("location");
                                        String date = jsonObject.getString("date");
                                        String description = jsonObject.getString("description");
                                    }
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

    }
}