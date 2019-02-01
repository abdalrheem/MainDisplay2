package com.example.abdalrheem.maindisplay;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class License extends AppCompatActivity {
    private RequestQueue requestQueue;
    static String check="F";
    static SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);pref= PreferenceManager.getDefaultSharedPreferences(this);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
        String check1 =pref.getString("verify","");
        if(check1.equals("DON")){
            Intent intent = new Intent(License.this, MainActivity.class);
            startActivity(intent);
        }

    }
    public void GetLicense(View view) {
        //check = check.trim();
        if(check.equals("DON")){
            Intent intent = new Intent(License.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            String servIP = "http://arabian-computer.com/QueuLicense/lic.php";
            requestQueue = Volley.newRequestQueue(License.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, servIP, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    check=response.trim();
                    Toast.makeText(License.this, check, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("verify", check);
                    editor.apply();
                    requestQueue.stop();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    requestQueue.stop();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("id", check);
                    return params;
                }

            };
            requestQueue.add(stringRequest);
        }

    }

}
