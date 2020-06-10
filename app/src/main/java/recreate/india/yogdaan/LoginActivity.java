package recreate.india.yogdaan;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import Helper.LocaleHelper;
import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {
    private EditText phone_number_edittext;
    private Button create_btn;
    private ProgressBar log_progress;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView textView,login_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        phone_number_edittext = findViewById(R.id.phone_number_text);
        create_btn = findViewById(R.id.generate_btn);
        log_progress = findViewById(R.id.login_progress);

        textView = findViewById(R.id.text_view);
        login_desc = findViewById(R.id.login_desc);



        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = phone_number_edittext.getText().toString();
                String code = "+91";
                final String complete_phone_number = code + phone_number;
                if (phone_number.length() == 10) {
                    create_btn.setEnabled(false);
                    log_progress.setVisibility(View.VISIBLE);
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    Intent otpIntent = new Intent(LoginActivity.this, OtpActivity.class);
                                    otpIntent.putExtra("phone_number", complete_phone_number);
                                    startActivity(otpIntent);
                                }
                            },
                            1500);


                } else {

                    Toast.makeText(LoginActivity.this, "Please enter a valid number.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Paper.init(this);
        String language = Paper.book().read("language");
        if(language==null)
            Paper.book().write("language","en");
        updateView((String)Paper.book().read("language"));
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this,lang);
        Resources resources = context.getResources();

        textView.setText(resources.getString(R.string.Verify_Your_Number));
        login_desc.setText(resources.getString(R.string.Please_Enter_Your_Phone_Number_For_Verification));
        phone_number_edittext.setHint(resources.getString(R.string.Your_Phone_Number));
        create_btn.setText(resources.getString(R.string.Generate_OTP));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseUser!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

    }
}