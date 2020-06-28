package recreate.india.yogdaan;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
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

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    AlertDialog alertDialog;
    private ImageButton donate,help,volunteer,ourWork;
    TextView Help,Our_Work,Volunteers,More,Our_Helpers,Donate;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = this.getActionBar();

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
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HelpPage.class));
            }
        });
        ourWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BlogActivity.class));
            }
        });
        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Volunteer.class));
            }
        });
        Our_Helpers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OurHelpers.class));
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         navigationView = (NavigationView) findViewById(R.id.n1);
        navigationView.setNavigationItemSelectedListener(this);





        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("isFirstTime", true);


//        if (isFirstTime) {
//            onFirst();
//        } else {
//            FirebaseUser currentUser = mAuth.getCurrentUser();
//            if (currentUser == null) {
//                gotoLoginActivity();
//            }
//        }

        Paper.init(this);
        String language = Paper.book().read("language");
        if(language==null)
            Paper.book().write("language","en");
        updateView((String)Paper.book().read("language"));

    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this,lang);
        Resources resources = context.getResources();

        Help.setText(resources.getString(R.string.Help));
        Donate.setText(resources.getString(R.string.Donate));
        Volunteers.setText(resources.getString(R.string.Volunteers));
        Our_Helpers.setText(resources.getString(R.string.Our_Helpers));
        Our_Work.setText(resources.getString(R.string.Our_Work));
        More.setText(resources.getString(R.string.More));

    }

//    public void onFirst() {
//            new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("Terms and Conditions")
//                    .setMessage("T&C")
//                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                            System.exit(0);
//                        }
//                    })
//                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
////                            gotoLoginActivity();
//
//                        }
//                    }).create().show();
//            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("isFirstTime", false);
//            editor.apply();
//        }
//
//
//    private void gotoLoginActivity() {
//        Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
//        logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(logIntent);
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {

            return true;
        }
//        switch (item.getItemId()){
//            case R.id.Status:
//                Intent intent = new Intent(MainActivity.this, Statuspage.class);
//                startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.YourDonation:
                Intent intent1 = new Intent(MainActivity.this, Statuspage.class);
                startActivity(intent1);
                break;

            case R.id.Status:
                Intent intent = new Intent(MainActivity.this, Statuspage.class);
                startActivity(intent);
                break;

            case R.id.selectlanguage:
                Intent intent0 = new Intent(MainActivity.this, Intro_Adapter.class);
                startActivity(intent0);
                break;
            case R.id.logout:
                Intent intent6 = new Intent(MainActivity.this, Intro_Adapter.class);
                startActivity(intent6);
                break;

        }

        return true;
    }
}
