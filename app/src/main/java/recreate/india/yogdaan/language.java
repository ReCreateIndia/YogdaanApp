package recreate.india.yogdaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import io.paperdb.Paper;

public class language extends AppCompatActivity {
    private LinearLayout english,hindi,selectlinearlayout;
    private Button select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        selectlinearlayout=findViewById(R.id.selectlinearlayout);
        selectlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(language.this,Introduction_Activity.class));
            }
        });
        select=findViewById(R.id.selectlanguageasap);
        english=findViewById(R.id.langchangetoenglish);
        hindi=findViewById(R.id.langchangetohindi);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("language","en");
            }
        });
        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("language","hi");
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(language.this,NewIntroActivity.class));
            }
        });
    }
}