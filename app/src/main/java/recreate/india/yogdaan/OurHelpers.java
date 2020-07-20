package recreate.india.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OurHelpers extends AppCompatActivity {
    private FirebaseFirestore ff;
    private RecyclerView ourhelpersview;
    FirestoreRecyclerAdapter adapter;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_helpers);
        actionBar = this.getActionBar();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        ff = FirebaseFirestore.getInstance();
        ourhelpersview = findViewById(R.id.ourhelpersview);
        //Query
        Query query = ff.collection("CollectionNgo");
        //RecyclerOptions
        FirestoreRecyclerOptions<NgoModel> options = new FirestoreRecyclerOptions.Builder<NgoModel>().setQuery(query, NgoModel.class).build();
        adapter = new FirestoreRecyclerAdapter<NgoModel, PostViewHolder>(options) {

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ngo_item, parent, false);
                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i, @NonNull NgoModel postModal) {

            }
        };
        ourhelpersview.setLayoutManager(new LinearLayoutManager(this));
        ourhelpersview.setAdapter(adapter);
        ourhelpersview.setHasFixedSize(true);
    }
    //ViewHolder


    private class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView postImage;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}