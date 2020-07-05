package recreate.india.yogdaan;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class PaymentSuggestion extends AppCompatActivity {
    LinearLayout stationary,books,direct,food;
    TextView tt1,tt2,tt3,tt4,tt5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_suggestion);
        stationary=findViewById(R.id.stationary);
        books=findViewById(R.id.books);
        direct=findViewById(R.id.moenydirect);
        food=findViewById(R.id.food_money);
        tt1=findViewById(R.id.textView13);
        tt2=findViewById(R.id.stationarymoney);
        tt3=findViewById(R.id.food);
        tt4=findViewById(R.id.money);
                tt5=findViewById(R.id.bookmoney);
        stationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentSuggestion.this, PaymentActivity.class);
                String amount = "15";
                i.putExtra("amount", amount);
                startActivity(i);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentSuggestion.this, PaymentActivity.class);
                String amount = "120";
                i.putExtra("amount", amount);
                startActivity(i);
            }
        });
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentSuggestion.this, PaymentActivity.class);
                String amount = "100";
                i.putExtra("amount", amount);
                startActivity(i);
            }
        });
        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentSuggestion.this, PaymentActivity.class);
                String amount = "";
                i.putExtra("amount", amount);
                startActivity(i);
            }
        });
        Paper.init(this);
        String language = Paper.book().read("language");
        if(language==null)
            Paper.book().write("language","en");
        updateView((String)Paper.book().read("language"));



    }

    private void updateView(String language) {
        Context context = LocaleHelper.setLocale(this, language);
        Resources resources = context.getResources();

        tt1.setText(resources.getString(R.string.select_the_money_range));
        tt2.setText(resources.getString(R.string.stationary_money));
        tt3.setText(resources.getString(R.string.books_money));
        tt4.setText(resources.getString(R.string.food_money));
        tt5.setText(resources.getString(R.string.money_directly));

    }
}