package recreate.india.yogdaan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class NewPostActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView new_postImgae;
    private Button new_postButton;
    private EditText new_postDescription;
    private Uri mainImageUri;
    private boolean ischanged = false;
    private StorageReference mStorageRef;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        toolbar = findViewById(R.id.NewPostActivityToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add new post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        new_postButton = findViewById(R.id.NewPostButton);
        new_postDescription = findViewById(R.id.NewPostDescription);
        new_postImgae = findViewById(R.id.NewPostImage);
        new_postImgae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission();
            }
        });
        new_postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    String uniqueID = UUID.randomUUID().toString();
                    String timestamp= FieldValue.serverTimestamp().toString();
                    final StorageReference filepath=mStorageRef.child("post_image").child(uniqueID+".jpg");
                    filepath.putFile(mainImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url =uri.toString();
                                        Map<String,Object>map=new HashMap<>();
                                        map.put("Url", url);
                                        map.put("desc",new_postDescription.getText().toString());
                                        //  map.put("userid",firebaseUser.getUid());
                                        map.put("servertimestamp",FieldValue.serverTimestamp().toString());
                                        firebaseFirestore.collection("posts").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if(task.isSuccessful()){
                                                    startActivity(new Intent(NewPostActivity.this,BlogActivity.class));
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                });


                            }
                        }
                    });
            }
        });

    }

    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(NewPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(NewPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(NewPostActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();

            } else {
                ActivityCompat.requestPermissions(NewPostActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        002);
            }

        } else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }


    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,001);
    }


    // when user picked an image ...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode ==001 && data != null) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            mainImageUri = data.getData();
            new_postImgae.setImageURI(mainImageUri);

        }
    }
}

