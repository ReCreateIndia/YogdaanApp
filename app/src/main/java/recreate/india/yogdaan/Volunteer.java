package recreate.india.yogdaan;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
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

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class Volunteer extends AppCompatActivity {
    Dialog epicdialog;

    Spinner spinnn;
    private String item = "yo";

    TextView volunteerquote,volunteer11,work1;
    EditText NameV,OccupationV,AddressV,DOBV;



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
        NameV=findViewById(R.id.volunteer_name);
        OccupationV=findViewById(R.id.volunteer_occupation);
        AddressV=findViewById(R.id.volunteer_address);
        DOBV=findViewById(R.id.volunteer_dob);
        epicdialog=new Dialog(this);


        volunteerquote=findViewById(R.id.volunteerquote);
        volunteer11=findViewById(R.id.voluntee);
        work1=findViewById(R.id.TypeOfWork);
        NameV=findViewById(R.id.volunteer_name);
        OccupationV=findViewById(R.id.volunteer_occupation);
        AddressV=findViewById(R.id.volunteer_address);
        DOBV=findViewById(R.id.volunteer_dob);

        spinnn = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();
        list.add(0, getString(R.string.Select_problem));
        list.add(getString(R.string.Education));
        list.add(getString(R.string.Food));
        list.add(getString(R.string.Medical_faciilities));
        list.add(getString(R.string.Need_blood));
        list.add(getString(R.string.clothing));
        list.add(getString(R.string.NeedMoney));
        list.add(getString(R.string.Housing));
        list.add(getString(R.string.Oldpeople));
        list.add(getString(R.string.Humanrights));
        list.add(getString(R.string.Informationtech));
        list.add(getString(R.string.Agriculture));
        list.add(getString(R.string.Waterresourcemanagement));
        list.add(getString(R.string.childdevelopment));
        list.add(getString(R.string.consumerrights));
        list.add(getString(R.string.awarenessofinsurance));
        list.add(getString(R.string.enterpreneurship));
        list.add(getString(R.string.cooperativeandresourcebuilding));
        list.add(getString(R.string.humanwelfare));
        list.add(getString(R.string.waterandsaniatation));
        list.add(getString(R.string.Egovernance));
        list.add(getString(R.string.eleraning));
        list.add(getString(R.string.communitydevelopment));
        list.add(getString(R.string.satate));
        list.add(getString(R.string.sustainabledevelopment));
        list.add(getString(R.string.Marriageofdeprivedgirls));
        list.add(getString(R.string.civicissues));
        list.add(getString(R.string.socialdeveopment));
        list.add(getString(R.string.animalhusbandry));
        list.add(getString(R.string.tribalaffairs));
        list.add(getString(R.string.legalawareness));
        list.add(getString(R.string.landresuorce));
        list.add(getString(R.string.microfinnce));
        list.add(getString(R.string.micro));
        list.add(getString(R.string.minorityissues));
        list.add(getString(R.string.newrenwavle));
        list.add(getString(R.string.nutition));
        list.add(getString(R.string.panchaytiraj));
        list.add(getString(R.string.prisionerissues));
        list.add(getString(R.string.righttoinformationandadvoccay));
        list.add(getString(R.string.ruraldevelopment));
        list.add(getString(R.string.scienceandtechnology));
        list.add(getString(R.string.scientistsandindustrialresaerch));
        list.add(getString(R.string.sports));
        list.add(getString(R.string.tourism));
        list.add(getString(R.string.urbandevelopment));
        list.add(getString(R.string.womenandem));
        list.add(getString(R.string.waterresuorce));
        list.add(getString(R.string.artandculture));
        list.add(getString(R.string.youthaffairs));
        list.add(getString(R.string.drinkingwater));
        list.add(getString(R.string.foodprocessing));
        list.add(getString(R.string.hiv));
        list.add(getString(R.string.labourandemployment));
        list.add(getString(R.string.landresource));
        list.add(getString(R.string.healthandfamilywelfare));
        list.add(getString(R.string.disastermanagement));
        list.add(getString(R.string.vocationaltraining));
        list.add(getString(R.string.environmentandforests));
        list.add(getString(R.string.Diffrentabledperson));
        list.add(getString(R.string.biotechnology));
        list.add(getString(R.string.Dalitup));
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
                map.put("name",NameV.getText().toString());
                map.put("dob",DOBV.getText().toString());
                map.put("occupation",OccupationV.getText().toString());
                map.put("address",AddressV.getText().toString());

                firebaseFirestore.collection("Volunteer_Requests").document(item).collection("present_request").document(firebaseUser.getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Shownewpopup();
                    }
                });

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

        volunteerquote.setText(resources.getString(R.string.the_best_way_to_persue_happiness_is_to_help_others));

        NameV.setHint(resources.getString(R.string.Name));
        OccupationV.setHint(resources.getString(R.string.Occupation));
        AddressV.setHint(resources.getString(R.string.Address));
        DOBV.setHint(resources.getString(R.string.Date_Of_Birth));
        work1.setText(resources.getString(R.string.Type_Of_Work));
        volunteer11.setText(resources.getString(R.string.Volunteers));

        button.setText(resources.getString(R.string.Submit));
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