package recreate.india.yogdaan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentSuggestion extends AppCompatActivity {
    LinearLayout stationary,books,direct,food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_suggestion);
        stationary=findViewById(R.id.stationary);
        books=findViewById(R.id.books);
        direct=findViewById(R.id.moenydirect);
        food=findViewById(R.id.food_money);
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

    }
}