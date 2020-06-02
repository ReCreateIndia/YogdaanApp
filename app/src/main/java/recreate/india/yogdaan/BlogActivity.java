package recreate.india.yogdaan;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BlogActivity extends AppCompatActivity {
    private Button addpostbutton;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        bottomNavigationView=findViewById(R.id.navigationView);
        addpostbutton=findViewById(R.id.NewPostButton);
        addpostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BlogActivity.this,NewPostActivity.class));

            }
        });
    }
}
