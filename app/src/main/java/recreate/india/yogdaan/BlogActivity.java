package recreate.india.yogdaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.zip.Inflater;


public class BlogActivity extends AppCompatActivity {
    private Button addpostbutton;
    FirebaseFirestore ff;
    private RecyclerView mfirestorelist;
    FirestoreRecyclerAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RaiseFundFragment()).commit();
        bottomNavigationView=findViewById(R.id.bottomnavview);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner=  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            switch(menuItem.getItemId()){
                case R.id.rf:selectedFragment=new RaiseFundFragment();break;
                case R.id.ow:selectedFragment=new OurWorkFragment();break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }

    };

//        addpostbutton=findViewById(R.id.add_post_button);
//        addpostbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(BlogActivity.this,AddPostActivity.class));
//            }
//        });
//        ff=FirebaseFirestore.getInstance();
//        mfirestorelist=findViewById(R.id.firestore_list);
//        //Query
//        Query query=ff.collection("OurWorkPost");
//        //RecyclerOptions
//        FirestoreRecyclerOptions<PostModal> options=new FirestoreRecyclerOptions.Builder<PostModal>().setQuery(query,PostModal.class).build();
//         adapter= new FirestoreRecyclerAdapter<PostModal, PostViewHolder>(options) {
//
//            @NonNull
//            @Override
//            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
//                return new PostViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i, @NonNull PostModal postModal) {
//
//                postViewHolder.description.setText(postModal.getDesc());
//                String url=postModal.getUrl();
//                Glide.with(BlogActivity.this).load(url).into(postViewHolder.postImage);
//            }
//        };
//         mfirestorelist.setLayoutManager(new LinearLayoutManager(this));
//         mfirestorelist.setAdapter(adapter);
//         mfirestorelist.setHasFixedSize(true);
//         //ViewHolder


//    private class PostViewHolder extends RecyclerView.ViewHolder{
//        private TextView description;
//        private ImageView postImage;
//        public PostViewHolder(@NonNull View itemView) {
//            super(itemView);
//           description=itemView.findViewById(R.id.desc);
//           postImage=itemView.findViewById(R.id.imageView);
//
//        }
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

}
