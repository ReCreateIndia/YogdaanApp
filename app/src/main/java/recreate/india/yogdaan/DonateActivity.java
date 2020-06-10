package recreate.india.yogdaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class DonateActivity extends AppCompatActivity {

    private Button donateMoney,donateClothes,donateFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        donateMoney=(Button)findViewById(R.id.donate_money_btn);
        donateClothes=(Button)findViewById(R.id.donate_clothes_btn);
        donateFood=(Button)findViewById(R.id.donate_food_btn);

//        donateClothes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DonateActivity.this,frequency.class);
//                startActivity(intent);
//            }
//        });
//        donateFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DonateActivity.this,frequency.class);
//                startActivity(intent);
//            }
//        });

        donateMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(MainActivitimy.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
//                }
                Intent intent = new Intent(DonateActivity.this,PaymentActivity.class);
                startActivity(intent);
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

        donateClothes.setText(resources.getString(R.string.Donate_Clothes));
        donateFood.setText(resources.getString(R.string.Donate_Food));
        donateMoney.setText(resources.getString(R.string.Donate_Money));
    }
}