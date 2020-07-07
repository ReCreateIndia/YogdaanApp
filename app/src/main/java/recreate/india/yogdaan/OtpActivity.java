package recreate.india.yogdaan;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import Helper.LocaleHelper;
import io.paperdb.Paper;

public class OtpActivity extends AppCompatActivity {
    private Button otp_btn;
    private ProgressBar otp_progressbar;
    private FirebaseAuth mAuth;
    private EditText otp;
    private String mPhoneNumber;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private Button resendBtn;
    TextView otp_title,otp_desc;
    private ActionBar actionBar;
    private int counter;
    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        timer=findViewById(R.id.timer);
        new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished){
                timer.setText(String.valueOf(counter));
                counter++;
            }
            public  void onFinish(){
                timer.setText("press resend otp");
            }
        }.start();
        actionBar = this.getActionBar();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        otp_btn = findViewById(R.id.verify_btn);
        mPhoneNumber = getIntent().getStringExtra("phone_number");
        otp_progressbar = findViewById(R.id.otp_progress);
        otp = findViewById(R.id.otp_text_view);
        mAuth = FirebaseAuth.getInstance();
        sendVerificationCode(mPhoneNumber);
        resendBtn = findViewById(R.id.resend_btn);
        otp_desc = findViewById(R.id.otp_desc);
        otp_title = findViewById(R.id.otp_title);


        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_progressbar.setVisibility(View.VISIBLE);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                resendVerificationCode(mPhoneNumber, mResendToken);
                            }
                        },
                        1500);
            }
        });
        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otp.getText().toString().trim();
                if (code.length() != 6) {

                    Toast.makeText(OtpActivity.this, "Please fill in the form and try again.", Toast.LENGTH_SHORT).show();
                } else {
                    otp_progressbar.setVisibility(View.VISIBLE);
                    otp_btn.setEnabled(false);
                    verifyVerificationCode(code);


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

        otp_title.setText(resources.getString(R.string.Enter_OTP));
        otp_desc.setText(resources.getString(R.string.We_Have_Sent_You_An_OTP_For_Phone_Number_Verification));
        resendBtn.setText(resources.getString(R.string.Resend_OTP));
        otp_btn.setText(resources.getString(R.string.Verify_OTP));
        otp.setHint(resources.getString(R.string.Enter_OTP));

    }

    private void resendVerificationCode(String mPhoneNumber, PhoneAuthProvider.ForceResendingToken mResendToken) {
        otp_progressbar.setVisibility(View.INVISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                OtpActivity.this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                mResendToken);             // ForceResendingToken from callbacks
    }

    private void sendVerificationCode(String mPhoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            final String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                otp.setText(code);
                //verifying the code
                otp_progressbar.setVisibility(View.VISIBLE);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                verifyVerificationCode(code);
                            }
                        },
                        1000);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            otp_progressbar.setVisibility(View.INVISIBLE);
            otp_btn.setEnabled(true);
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            mResendToken = forceResendingToken;

        }
    };

    private void gotoHomeActivity() {
        Intent homeIntent = new Intent(OtpActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            gotoHomeActivity();
                            // ...
                        } else {
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}