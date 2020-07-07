package recreate.india.yogdaan;

import android.app.ActionBar;
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
    TextView tt1S,tt2S,tt3S,tt4S,tt5S;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_suggestion);
        actionBar = this.getActionBar();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        stationary=findViewById(R.id.stationary);
        books=findViewById(R.id.books);
        direct=findViewById(R.id.moenydirect);
        food=findViewById(R.id.food_money);
        tt1S=findViewById(R.id.textView13S);
        tt2S=findViewById(R.id.stationarymoney);
        tt4S=findViewById(R.id.food);
        tt5S=findViewById(R.id.money);
        tt3S=findViewById(R.id.bookmoney);
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

        tt1S.setText(resources.getString(R.string.select_the_money_range));
        tt2S.setText(resources.getString(R.string.stationary_money));
        tt3S.setText(resources.getString(R.string.books_money));
        tt4S.setText(resources.getString(R.string.food_money));
        tt5S.setText(resources.getString(R.string.money_directly));

    }
}