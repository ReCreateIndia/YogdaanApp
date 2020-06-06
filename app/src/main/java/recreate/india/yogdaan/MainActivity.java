package recreate.india.yogdaan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageSlider imageslider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.d5));
        slideModels.add(new SlideModel(R.drawable.d4));
        slideModels.add(new SlideModel(R.drawable.d3));
        slideModels.add(new SlideModel(R.drawable.d1));
        mAuth = FirebaseAuth.getInstance();
        slideModels.add(new SlideModel(R.drawable.d2));
        imageslider.setImageList(slideModels, true);


        drawerLayout=(DrawerLayout) findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.n1);

        onFirst();


    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser==null){
            Intent logIntent= new Intent(MainActivity.this,LoginActivity.class);
            logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logIntent);
        }

    }

    private void onFirst() {

        Paper.init(this);
        String isFirstRun = Paper.book().read("isFirstRun");
        if (isFirstRun == null)
            Paper.book().write("isFirstRun", "false");


        if (isFirstRun.equals("false")) {
            Intent intent = new Intent(MainActivity.this, TandC.class);
            startActivity(intent);

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

                            Paper.book().write("isFirstRun", "true");

                        }
                    }).show();

        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }
}
