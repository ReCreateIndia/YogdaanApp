package recreate.india.yogdaan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlogsFragment extends Fragment {

    private RecyclerView blog_list_view;
    private List<BlogPost> blog_list;
    private FirebaseFirestore ff;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    public BlogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_blogs , container, false);
        blog_list=new ArrayList<>();
        blog_list_view=view.findViewById(R.id.blog_list_view);
        blog_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        blog_list_view.setAdapter(blogRecyclerAdapter);
        blogRecyclerAdapter=new BlogRecyclerAdapter(blog_list);
        ff=FirebaseFirestore.getInstance();
        ff.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot doc:task.getResult()){
                    BlogPost blogPost=doc.toObject(BlogPost.class);
                    blog_list.add(blogPost);
                    blogRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
