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
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.zip.Inflater;


public class BlogActivity extends AppCompatActivity {
    private Button addpostbutton;
    FirebaseFirestore ff;
    private RecyclerView mfirestorelist;
    FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        addpostbutton=findViewById(R.id.add_post_button);
        addpostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BlogActivity.this,AddPostActivity.class));
            }
        });
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
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i, @NonNull PostModal postModal) {

                postViewHolder.list_username.setText(postModal.getUsername());
                postViewHolder.list_password.setText(postModal.getPassword());

            }
        };
         mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
         mfirestorelist.setAdapter(adapter);
         mfirestorelist.setHasFixedSize(true);

        //ViewHolder
    }

    private class PostViewHolder extends RecyclerView.ViewHolder{
        private TextView list_username;
        private TextView list_password;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            list_username=itemView.findViewById(R.id.name);
            list_password=itemView.findViewById(R.id.pass);

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
