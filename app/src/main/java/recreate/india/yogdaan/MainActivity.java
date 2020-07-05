package recreate.india.yogdaan;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity  {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    AlertDialog alertDialog;
     private boolean isFirstTime;
   private ImageButton our_work;


    private ImageButton donate, help, volunteer, ourWork;
    TextView Help, Our_Work, Volunteers, More, Our_Helpers, Donate;
    ActionBar actionBar;



    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = this.getActionBar();
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        donate = findViewById(R.id.donate);
        help = findViewById(R.id.help);
        volunteer = findViewById(R.id.volunteer);
        ourWork = findViewById(R.id.ourWork);

        Help = findViewById(R.id.Help);
        Our_Work = findViewById(R.id.Our_Work);
        Volunteers = findViewById(R.id.Volunteers);
        More = findViewById(R.id.More);
        Our_Helpers = findViewById(R.id.Our_Helpers);
        Donate = findViewById(R.id.Donate);

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DonateActivity.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HelpPage.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
        ourWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BlogActivity.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Volunteer.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
        Our_Helpers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OurHelpers.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        ImageSlider imageslider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.d5));
        slideModels.add(new SlideModel(R.drawable.d4));
        slideModels.add(new SlideModel(R.drawable.d3));
        slideModels.add(new SlideModel(R.drawable.d1));

        slideModels.add(new SlideModel(R.drawable.d2));
        imageslider.setImageList(slideModels, true);
       getactionbarnow();

        navigationView = findViewById(R.id.n1);


        //View header = navigationView.getHeaderView(0);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
 


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        isFirstTime = prefs.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            onFirst();
        } else {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                gotoLoginActivity();
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        mAuth.signOut();
                        gotoLoginActivity();
                        break;
                    case R.id.YourDonation:
                        Toast.makeText(MainActivity.this, "Your Donation", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Certificates:
                        Toast.makeText(MainActivity.this, "Your Certificates/Receipt", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Status:
                        Toast.makeText(MainActivity.this, "Status of your request", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.selectlanguage:
                        Toast.makeText(MainActivity.this, "Choose language", Toast.LENGTH_SHORT).show();
                        break;


                }

                return false;
            }
        });


        Paper.init(this);
        String language = Paper.book().read("language");
        if (language == null)
            Paper.book().write("language", "en");
        //updateView((String) Paper.book().read("language"));

    }

    private void getactionbarnow() {
        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    public void onFirst() {


        Intent intent = new Intent(MainActivity.this, TandC.class);
        startActivity(intent);


        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Terms and Conditions")
                .setMessage("T&C")
                .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                    //@Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    //@Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gotoLoginActivity();


                    }
                }).create().show();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isFirstTime", false);
        editor.apply();


//        Context context = LocaleHelper.setLocale(this, lang);
//        Resources resources = context.getResources();
//
//        Help.setText(resources.getString(R.string.need_help));
//        Donate.setText(resources.getString(R.string.Donate));
//        Volunteers.setText(resources.getString(R.string.Volunteers));
//        Our_Helpers.setText(resources.getString(R.string.Our_Helpers));
//        Our_Work.setText(resources.getString(R.string.Our_Work));
//        More.setText(resources.getString(R.string.More));


    }


    private void gotoLoginActivity() {
        Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
        logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logIntent);
    }



}
