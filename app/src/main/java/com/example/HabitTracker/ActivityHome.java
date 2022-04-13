package com.example.HabitTracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.HabitTracker.fragments.EditHabitDialogFragment;
import com.example.HabitTracker.fragments.FragmentBuyClothes;
import com.example.HabitTracker.fragments.FragmentDialogFirstTime;
import com.example.HabitTracker.fragments.NewAlarmDialog;
import com.example.HabitTracker.fragments.NewHabitDialog;
import com.example.HabitTracker.fragments.FragmentDialogUserDie;
import com.example.HabitTracker.habitos.Habit;
import com.example.HabitTracker.interfaces.OnHabitInteractionListener;
import com.example.HabitTracker.interfaces.OnNewHabitListener;
import com.example.HabitTracker.ui.body.BodyCustomFragment;
import com.example.HabitTracker.ui.face.FaceCustomFragment;
import com.example.HabitTracker.ui.hair.HairCustomFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class ActivityHome extends AppCompatActivity implements OnHabitInteractionListener, OnNewHabitListener, FaceCustomFragment.OnCustomFaceReadListener, HairCustomFragment.OnCustomHairReadListener, BodyCustomFragment.OnCustomBodyReadListener {

    private AppBarConfiguration mAppBarConfiguration;

    DialogFragment dialogNewHabit, dialogEditHabit, FragmentDialogUserDie, FragmentBuyClothes, dialogNewAlarm, FragmentDialogFirstTime;
    Realm realm;
    RealmResults<Characters> charactersList;
    RealmResults<User> usersList;
    RealmResults<Tags> tagsList;
    RealmResults<Habit> habitList;
    RealmResults<Purchases> purchasesList;
    ProgressBar progressBarLevel;
    ProgressBar progressBarHealt;
    private long tagId;
    private int meditation;
    private int social;
    private int sports;
    private int diet;
    private int wellbeing;
    private int studies;
    private long tagUserID;

    private Long userID;
    private String name;
    private String email;
    private String password;
    private int level;
    private double experience;
    private int health;
    private int money;

    private TextView  tv_level;
    private TextView  tv_money;

    private ImageView faceNav;
    private ImageView bodyNav;
    private ImageView hairNav;
    private ImageView faceHome;
    private ImageView bodyHome;
    private ImageView hairHome;

    private String s_face;
    private String s_body;
    private String s_hair;

    ConstraintLayout content_user_data;

    private String ip = "https://styleless-delight.000webhostapp.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();

        content_user_data = findViewById(R.id.content_user_data);


        //content_user_data.setVisibility(View.GONE);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_habits,R.id.nav_stats, R.id.nav_avatarCustom)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ActivityHome.this, navigationView.getCheckedItem().toString().equals("Stats")+"", Toast.LENGTH_SHORT).show();
                if ((navigationView.getCheckedItem().toString().equals("Alarms")) || navigationView.getCheckedItem().toString().equals("Alarmas")){
                    dialogNewAlarm = new NewAlarmDialog();
                    dialogNewAlarm.show(getSupportFragmentManager(), "dialogNewAlarm");
                }else{
                    dialogNewHabit = new NewHabitDialog();
                    dialogNewHabit.show(getSupportFragmentManager(),"dialogNewHabit");
                }
            }
        });

        TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_user_name);
        TextView email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_user_email);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        int money = Integer.parseInt(sharedPref.getString(getString(R.string.preferences_money),"money"));
        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));
        name.setText(sharedPref.getString(getString(R.string.preferences_name),"user"));
        email.setText(sharedPref.getString(getString(R.string.preferences_email),"email"));

        boolean firstTime = sharedPref.getBoolean(getString(R.string.preferences_firstTime),true);

        if (firstTime){
            SharedPreferences sharedPref2 = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref2.edit();
            editor.putBoolean(getString(R.string.preferences_firstTime), false);
            editor.commit();


            FragmentDialogFirstTime = new FragmentDialogFirstTime();
            FragmentDialogFirstTime.show(getSupportFragmentManager(),"FragmentDialogFirstTime");
        }

        faceNav = navigationView.getHeaderView(0).findViewById(R.id.iv_facea);
        bodyNav = navigationView.getHeaderView(0).findViewById(R.id.iv_bodya);
        hairNav = navigationView.getHeaderView(0).findViewById(R.id.iv_haira);

        faceHome = findViewById(R.id.iv_faceb);
        bodyHome = findViewById(R.id.iv_bodyb);
        hairHome = findViewById(R.id.iv_hairb);

        charactersList = realm.where(Characters.class).findAll();
        s_face = charactersList.get(0).getFace();
        s_body = charactersList.get(0).getBody();
        s_hair = charactersList.get(0).getHair();


        int face1 = getResources().getIdentifier(s_face, "drawable", getPackageName());
        Drawable drawable1 = getResources().getDrawable(face1);

        faceNav.setImageDrawable(drawable1);
        faceHome.setImageDrawable(drawable1);

        int body1 = getResources().getIdentifier(s_body, "drawable", getPackageName());
        Drawable drawable2 = getResources().getDrawable(body1);

        bodyNav.setImageDrawable(drawable2);
        bodyHome.setImageDrawable(drawable2);

        int hair1 = getResources().getIdentifier(s_hair, "drawable", getPackageName());
        Drawable drawable3 = getResources().getDrawable(hair1);

        hairNav.setImageDrawable(drawable3);
        hairHome.setImageDrawable(drawable3);

        tv_level = findViewById(R.id.tv_level);
        tv_money = findViewById(R.id.tv_money);

        usersList = realm.where(User.class).findAll();
        tv_level.setText(getText(R.string.level)+" "+usersList.get(0).getLevel());
        tv_money.setText(money+"");

        progressBarHealt = findViewById(R.id.pb_healt);
        progressBarLevel = findViewById(R.id.pb_level);

        progressBarLevel.setMax(100);
        progressBarLevel.setProgress((int) usersList.get(0).getExperience());

        progressBarHealt.setMax(50);
        progressBarHealt.setProgress(usersList.get(0).getHealth());



    }
    //SYNC

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onHabitClick(Habit mItem) {
        //Toast.makeText(this, mItem.getId() +" "+ mItem.getName() +" "+ mItem.getDescription() +" "+ mItem.getDifficulty() +" "+ mItem.getTag() +" "+ mItem.getStreakReach() +" "+ mItem.getStreak() +" "+ mItem.getUserId() +" "+ mItem.getTime(), Toast.LENGTH_SHORT).show();
        dialogEditHabit = EditHabitDialogFragment.newInstance(mItem.getId(), mItem.getName(), mItem.getDescription(), mItem.getDifficulty(), mItem.getTag(), mItem.getStreakReach(), mItem.getStreak(), mItem.getUserId(), mItem.getTime());
        dialogEditHabit.show(getSupportFragmentManager(),"EditHabitDialog");
    }

    @Override
    public void onHabitRemove(long habitID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Habit habit = realm.where(Habit.class).equalTo(Habit.HABIT_ID,habitID).findFirst();
        builder.setTitle(getString(R.string.deleteHabit));
        builder.setMessage(getString(R.string.DeleteHabitQuestion)+" "+habit.getName()+" ?");

        builder.setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                deleteHabit(ip+"HabitTracker/deleteHabits.php", habit.getUserId(), habit.getTime());

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Habit deleteHabit = habit;
                        deleteHabit.deleteFromRealm();
                    }
                });

                dialog.dismiss();

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);
        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));

        //noinspection SimplifiableIfStatement
        if (id == R.id.sync) {

            synchronize(UserID);
            return true;
        }
        if (id == R.id.nav_game){
            Intent i = new Intent(getApplicationContext(), GameScreenActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHabitSaveClickListener(final String name, final String description, final String difficulty, final String tag,  final String streakReach,  final int streak, final long userID, final String time) {

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));

        if (isInternetAvailable(getApplicationContext())){
            setHabit(ip+"HabitTracker/setHabits.php", name, description, difficulty, tag, streakReach, streak, UserID, time);
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Habit newHabit = new Habit(name, description, difficulty, tag, streakReach, streak, UserID, time);

                realm.copyToRealm(newHabit);
            }
        });
    }

    @Override
    public void onHabitUpdateClickListener(final long id,final String name,final String description,final String difficulty,final String tag, final String streakReach, final int streak, final long userID, final String time) {

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));

        if (isInternetAvailable(getApplicationContext())){
            setHabit(ip+"HabitTracker/setHabits.php", name, description, difficulty, tag, streakReach, streak, UserID, time);
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Habit newHabit = new Habit(id, name, description, difficulty, tag, streakReach, streak, UserID, time);


                realm.copyToRealmOrUpdate(newHabit);
            }
        });
    }

    @Override
    public void onAddListener(final Habit mItem) {

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));
        int money = Integer.parseInt(sharedPref.getString(getString(R.string.preferences_money),"0"));

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Habit newHabit = new Habit();
                newHabit.setId(mItem.getId());
                newHabit.setName(mItem.getName());
                newHabit.setDescription(mItem.getDescription());
                newHabit.setDifficulty(mItem.getDifficulty());
                newHabit.setTag(mItem.getTag());
                newHabit.setStreakReach(mItem.getStreakReach());
                newHabit.setStreak(mItem.getStreak()+1);
                newHabit.setUserId(mItem.getUserId());
                newHabit.setTime(mItem.getTime());

                realm.copyToRealmOrUpdate(newHabit);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setHabit(ip+"HabitTracker/setHabits.php", mItem.getName(), mItem.getDescription(), mItem.getDifficulty(), mItem.getTag(), mItem.getStreakReach(), mItem.getStreak()+1, mItem.getUserId(), mItem.getTime());
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tagsList = realm.where(Tags.class).findAll();

                for (Tags tags : tagsList){
                    if (tags.getUserId() == UserID){
                        tagId = tags.getId();
                        meditation = tags.getMeditation();
                        social = tags.getSocial();
                        sports = tags.getSports();
                        diet = tags.getDiet();
                        wellbeing = tags.getWellbeing();
                        studies = tags.getStudies();
                        tagUserID = tags.getUserId();
                    }
                }
            }
        });


        switch (mItem.getTag()){
            case "studies":
                studies += 1;
                break;
            case "social":
                social += 1;
                break;
            case "sports":
                sports += 1;
                break;
            case "wellbeing":
                wellbeing += 1;
                break;
            case "diet":
                diet += 1;
                break;
            case "meditation":
                meditation += 1;
                break;
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Tags newTag = new Tags();
                newTag.setId(tagId);
                newTag.setWellbeing(wellbeing);
                newTag.setMeditation(meditation);
                newTag.setStudies(studies);
                newTag.setSports(sports);
                newTag.setSocial(social);
                newTag.setDiet(diet);
                newTag.setUserId(tagUserID);

                realm.copyToRealmOrUpdate(newTag);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setTags(ip+"HabitTracker/setTags.php", tagUserID, studies+"", social+"", diet+"", wellbeing+"", sports+"", meditation+"");
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                usersList = realm.where(User.class).findAll();

                for (User user : usersList){
                    if (user.getId() == UserID){
                        userID = user.getId();
                        name = user.getName();
                        email = user.getEmail();
                        password = user.getPassword();
                        level = user.getLevel();
                        experience = user.getExperience();
                        health = user.getHealth();

                    }
                }
            }
        });

        double experienciaImp = 0;
        double monedasImp = 0;

        int levelporgress = progressBarLevel.getProgress();


            switch (mItem.getDifficulty()){
                case "1":
                    switch (mItem.getStreakReach()){
                        case "1":
                            experience += 5.0;
                            progressBarLevel.setProgress(levelporgress+5);
                            money += 2;
                            experienciaImp = 5.0;
                            monedasImp = 2;
                            break;
                        case "2":
                            experience += 4.0;
                            progressBarLevel.setProgress(levelporgress+4);
                            money += 2;
                            experienciaImp = 4.0;
                            monedasImp = 2;
                            break;
                        case "3":
                            experience += 3.0;
                            progressBarLevel.setProgress(levelporgress+3);
                            money += 1;
                            experienciaImp = 3.0;
                            monedasImp = 1;
                            break;
                    }
                    break;
                case "2":
                    switch (mItem.getStreakReach()){
                        case "1":
                            experience += 10.0;
                            progressBarLevel.setProgress(levelporgress+10);
                            money += 3;
                            experienciaImp = 10.0;
                            monedasImp = 3;
                            break;
                        case "2":
                            experience += 9.0;
                            progressBarLevel.setProgress(levelporgress+9);
                            money += 2;
                            experienciaImp = 9.0;
                            monedasImp = 2;
                            break;
                        case "3":
                            experience += 8.0;
                            progressBarLevel.setProgress(levelporgress+8);
                            money += 2;
                            experienciaImp = 8.0;
                            monedasImp = 2;
                            break;
                    }
                    break;
                case "3":
                    switch (mItem.getStreakReach()){
                        case "1":
                            experience += 15.0;
                            progressBarLevel.setProgress(levelporgress+15);
                            money += 4;
                            experienciaImp = 15.0;
                            monedasImp = 4;
                        case "2":
                            experience += 14.0;
                            progressBarLevel.setProgress(levelporgress+14);
                            money += 3;
                            experienciaImp = 14.0;
                            monedasImp = 3;
                            break;
                        case "3":
                            experience += 13.0;
                            progressBarLevel.setProgress(levelporgress+13);
                            money += 2;
                            experienciaImp = 13.0;
                            monedasImp = 2;
                            break;
                    }
                    break;
                case "4":
                    switch (mItem.getStreakReach()){
                        case "1":
                            experience += 20.0;
                            progressBarLevel.setProgress(levelporgress+20);
                            money += 5;
                            experienciaImp = 20.0;
                            monedasImp = 5;
                            break;
                        case "2":
                            experience += 18.0;
                            progressBarLevel.setProgress(levelporgress+18);
                            money += 4;
                            experienciaImp = 18.0;
                            monedasImp = 4;
                            break;
                        case "3":
                            experience += 16.0;
                            progressBarLevel.setProgress(levelporgress+16);
                            money += 3;
                            experienciaImp = 16.0;
                            monedasImp = 3;
                            break;
                    }
            }




        if (experience >= 100.00){
            level += 1;
            if (level >= 100){
                level = 100;
            }
            experience=0.0;
            progressBarLevel.setProgress(0);
            health = 50;
            progressBarHealt.setProgress(health);
            Toast.makeText(this, getString(R.string.newLevel), Toast.LENGTH_SHORT).show();
        }

        final int money2 = money;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User newUser = new User();
                    newUser.setId(userID);
                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setPassword(password);
                    newUser.setLevel(level);
                    newUser.setExperience(experience);
                    newUser.setHealth(health);
                    newUser.setMoney(money2);
                realm.copyToRealmOrUpdate(newUser);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setUsers(ip+"HabitTracker/setUsers.php", mItem.getUserId(), name, email, password, level, experience, health, money);
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.preferences_money), money+"");
        editor.commit();

        tv_money.setText(money+"");
        tv_level.setText(getString(R.string.level)+" "+level);

        Toast.makeText(this, getText(R.string.youGet)+" "+experienciaImp+" "+getText(R.string.experience)+" "+getText(R.string.and)+" "+monedasImp+" "+getText(R.string.moneyTxt), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSubstractListener(final Habit mItem) {

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Habit newHabit = new Habit();
                newHabit.setId(mItem.getId());
                newHabit.setName(mItem.getName());
                newHabit.setDescription(mItem.getDescription());
                newHabit.setDifficulty(mItem.getDifficulty());
                newHabit.setTag(mItem.getTag());
                newHabit.setStreakReach(mItem.getStreakReach());
                newHabit.setStreak(0);
                newHabit.setUserId(mItem.getUserId());
                newHabit.setTime(mItem.getTime());

                realm.copyToRealmOrUpdate(newHabit);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setHabit(ip+"HabitTracker/setHabits.php", mItem.getName(), mItem.getDescription(), mItem.getDifficulty(), mItem.getTag(), mItem.getStreakReach(), mItem.getStreak()+1, mItem.getUserId(), mItem.getTime());
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                usersList = realm.where(User.class).findAll();

                for (User user : usersList){
                    if (user.getId() == UserID){
                        userID = user.getId();
                        name = user.getName();
                        email = user.getEmail();
                        password = user.getPassword();
                        level = user.getLevel();
                        experience = user.getExperience();
                        health = user.getHealth();
                        money = user.getMoney();
                    }
                }
            }
        });



        health -= 2;
        progressBarHealt.setProgress(health);

        if (health<=0){
            experience = 0;
            progressBarLevel.setProgress(0);
            money = 0;
            level -=1;
            if (level<0){
                level = 0;
            }

            FragmentDialogUserDie = new FragmentDialogUserDie();
            FragmentDialogUserDie.show(getSupportFragmentManager(),"fragmentDialogUserDie");
        }



        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User newUser = new User(userID,name,email,password,level,experience,health,money);
                realm.copyToRealmOrUpdate(newUser);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setUsers(ip+"HabitTracker/setUsers.php", mItem.getUserId(), name, email, password, level, experience, health, money);
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.preferences_money), money+"");
        editor.commit();

        tv_money.setText(money+"");
        tv_level.setText(getString(R.string.level)+" "+level);


        Toast.makeText(this, getText(R.string.youLost)+" -"+2+" "+getText(R.string.health), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReviveUSer(boolean newboolean) {
            usersList = realm.where(User.class).findAll();

            health = usersList.get(0).getHealth();

            health = 50;
            progressBarHealt.setProgress(health);



        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User newUser = new User();
                newUser.setId(userID);
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setLevel(level);
                newUser.setExperience(experience);
                newUser.setHealth(health);
                newUser.setMoney(money);
                realm.copyToRealmOrUpdate(newUser);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setUsers(ip+"HabitTracker/setUsers.php", userID, name, email, password, level, experience, health, money);
        }

            FragmentDialogUserDie.dismiss();
    }

    @Override
    public void onBuyClothes() {

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));
        final String string = sharedPref.getString(getString(R.string.preferences_body), "body");
        final int price = Integer.parseInt(sharedPref.getString(getString(R.string.preferences_price),"0"));

        int body1 = getResources().getIdentifier(string, "drawable", getPackageName());
        Drawable drawable2 = getResources().getDrawable(body1);

        bodyNav.setImageDrawable(drawable2);
        bodyHome.setImageDrawable(drawable2);


        charactersList = realm.where(Characters.class).findAll();
        final long characterId = charactersList.get(0).getId();
        s_face = charactersList.get(0).getFace();
        s_body = charactersList.get(0).getBody();
        s_hair = charactersList.get(0).getHair();
        userID = charactersList.get(0).getUserId();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Characters newCharacters = new Characters();
                newCharacters.setId(characterId);
                newCharacters.setFace(s_face);
                newCharacters.setBody(string);
                newCharacters.setHair(s_hair);
                newCharacters.setUserId(userID);
                realm.copyToRealmOrUpdate(newCharacters);
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                usersList = realm.where(User.class).findAll();

                for (User user : usersList){
                    if (user.getId() == UserID){
                        userID = user.getId();
                        name = user.getName();
                        email = user.getEmail();
                        password = user.getPassword();
                        level = user.getLevel();
                        experience = user.getExperience();
                        health = user.getHealth();
                        money = user.getMoney();
                    }
                }
            }
        });

        final int total = money-price;

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User newUser = new User();
                newUser.setId(userID);
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setLevel(level);
                newUser.setExperience(experience);
                newUser.setHealth(health);
                newUser.setMoney(total);
                realm.copyToRealmOrUpdate(newUser);
            }
        });


        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.preferences_money), total+"");
        editor.commit();

        tv_money.setText(total+"");

        setUsers(ip+"HabitTracker/setUsers.php", userID, name, email, password, level, experience, health, money);
        setPurchases(ip+"HabitTracker/setPurchases.php", userID, string);
        setCharacter(ip+"HabitTracker/setCharacters.php",userID, s_face, string, s_hair);

        FragmentBuyClothes.dismiss();

    }

    @Override
    public void onFirstTime() {
        FragmentDialogFirstTime.dismiss();
    }


    @Override
    public void onCustomBodyRead(Drawable drawable, final String string, int levels, int price) {

        usersList = realm.where(User.class).findAll();

        level = usersList.get(0).getLevel();
        money = usersList.get(0).getMoney();





        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);
        final Long UserID = Long.parseLong(sharedPref.getString(getString(R.string.preferences_id),"id"));

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.preferences_body), string);
        editor.putString(getString(R.string.preferences_price), price+"");
        editor.apply();



        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                purchasesList = realm.where(Purchases.class).findAll();

                for (Purchases purchases : purchasesList){

                    if (purchases.getName().equals(string) && purchases.getUserId() == UserID){
                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferencesHabitTracker), Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.preferences_price), "0");
                        editor.apply();
                    }
                }
            }
        });

        price = Integer.parseInt(sharedPref.getString(getString(R.string.preferences_price),"0"));

        if (price == 0){
            bodyNav.setImageDrawable(drawable);
            bodyHome.setImageDrawable(drawable);


            charactersList = realm.where(Characters.class).findAll();
            final long characterId = charactersList.get(0).getId();
            s_face = charactersList.get(0).getFace();
            s_body = charactersList.get(0).getBody();
            s_hair = charactersList.get(0).getHair();
            userID = charactersList.get(0).getUserId();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    Characters newCharacters = new Characters();
                    newCharacters.setId(characterId);
                    newCharacters.setFace(s_face);
                    newCharacters.setBody(string);
                    newCharacters.setHair(s_hair);
                    newCharacters.setUserId(userID);
                    realm.copyToRealmOrUpdate(newCharacters);
                }
            });

            if (isInternetAvailable(getApplicationContext())){
                setCharacter(ip+"HabitTracker/setCharacters.php",userID, s_face, string, s_hair);
            }
        }else{

            if (level<levels){
                Toast.makeText(this, getString(R.string.buyClothes)+" "+levels+" "+getText(R.string.cost)+" "+price, Toast.LENGTH_SHORT).show();
            }
            else if (money<price){
                Toast.makeText(this, getString(R.string.buyClothes)+" "+levels+" "+getText(R.string.cost)+" "+price, Toast.LENGTH_SHORT).show();
            }
            else{
                if (isInternetAvailable(getApplicationContext())){


                    FragmentBuyClothes = new FragmentBuyClothes();
                    FragmentBuyClothes.show(getSupportFragmentManager(),"FragmentBuyClothes");
                }else{
                    Toast.makeText(this, getText(R.string.internetConection), Toast.LENGTH_SHORT).show();
                }
            }



        }




    }

    @Override
    public void onCustomFaceRead(Drawable drawable, final String string) {

        faceNav.setImageDrawable(drawable);
        faceHome.setImageDrawable(drawable);

        charactersList = realm.where(Characters.class).findAll();

        s_face = charactersList.get(0).getFace();
        s_body = charactersList.get(0).getBody();
        s_hair = charactersList.get(0).getHair();
        userID = charactersList.get(0).getUserId();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Characters newCharacters = new Characters();
                newCharacters.setId(charactersList.get(0).getId());
                newCharacters.setFace(string);
                newCharacters.setBody(s_body);
                newCharacters.setHair(s_hair);
                newCharacters.setUserId(userID);
                realm.copyToRealmOrUpdate(newCharacters);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setCharacter(ip+"HabitTracker/setCharacters.php",userID, string, s_body, s_hair);
        }

    }

    @Override
    public void onCustomHairRead(Drawable drawable, final String string) {

        hairNav.setImageDrawable(drawable);
        hairHome.setImageDrawable(drawable);

        charactersList = realm.where(Characters.class).findAll();

        s_face = charactersList.get(0).getFace();
        s_body = charactersList.get(0).getBody();
        s_hair = charactersList.get(0).getHair();
        userID = charactersList.get(0).getUserId();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Characters newCharacters = new Characters();
                newCharacters.setId(charactersList.get(0).getId());
                newCharacters.setFace(s_face);
                newCharacters.setBody(s_body);
                newCharacters.setHair(string);
                newCharacters.setUserId(userID);
                realm.copyToRealmOrUpdate(newCharacters);
            }
        });

        if (isInternetAvailable(getApplicationContext())){
            setCharacter(ip+"HabitTracker/setCharacters.php",userID, s_face, s_body, string);
        }

    }


    public void synchronize(final long UserID){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                habitList = realm.where(Habit.class).findAll();
                tagsList = realm.where(Tags.class).findAll();
                usersList = realm.where(User.class).findAll();
                charactersList = realm.where(Characters.class).findAll();

                if(!habitList.isEmpty() && !tagsList.isEmpty() && !usersList.isEmpty() && !charactersList.isEmpty()) {

                    for(Habit habit : habitList) {

                        if (habit.getUserId() == UserID){

                            setHabit(ip+"HabitTracker/setHabits.php", habit.getName(), habit.getDescription(), habit.getDifficulty(), habit.getTag(), habit.getStreakReach(), habit.getStreak(), habit.getUserId(), habit.getTime());

                        }

                    }
                    for(Tags tags : tagsList) {

                        if (tags.getUserId() == UserID){

                            setTags(ip+"HabitTracker/setTags.php", tags.getUserId(), tags.getStudies()+"", tags.getSocial()+"", tags.getDiet()+"", tags.getWellbeing()+"", tags.getSports()+"", tags.getMeditation()+"");

                        }

                    }
                    for (User user : usersList){

                        if (user.getId() == UserID){

                            setUsers(ip+"HabitTracker/setUsers.php", user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getLevel(), user.getExperience(), user.getHealth(), user.getMoney());

                        }

                    }
                    for (Characters characters : charactersList){

                        if (characters.getId() == UserID){

                            setCharacter(ip+"HabitTracker/setCharacters.php", characters.getUserId(), characters.getFace(), characters.getBody(), characters.getHair());

                        }

                    }

                }


            }
        });


    }


    private void setHabit(String URL, final String name, final String description, final String difficulty, final String tag, final String streakReach, final int streak, final long UserID, final String time ){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            Log.d("approve", "bien");


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
                }else{
                    message = "shit";
                }
                Log.d("Realm", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("name", name);
                parametros.put("description", description);
                parametros.put("difficulty", difficulty);
                parametros.put("tag", tag);
                parametros.put("streakReach", streakReach);
                parametros.put("streak", streak+"");
                parametros.put("UserID", UserID+"");
                parametros.put("time", time);

                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //SET_CHARACTER
    private void setCharacter(String URL, final Long userID, final String face, final String body, final String hair){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);


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
                Log.d("Internet",message);
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

    private void setTags(String URL, final Long userID, final String studies, final String social, final String diet, final String wellbeing, final String sports, final String meditation){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

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
                Log.d("Tag",message);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("studies", studies);
                parametros.put("social", social);
                parametros.put("diet", diet);
                parametros.put("wellbeing", wellbeing);
                parametros.put("sports", sports);
                parametros.put("meditation", meditation);
                parametros.put("UserID", userID.toString());

                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setUsers(String URL, final Long userID, final String name, final String email, final String password, final int level, final double experience, final int health, final int money){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


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
                Log.d("Internet",message);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("name", name);
                parametros.put("email", email);
                parametros.put("pass", password);
                parametros.put("level", level+"");
                parametros.put("experience", experience+"");
                parametros.put("health", health+"");
                parametros.put("money", money+"");
                parametros.put("UserID", userID.toString());

                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void deleteHabit(String URL, final long userID, final String time) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                Log.d("Internet", message);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UserID", userID+"");
                parametros.put("time",time);

                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    private void checkPurchases(final long userID, final String name) {



    }

    private void setPurchases(String URL, final long userID, final String name) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response);

                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(ActivityHome.this, getText(R.string.bought), Toast.LENGTH_SHORT).show();
                                realm.executeTransactionAsync(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Purchases purchases = new Purchases(name, userID);
                                        realm.copyToRealm(purchases);
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
                Log.d("Internet", message);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("UserID", userID+"");
                parametros.put("name",name);

                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
