package recreate.india.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.zip.Inflater;


public class FundRaiseActivity extends AppCompatActivity {
    private Button addpostbutton;
    FirebaseFirestore ff;
    private RecyclerView mfirestorelist;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_raise);

        ff=FirebaseFirestore.getInstance();
        mfirestorelist=findViewById(R.id.firestore_list);
        //Query
        Query query=ff.collection("users");
        //RecyclerOptions
        FirestoreRecyclerOptions<PostModal> options=new FirestoreRecyclerOptions.Builder<PostModal>().setQuery(query,PostModal.class).build();
        adapter= new FirestoreRecyclerAdapter<PostModal, PostViewHolder>(options) {

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_fund_item,parent,false);
                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i, @NonNull PostModal postModal) {

                postViewHolder.description.setText(postModal.getDesc());
                String url=postModal.getUrl();
                Glide.with(FundRaiseActivity.this).load(url).into(postViewHolder.postImage);

            }
        };
        mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mfirestorelist.setAdapter(adapter);
        mfirestorelist.setHasFixedSize(true);

        //ViewHolder
    }

    private class PostViewHolder extends RecyclerView.ViewHolder{
        private EditText description;
        private ImageView postImage;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.new_desc);
            postImage=itemView.findViewById(R.id.new_post);

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
