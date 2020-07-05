package recreate.india.yogdaan;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class OurWorkFragment extends Fragment {

    private RecyclerView blog_list_view;
    private List<OurWorkModel> list;
    private FirebaseFirestore ff;
    private OurWorkBlogRecyclerAdapter blogRecyclerAdapter;
    FirestoreRecyclerAdapter adapter;
    Context context;
    public OurWorkFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_our_work, container, false);
        ff=FirebaseFirestore.getInstance();
        context=getActivity();
        list=new ArrayList<>();
        list.add(new OurWorkModel());
        blogRecyclerAdapter=new OurWorkBlogRecyclerAdapter(list,getContext());
        blog_list_view=view.findViewById(R.id.ourWorkRecyclerView);

        Query query = ff.collection("OurWorkPost");
        //RecyclerOptions
        FirestoreRecyclerOptions<OurWorkModel> options = new FirestoreRecyclerOptions.Builder<OurWorkModel>().setQuery(query, OurWorkModel.class).build();
        adapter = new FirestoreRecyclerAdapter<OurWorkModel, OurWorkFragment.PostViewHolder>(options) {

            @NonNull
            @Override
            public OurWorkFragment.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new OurWorkFragment.PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OurWorkFragment.PostViewHolder postViewHolder, int i, @NonNull OurWorkModel ourWorkModel) {
                    postViewHolder.name.setText(ourWorkModel.getTitle());
                    postViewHolder.date.setText(ourWorkModel.getCreated());
                    String url=ourWorkModel.getImage();
                    postViewHolder.desc.setText(ourWorkModel.getBody());
                    Glide.with(context).load(url).into(postViewHolder.postImage);
            }
        };
        blog_list_view.setLayoutManager(new LinearLayoutManager(getContext()));
        blog_list_view.setAdapter(adapter);
        blog_list_view.setHasFixedSize(true);
        return view;
    }
    //ViewHolder


        public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView name,desc;
        private ImageView postImage;
        private TextView date;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            desc=itemView.findViewById(R.id.postdesc);
            name=itemView.findViewById(R.id.NgoName);
            postImage=itemView.findViewById(R.id.postImage);
            date=itemView.findViewById(R.id.Postdate);


        }
    }

    @Override
        public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
        public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}