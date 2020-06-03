package recreate.india.yogdaan;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BlogActivity extends AppCompatActivity {
    private Button addpostbutton;
    private BottomNavigationView bottomNavigationView;
    private BlogsFragment blogsFragment;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        toolbar=findViewById(R.id.toolbar);
        blogsFragment=new BlogsFragment();
        ReplaceFragments(blogsFragment);
        bottomNavigationView=findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:ReplaceFragments(blogsFragment);return true;
                    case R.id.action_settings:ReplaceFragments(blogsFragment);return true;
                    case R.id.action_navigation:ReplaceFragments(blogsFragment);return true;
                }
                return false;
            }
        });
        addpostbutton=findViewById(R.id.NewPostButton);
        addpostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BlogActivity.this,NewPostActivity.class));

            }
        });
    }
    private void ReplaceFragments(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.commit();
    }
}
