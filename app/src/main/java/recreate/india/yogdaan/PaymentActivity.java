package recreate.india.yogdaan;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class PaymentActivity extends AppCompatActivity {

    private EditText amount, note;
    TextView tt;
    private Button send;
    String TAG = "main",upiVirtualID = "virensaroha123@okhdfcbank";
    final int UPI_PAYMENT = 0;
    private EditText namepay,phonenopay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Bundle bundle = getIntent().getExtras();
        String ammount  = bundle.getString("amount");

        send = (Button) findViewById(R.id.send);
        amount = (EditText) findViewById(R.id.amount_et);
        tt=findViewById(R.id.textView7P);
        if(ammount!=null){
            amount.setText(ammount);
        }

        namepay = (EditText) findViewById(R.id.namepay);
        phonenopay = (EditText) findViewById(R.id.phonenumberpay);
//        upiVirtualID = (EditText) findViewById(R.id.upi_id);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(namepay.getText().toString().trim())) {
                    Toast.makeText(PaymentActivity.this, "Name is invalid", Toast.LENGTH_SHORT).show();
                }
//                else if (TextUtils.isEmpty(upiVirtualID.getText().toString().trim())) {
//                    Toast.makeText(PaymentActivity.this, "UPI ID  is invalid", Toast.LENGTH_SHORT).show();
//                }
//                else if (TextUtils.isEmpty(note.getText().toString().trim())) {
//                    Toast.makeText(PaymentActivity.this, "Note is invalid", Toast.LENGTH_SHORT).show();
//                }
                else {
                    payUsingUpi("viren n" ,amount.getText().toString() );
//                            note.getText().toString(),

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

        send.setText(resources.getString(R.string.Donate_Now));
        amount.setHint(resources.getString(R.string.enter_amount));
        namepay.setHint(resources.getString(R.string.Name));
        phonenopay.setHint(resources.getString(R.string.phone_number));
        tt.setText(resources.getString(R.string.Donate));

    }

    void payUsingUpi(String name, /*String note,*/ String amount) {
        Log.e("main", "name " + "--up--" + "virensaroha123@okhdfcbank" + "--" + note + "--" + amount);

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "virensaroha123@okhdfcbank")
                .appendQueryParameter("pn", name)
//                         .appendQueryParameter("mc", "your-merchant-code")
//                         .appendQueryParameter("tr", "your-transaction-ref-id")
//                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
//                         .appendQueryParameter("url", "your-transaction-url")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay With");

        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this, "No UPI app found, please install one to continue.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main", "response" + resultCode);

        if (requestCode == UPI_PAYMENT) {
            if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.e("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                Log.e("UPI", "onActivityResult: " + "Return data is null");
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> dataList) {
        if (isConnectionAvailable(PaymentActivity.this)) {
            String str = dataList.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String[] equalStr = response[i].split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[i].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase())) {
                        approvalRefNo = equalStr[i];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                Toast.makeText(PaymentActivity.this, "Transaction Successfull", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PaymentActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(this, "Internet connection is not available,please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}