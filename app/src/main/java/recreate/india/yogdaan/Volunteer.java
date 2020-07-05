package recreate.india.yogdaan;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Volunteer extends AppCompatActivity {
    Dialog epicdialog;
    Button box;
    Spinner spinnn;
    private String item = "yo";
    TextView quote,volunteer,name,work,occupation,address,dob;
    EditText Name,TypeofWork,Occupation,Address,DOB;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;


    ImageView closenew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        button=findViewById(R.id.volunteer_submit);
        Name=findViewById(R.id.volunteer_name);
        Occupation=findViewById(R.id.volunteer_occupation);
        Address=findViewById(R.id.volunteer_address);
        DOB=findViewById(R.id.volunteer_dob);
        epicdialog=new Dialog(this);
        box=(Button) findViewById(R.id.volunteer_submit);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shownewpopup();
            }
        });
        spinnn = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        list.add(0, "Select problem");
        list.add("Education");
        list.add("Food");
        list.add("Medical faciltites");
        list.add("Need Blood");
        list.add("clothing");
        list.add("Need Money");
        list.add("Housing");
        list.add("Old people");
        list.add("Human Rights");
        list.add("Information Technology");
        list.add("Agriculture");
        list.add("Water Resource Management");
        list.add("Child Development");
        list.add("Consumer Rights");
        list.add("Awareness of Insurance");
        list.add("Enterpreneurship Community");
        list.add("cooperative and Resource Building");
        list.add("human Welfare");
        list.add("Water and Sanitation ");
        list.add("E-governance");
        list.add("E-learning");
        list.add("Community Development");
        list.add("State budget analysis in perspective of poor");
        list.add("Sustainable Development");
        list.add("Marriage of deprived girls");
        list.add("Civic issues");
        list.add("Social Development");
        list.add("Animals Husbandry,Dairying,Fisheries");
        list.add("Tribal affairs");
        list.add("Legal Awareness and AID");
        list.add("Land Resource");
        list.add("Micro Finance (SHGs)");
        list.add("Micro Small and Minor Enterprises");
        list.add("Minority issues");
        list.add("New and Renewable Energy");
        list.add("Nutrition");
        list.add("Panchayati Raj");
        list.add("Prisioner's issues");
        list.add("Right to Information and Advocacy");
        list.add("Rural Development & Poverty Alleviation");
        list.add("Science and Technology");
        list.add("Scientific and Industrial Research");
        list.add("Sports");
        list.add("Tourism");
        list.add("Urban Development & Poverty Alleviation");
        list.add("Women's Development and Empowerment");
        list.add("Water resource");
        list.add("Art & culture");
        list.add("Youth Affairs");
        list.add("Drinking Water");
        list.add("Food Processing");
        list.add("HIV/Aids");
        list.add("Labour and Employment");
        list.add("Land Resource");
        list.add("Health And Family Welfare");
        list.add("Disaster Management");
        list.add("Vocational Training");
        list.add("Environment and Forests");
        list.add("Differently abled Person");
        list.add("Biotechnology");
        list.add("Dalit Upliftment");
        list.add("Any other");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnn.setAdapter(arrayAdapter);
        spinnn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Select problem")) {

                } else {
                    item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected:" + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object>map=new HashMap<>();
                map.put("name",Name.getText().toString());
                map.put("dob",DOB.getText().toString());
                map.put("occupation",Occupation.getText().toString());
                map.put("address",Address.getText().toString());

                firebaseFirestore.collection("Volunteer_Requests").document(item).collection("present_request").document(firebaseUser.getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Shownewpopup();
                    }
                });

            }
        });





    }
    public void Shownewpopup(){
        epicdialog.setContentView(R.layout.new_card);
        closenew=(ImageView) epicdialog.findViewById(R.id.closePopyu);

        closenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicdialog.dismiss();
            }
        });
        epicdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
        epicdialog.show();
    }
}