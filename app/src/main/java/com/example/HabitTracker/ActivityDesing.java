package com.example.HabitTracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.HabitTracker.ui.characterBody.CharacterBodyFragment;
import com.example.HabitTracker.ui.characterFace.CharacterFaceFragment;
import com.example.HabitTracker.ui.characterHair.CharacterHairFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

public class ActivityDesing extends AppCompatActivity implements CharacterFaceFragment.OnCharacterFaceReadListener, CharacterBodyFragment.OnCharacterBodyReadListener , CharacterHairFragment.OnCharacterHairReadListener {

    private ImageView iv_face;
    private ImageView iv_body;
    private ImageView iv_hair;
    private TextView tv_save;
    private String ip = "https://styleless-delight.000webhostapp.com/";
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desing);

        Toolbar toolbar2 = findViewById(R.id.toolbarDesing);
        setSupportActionBar(toolbar2);

        BottomNavigationView navView = findViewById(R.id.nav_view2);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_face, R.id.navigation_body, R.id.navigation_hair)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
        NavigationUI.setupWithNavController(navView, navController);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);
        final Long userID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));


        realm = Realm.getDefaultInstance();

        iv_body = findViewById(R.id.iv_body);
        iv_face = findViewById(R.id.iv_face);
        iv_hair = findViewById(R.id.iv_hair);

        iv_body.setImageDrawable(getResources().getDrawable(R.drawable.body_yellow));
        iv_body.setTag("body_yellow");
        iv_face.setImageDrawable(getResources().getDrawable(R.drawable.face5));
        iv_face.setTag("face5");
        iv_hair.setImageDrawable(getResources().getDrawable(R.drawable.hair_black));
        iv_hair.setTag("hair_black");

        tv_save = findViewById(R.id.tv_save);

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCharacter(ip+"HabitTracker/setCharacters.php",iv_face.getTag()+"", iv_body.getTag()+"", iv_hair.getTag()+"", userID);

                setTags(ip+"HabitTracker/setTags.php", userID);

            }
        });

    }

    private void setCharacter(String URL, final String face, final String body, final String hair, final Long userID){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(ActivityDesing.this, getText(R.string.registered), Toast.LENGTH_SHORT).show();

                                getCharacter(ip+"HabitTracker/getCharacters.php", userID);
                            }else{
                                Toast.makeText(ActivityDesing.this, getText(R.string.failedRegister), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ActivityDesing.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("face", face);
                parametros.put("body", body);
                parametros.put("hair", hair);
                parametros.put("UserID", userID.toString());

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
                Toast.makeText(ActivityDesing.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
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

    private void setTags(String URL, final Long userID){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){

                                getTags(ip+"HabitTracker/getTags.php", userID);
                            }else{
                                Toast.makeText(ActivityDesing.this, getText(R.string.failedRegister), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ActivityDesing.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("studies", "0");
                parametros.put("social", "0");
                parametros.put("diet", "0");
                parametros.put("wellbeing", "0");
                parametros.put("sports", "0");
                parametros.put("meditation", "0");
                parametros.put("UserID", userID.toString());

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

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getApplicationContext(), ActivityHome.class);
                            startActivity(intent);


                        }
                    }, 2000);

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
                Toast.makeText(ActivityDesing.this, getText(R.string.failInternet), Toast.LENGTH_SHORT).show();
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
    public void onFaceRead(Drawable drawable, String string) {
        iv_face.setImageDrawable(drawable);
        iv_face.setTag(string);
    }

    @Override
    public void onBodyRead(Drawable drawable, String string) {

        iv_body.setImageDrawable(drawable);
        iv_body.setTag(string);
    }

    @Override
    public void onHairRead(Drawable drawable, String string) {
        iv_hair.setImageDrawable(drawable);
        iv_hair.setTag(string);
    }

    @Override
    public void onBackPressed() {

    }
}
