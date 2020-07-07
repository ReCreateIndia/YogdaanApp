package recreate.india.yogdaan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {

    private  static int SPLASH_TIME_OUT = 2000;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        Paper.init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String language = Paper.book().read("language");
                if (language == null){
                    Intent intent = new Intent(SplashScreen.this,language.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
//                    finish();
                }
//                if(firebaseUser!=null){
//                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else{
//
//                    Intent intent = new Intent(SplashScreen.this,language.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        },SPLASH_TIME_OUT);


    }

}