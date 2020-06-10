package recreate.india.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class HelpPage extends AppCompatActivity {
    TextView SelectProblem,NameHelpPage,Date_Of_Birth_Help_Page,Area_Of_Help,Flat_House_No1,Flat_House_No2,City_Help_Page,Pin_Code_Help_Page,State_Help_Page,TypeOfHelp;
    Spinner spin;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton c1,c2;
    FirebaseFirestore ff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String item="yo";
    private Button submit_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        c1 = (RadioButton) findViewById(R.id.c1);
        c2 = (RadioButton) findViewById(R.id.c2);
        SelectProblem = (TextView)findViewById(R.id.Select_Problem);
        NameHelpPage = (TextView)findViewById(R.id.NameHelpPage);
        Date_Of_Birth_Help_Page = (TextView)findViewById(R.id.Date_Of_Birth_Help_Page);
        Area_Of_Help = (TextView)findViewById(R.id.Area_Of_Help);
        Flat_House_No1 = (TextView)findViewById(R.id.Flat_House_No1);
        Flat_House_No2 = (TextView)findViewById(R.id.Flat_House_No2);
        City_Help_Page = (TextView)findViewById(R.id.City_Help_Page);
        Pin_Code_Help_Page = (TextView)findViewById(R.id.Pin_Code_Help_Page);
        State_Help_Page = (TextView)findViewById(R.id.State_Help_Page);
        TypeOfHelp = (TextView)findViewById(R.id.TypeOfHelp);

        ff=FirebaseFirestore.getInstance();
        submit_request=findViewById(R.id.submitRequest);
        submit_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Object,String>map=new HashMap<>();
                map.put("yo","yo");
                ff.collection("AllRequest").document(item).collection("presentRequest").document(firebaseUser.getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(HelpPage.this,"hogye",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        radioGroup=findViewById(R.id.radio1);
        spin=(Spinner)findViewById(R.id.spinner2);
        List<String> list=new ArrayList<String>();
        list.add(0,"Select problem");
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



        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select problem")){

                }
                else
                {
                    item=parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected:"+item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


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

        SelectProblem.setText(resources.getString(R.string.Select_Problem));
        c1.setText(resources.getString(R.string.Help_For_Others));
        c2.setText(resources.getString(R.string.Help_For_Yourself));
        TypeOfHelp.setText(resources.getString(R.string.Type_Of_Help));
        Area_Of_Help.setText(resources.getString(R.string.Area_of_Help));
        Flat_House_No1.setText(resources.getString(R.string.Flat_House_No));
        Flat_House_No2.setText(resources.getString(R.string.Flat_House_No));
        Pin_Code_Help_Page.setText(resources.getString(R.string.Pin_Code));
        City_Help_Page.setText(resources.getString(R.string.City));
        State_Help_Page.setText(resources.getString(R.string.State));
        NameHelpPage.setText(resources.getString(R.string.Name));
        Date_Of_Birth_Help_Page.setText(resources.getString(R.string.Date_Of_Birth));

    }

    public void onClickRadiobutton(View view) {
        int radioid=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioid);
        Toast.makeText(this, radioButton.getText()+"is selected", Toast.LENGTH_SHORT).show();
    }
}