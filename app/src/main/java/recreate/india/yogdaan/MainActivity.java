package recreate.india.yogdaan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    AlertDialog alertDialog;
    private boolean isFirstTime;
    private ImageButton our_work;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        our_work=findViewById(R.id.ourWork);
        our_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BlogActivity.class));
            }
        });


        ImageSlider imageslider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.d5));
        slideModels.add(new SlideModel(R.drawable.d4));
        slideModels.add(new SlideModel(R.drawable.d3));
        slideModels.add(new SlideModel(R.drawable.d1));
        mAuth = FirebaseAuth.getInstance();
        slideModels.add(new SlideModel(R.drawable.d2));
        imageslider.setImageList(slideModels, true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView =  findViewById(R.id.n1);
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
                    case R.id.logout: mAuth.signOut(); gotoLoginActivity();
                        break;
                    case R.id.Your:
                        Toast.makeText(MainActivity.this,"Your Donation",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Certificates:
                        Toast.makeText(MainActivity.this,"Your Certificates/Receipt",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Status:
                        Toast.makeText(MainActivity.this,"Status of your request",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Toast.makeText(MainActivity.this,"Setting",Toast.LENGTH_SHORT).show();
                        break;


                }

                return false;
            }
        });


    }

    public void onFirst() {


        Intent intent = new Intent(MainActivity.this, TandC.class);
        startActivity(intent);


       
        if (!isFirstTime) {
            Intent intent1 = new Intent(MainActivity.this, TandC.class);
            startActivity(intent1);


            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Terms and Conditions")
                    .setMessage("T&C")
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            gotoLoginActivity();


                        }
                    }).create().show();
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();


        }

      Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
      startActivity(intent2);
    }




    private void gotoLoginActivity() {
        Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
        logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logIntent);
    }



}
