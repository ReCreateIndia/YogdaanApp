package recreate.india.yogdaan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OtpActivity extends AppCompatActivity {
    private Button otp_btn;
    private ProgressBar otp_progressbar;
    private FirebaseAuth mAuth;
    private EditText otp;
    private String mAuthVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otp_btn = findViewById(R.id.verify_btn);
        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");
        otp_progressbar = findViewById(R.id.otp_progress);
        otp = findViewById(R.id.otp_text_view);
        mAuth = FirebaseAuth.getInstance();
        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpText = otp.getText().toString();
                if (otpText.isEmpty()){

                    Toast.makeText(OtpActivity.this, "Please fill in the form and try again.",Toast.LENGTH_SHORT).show();
                }
                else{
                    otp_progressbar.setVisibility(View.VISIBLE);
                    otp_btn.setEnabled(false);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otpText);
                    signInWithPhoneAuthCredential(credential);

                }

            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent homeIntent = new Intent(OtpActivity.this, MainActivity.class);
                            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeIntent);
                            // ...
                        } else {
                           Toast.makeText(OtpActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        otp_progressbar.setVisibility(View.INVISIBLE);
                        otp_btn.setEnabled(true);
                    }
                });

    }
}