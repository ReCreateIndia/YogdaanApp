package recreate.india.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.HashMap;
import java.util.Map;

import Helper.LocaleHelper;
import io.paperdb.Paper;

public class AddPostActivity extends AppCompatActivity {

    private EditText description;
    private Button submitthis;
    private ImageView newimage;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore ff;
    private Uri pickedImgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        firebaseAuth=FirebaseAuth.getInstance();
        ff=FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        description=findViewById(R.id.new_desc);
        newimage=findViewById(R.id.new_post);
        submitthis=findViewById(R.id.submit);
        newimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission();
            }
        });
        submitthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("blog_images");
                final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageDownlaodLink = uri.toString();
                                Map<String,Object>map=new HashMap<>();
                                map.put("url",imageDownlaodLink);
                                map.put("desc",description.getText().toString());
                                ff.collection("OurWorkPost").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            startActivity(new Intent(AddPostActivity.this,BlogActivity.class));
                                        }
                                        else{
                                            Toast.makeText(AddPostActivity.this,"error",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // something goes wrong uploading picture
                                Toast.makeText(AddPostActivity.this,"error",Toast.LENGTH_LONG).show();


                            }
                        });


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

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this,lang);
        Resources resources = context.getResources();

        description.setHint(resources.getString(R.string.Add_Some_Description));

    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(AddPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(AddPostActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(AddPostActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        001);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }
    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,002);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 002 && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            newimage.setImageURI(pickedImgUri);

        }


    }
}
