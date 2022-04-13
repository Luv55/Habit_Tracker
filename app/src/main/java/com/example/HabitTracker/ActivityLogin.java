package com.example.HabitTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.HabitTracker.fragments.FragmentDialogFirstTime;
import com.example.HabitTracker.fragments.FragmentLoadingScreen;
import com.example.HabitTracker.habitos.Habit;
import com.example.HabitTracker.interfaces.OnSplashScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;

public class ActivityLogin extends AppCompatActivity implements OnSplashScreen {

    private EditText name, password;
    private Button bt_login;
    private String ip = "https://styleless-delight.000webhostapp.com/";
    Realm realm;
    DialogFragment Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.et_log_name);
        password = findViewById(R.id.et_log_password);
        bt_login = findViewById(R.id.bt_login2);

        realm = Realm.getDefaultInstance();

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bt_login.setBackground(getDrawable(R.drawable.rounded_buttom_pressed));

                validateUser(ip+"HabitTracker/login.php");
            }
        });
    }

    private void validateUser(String URL) {


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

                                getCharacter(ip+"HabitTracker/getCharacters.php", userID);
                                getTags(ip+"HabitTracker/getTags.php", userID);
                                getPurchases(ip+"HabitTracker/getPurchases.php", userID);
                                getHabits(ip+"HabitTracker/getHabits.php", userID);

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
                        Toast.makeText(ActivityLogin.this, getText(R.string.login), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ActivityLogin.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("user", name.getText().toString());
                parametros.put("pass", password.getText().toString());

                return parametros;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void getCharacter(String URL, final Long UserID) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    final Long ID = jsonObject.getLong("ID");
                    final String face = jsonObject.getString("Face");
                    final String body = jsonObject.getString("Body");
                    final String hair = jsonObject.getString("Hair");

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Characters newCharacter = new Characters(ID, face, body, hair, UserID);

                            realm.copyToRealmOrUpdate(newCharacter);
                        }
                    });

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
                Toast.makeText(ActivityLogin.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UserID", UserID.toString());

                return parametros;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void getTags(String URL, final Long UserID) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    final Long ID = jsonObject.getLong("ID");
                    final int social = jsonObject.getInt("Social");
                    final int sports = jsonObject.getInt("Sports");
                    final int diet = jsonObject.getInt("Diet");
                    final int studies = jsonObject.getInt("Studies");
                    final int wellbeing = jsonObject.getInt("Wellbeing");
                    final int meditation = jsonObject.getInt("Meditation");

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Tags newTag = new Tags();
                            newTag.setId(ID);
                            newTag.setWellbeing(wellbeing);
                            newTag.setMeditation(meditation);
                            newTag.setStudies(studies);
                            newTag.setSports(sports);
                            newTag.setSocial(social);
                            newTag.setDiet(diet);
                            newTag.setUserId(UserID);

                            realm.copyToRealmOrUpdate(newTag);
                        }
                    });

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
                Toast.makeText(ActivityLogin.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UserID", UserID.toString());

                return parametros;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void getHabits(String URL, final Long UserID) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    final JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        final long a  = 1000+i;
                        final JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final Long ID = jsonObject.getLong("ID");
                        final String name = jsonObject.getString("Name");
                        final String description = jsonObject.getString("Description");
                        final String difficulty = jsonObject.getString("Difficulty");
                        final String tag = jsonObject.getString("Tag");
                        final String streakReach = jsonObject.getString("StreakReach");
                        final int streak = jsonObject.getInt("Streak");
                        final String time = jsonObject.getString("Date");
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Habit newHabit = new Habit();
                                newHabit.setId(a);
                                newHabit.setName(name);
                                newHabit.setDescription(description);
                                newHabit.setDifficulty(difficulty);
                                newHabit.setTag(tag);
                                newHabit.setStreakReach(streakReach);
                                newHabit.setStreak(streak);
                                newHabit.setUserId(UserID);
                                newHabit.setTime(time);

                                realm.copyToRealmOrUpdate(newHabit);
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(intent);
                intent.putExtra("activity", "login");

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
                Toast.makeText(ActivityLogin.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UserID", UserID.toString());

                return parametros;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    private void getPurchases(String URL, final Long UserID) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    final JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++) {
                        final long a = 1000 + i;
                        final JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final String name = jsonObject.getString("Name");
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Purchases purchases = new Purchases(a, name, UserID);

                                realm.copyToRealmOrUpdate(purchases);
                            }
                        });
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
                Toast.makeText(ActivityLogin.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UserID", UserID.toString());

                return parametros;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }



    @Override
    public boolean onLoading(boolean a) {

        Loading.dismiss();


        return false;
    }
}