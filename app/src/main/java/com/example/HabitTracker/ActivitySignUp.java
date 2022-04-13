package com.example.HabitTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.HabitTracker.habitos.Habit;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmResults;

public class ActivitySignUp extends AppCompatActivity {

    private EditText et_name;
    private EditText et_email;
    private  EditText et_password;
    private Button bt_send;
    Realm realm;
    RealmResults<Habit> habitList;

    private String ip = "https://styleless-delight.000webhostapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_name = findViewById(R.id.et_usr_name);
        et_email = findViewById(R.id.et_usr_email);
        et_password = findViewById(R.id.et_usr_password);
        bt_send = findViewById(R.id.bt_login2);

        realm = Realm.getDefaultInstance();
        habitList = realm.where(Habit.class).findAll();



        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bt_send.setBackground(getDrawable(R.drawable.rounded_buttom_pressed));
                final String name = et_name.getText().toString();
                final String email = et_email.getText().toString();
                final String pass = et_password.getText().toString();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    if (!name.isEmpty()){
                        if (!pass.isEmpty()){
                            register(ip+"HabitTracker/regist.php");
                        }else{
                            Toast.makeText(ActivitySignUp.this, "Password invalid", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ActivitySignUp.this, "Name invalid", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ActivitySignUp.this, "Email invalid", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void register(String URL){


        final String name = et_name.getText().toString();
        final String email = et_email.getText().toString().toLowerCase();
        final String pass = et_password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {


                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");


                        if (success.equals("1")){
                            Toast.makeText(ActivitySignUp.this, "Registrado", Toast.LENGTH_SHORT).show();

                            cargardatos(ip+"HabitTracker/login.php");

                        }else{
                            Toast.makeText(ActivitySignUp.this, "Email is in use", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = null;
                    if (error instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connectionN!";
                    } else if (error instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                    } else if (error instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connectionA!";
                    } else if (error instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                    } else if (error instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                    }
                    Log.d("InternetFail", message);
                    Toast.makeText(ActivitySignUp.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
                }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("user", name);
                parametros.put("email", email.toLowerCase());
                parametros.put("pass", pass);

                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public static boolean isEmailValid(String email){
        String emailRegex = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        if (email.matches(emailRegex) && email.length() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void cargardatos(String URL) {

        final String name = et_name.getText().toString();
        final String email = et_email.getText().toString().toLowerCase();
        final String pass = et_password.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    if (!response.isEmpty()){
                        final JSONObject jsonObject = new JSONObject(response);
                        final Long userID = jsonObject.getLong("UserID");
                        final String name = jsonObject.getString("Name");
                        final String email = jsonObject.getString("Email").toLowerCase();
                        final String pass = jsonObject.getString("Password");
                        final int level = jsonObject.getInt("Level");
                        final double experience = jsonObject.getDouble("Experience");
                        final int health = jsonObject.getInt("Health");
                        final int money = jsonObject.getInt("Money");

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                User newUser = new User(userID,name, email, pass, level, experience, health, money);
                                realm.copyToRealmOrUpdate(newUser);


                                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(getString(R.string.preferences_name), name);
                                editor.putString(getString(R.string.preferences_id), userID.toString());
                                editor.putString(getString(R.string.preferences_email), email.toLowerCase());
                                editor.putString(getString(R.string.preferences_body),"body");
                                editor.putString(getString(R.string.preferences_price),"price");
                                editor.putString(getString(R.string.preferences_money), money+"");
                                editor.putBoolean(getString(R.string.preferences_islogin), true);
                                editor.commit();

                            }
                        });
                    }else{
                        Toast.makeText(ActivitySignUp.this, "User or password wrong", Toast.LENGTH_SHORT).show();
                    }

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getApplicationContext(), SplashScreenLogin.class);
                            startActivity(intent);


                        }
                    }, 1000);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connectionN!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connectionA!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Log.d("InternetFail", message);
                Toast.makeText(ActivitySignUp.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("user", email);
                parametros.put("pass", pass);

                return parametros;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

}