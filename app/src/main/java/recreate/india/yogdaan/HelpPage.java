package recreate.india.yogdaan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class HelpPage extends AppCompatActivity {
    TextView SelectProblem,enterinfo, NameHelpPage, Occupation,address,idd,orr, quote, TypeOfHelp,yr,tr;
    Spinner spin;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton c1, c2;
    FirebaseFirestore ff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Dialog epicdialog;
    Button box;
    private  String imageDownlaodLink;
    ImageView closenew;
    public static final int PERMISSION_REQUEST_CODE = 9001;
    private static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 9003;
    private String item = "yo";
    LocationManager locationManager;
    Button submit_request;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double lat = 0, lng = 0;
    private Button get_current_location;
    private boolean mLocationPermissionGranted;
    private Button manualAddress;
    LinearLayout ll;
    String help_domain;
    EditText name, city, locality,adress,ocupation;
    private Button takeimage;
    Uri uri;
    private ImageView idproof;
    List<Address> addresses;
    private ActionBar actionBar;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        actionBar = this.getActionBar();
        quote=findViewById(R.id.helpquote);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        idproof = findViewById(R.id.idProof);
        name = findViewById(R.id.name);
        enterinfo=findViewById(R.id.enterinfo);
        idd=findViewById(R.id.iddd);
        orr=findViewById(R.id.orr);
        name=findViewById(R.id.name);
        ocupation=findViewById(R.id.occupation);
        adress=findViewById(R.id.address);
        TypeOfHelp=findViewById(R.id.TypeOfHelp);
        yr=findViewById(R.id.points435);
        tr=findViewById(R.id.messag324e);

        takeimage = findViewById(R.id.cameraIntent);
        takeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 004);
                    } else {
                        openCamera();
                    }
                } else {
                    openCamera();
                }
            }
        });
//        city=findViewById(R.id.city);
//        locality=findViewById(R.id.locality);
        c1 = (RadioButton) findViewById(R.id.c1);
        c2 = (RadioButton) findViewById(R.id.c2);
        radioGroup = findViewById(R.id.radio1);
        int radioid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioid);

        //initialise database
        ff = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        submit_request = findViewById(R.id.submitRequest);
        submit_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images");
                final StorageReference imageFilePath = storageReference.child(uri.getLastPathSegment());
                imageFilePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageDownlaodLink = uri.toString();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // something goes wrong uploading picture
                        Toast.makeText(HelpPage.this,"error",Toast.LENGTH_LONG).show();
                    }
                });
                if(imageDownlaodLink==null){
                    Toast.makeText(HelpPage.this,"nhi hua",Toast.LENGTH_LONG).show();
                }
                Geocoder geocoder;
                geocoder = new Geocoder(HelpPage.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();



                Map<String, Object> map = new HashMap<>();
                map.put("name", name.getText().toString());
                map.put("lat", lat);
                map.put("lng", lng);
                map.put("city", city);
                map.put("state", state);
                map.put("complete_address", address);
                map.put("help_domain", radioButton.getText().toString());
                map.put("phone number", firebaseUser.getPhoneNumber());
                map.put("image",imageDownlaodLink);
                map.put("day",1);


                ff.collection("AllRequest").document(item).collection("presentRequest").document(firebaseUser.getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Shownewpopup();
                        }
                    }
                });
            }
        });
        get_current_location = findViewById(R.id.getCurrentLocation);
        get_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGoogleMap();
                getLocation();
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        }
        ff = FirebaseFirestore.getInstance();
        SelectProblem = (TextView) findViewById(R.id.Select_Problem);
        TypeOfHelp = (TextView) findViewById(R.id.TypeOfHelp);
        epicdialog = new Dialog(this);
        spin = (Spinner) findViewById(R.id.spinner2);
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
        spin.setAdapter(arrayAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        Paper.init(this);
        String language = Paper.book().read("language");
        if (language == null)
            Paper.book().write("language", "en");
        updateView((String)Paper.book().read("language"));

    }

    private void updateView(String language) {
        Context context = LocaleHelper.setLocale(this,language);
        Resources resources = context.getResources();
        quote.setText(resources.getString(R.string.there_is_a_free_platform_for_everyone_and_always_will_be_just_select_the_problem_and_we_will_do_or_best_to_sort_it_out
        ));
        SelectProblem.setText(resources.getString(R.string.Select_Problem));

        c1.setText(resources.getString(R.string.need_help_for_others));
        c2.setText(resources.getString(R.string.need_help_for_yourself));
        enterinfo.setText(resources.getString(R.string.enter_information));
        takeimage.setText(resources.getString(R.string.use_camera));
        idd.setText(resources.getString(R.string.please_add_any_government_id_proof_like_aadhar_card_pan_card_voter_id_driving_liscence_rashan_card));

        orr.setText(resources.getString(R.string.or_give_address_below));

        TypeOfHelp.setText(resources.getString(R.string.select_type_of_help));
        get_current_location.setText(resources.getString(R.string.get_current_location));
        name.setHint(resources.getString(R.string.Name));
        ocupation.setHint(resources.getString(R.string.Occupation));
        adress.setHint(resources.getString(R.string.Address));
        tr.setText(resources.getString(R.string.thankyou_for_putting_your_request_on_our_app));
        yr.setText(resources.getString(R.string.submitted_sucessfully));


    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "new");
        values.put(MediaStore.Images.Media.DESCRIPTION, "year");
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, 0033);

    }
    private void initGoogleMap() {

        if (isGPSEnabled()) {
            if (checkLocationPermission()) {
            } else {
                requestLocationPermission();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isGPSEnabled() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled) {

            return true;

        } else {

            androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("GPS Permissions")
                    .setMessage("GPS is required for this app to work. Please enable GPS.")
                    .setPositiveButton("Yes", (new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            HelpPage.this.startActivityForResult(intent, GPS_REQUEST_CODE);
                        }
                    }))
                    .setCancelable(false)
                    .show();

        }

        return false;
    }

    private boolean checkLocationPermission() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }


    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getLocation();
            submit_request.setVisibility(View.VISIBLE);

        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
        if(requestCode==005 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_REQUEST_CODE) {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnabled) {
                Toast.makeText(this, "GPS is enabled", Toast.LENGTH_SHORT).show();
                initGoogleMap();
            } else {
                initGoogleMap();
            }
        }
        if(resultCode==RESULT_OK){
            idproof.setVisibility(View.VISIBLE);
            idproof.setImageURI(uri);
        }
    }

    private void getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                lat = geoPoint.getLatitude();
                lng = geoPoint.getLongitude();
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