package recreate.india.yogdaan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class RaiseFundFragment extends Fragment {

    private RecyclerView blog_list_view;
    private List<FundRaiseModel> list;
    private FirebaseFirestore ff;
    private RaiseFundBlogRecyclerAdapter blogRecyclerAdapter;
    public RaiseFundFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raise_fund, container, false);
        ff=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        blogRecyclerAdapter=new RaiseFundBlogRecyclerAdapter(list);
        blog_list_view=view.findViewById(R.id.raiseFundRecyclerView);
        blog_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        blog_list_view.setAdapter(blogRecyclerAdapter);
        ff.collection("FundRaiseBlogs").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange documentChange:queryDocumentSnapshots.getDocumentChanges()){
                    FundRaiseModel fundRaiseModel=documentChange.getDocument().toObject(FundRaiseModel.class);
                    list.add(fundRaiseModel);
                    blogRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }


}