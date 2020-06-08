package recreate.india.yogdaan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class TandC extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tand_c);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


//    private void onFirst() {
//
//        Paper.init(this);
//        String isFirstRun = Paper.book().read("isFirstRun");
//        if (isFirstRun == null)
//            Paper.book().write("isFirstRun", "false");
//
//
//        assert isFirstRun != null;
//        if (isFirstRun.equals("false")) {
//            Intent intent = new Intent(MainActivity.this, TandC.class);
//            startActivity(intent);
//
//            new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("Terms and Conditions")
//                    .setMessage("T&C")
//                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                            System.exit(0);
//                        }
//                    })
//                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            Paper.book().write("isFirstRun", "true");
//                        }
//                    }).show();
//
//        }
//
//    }
}