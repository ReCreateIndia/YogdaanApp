package recreate.india.yogdaan;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class OurWorkBlogRecyclerAdapter  extends RecyclerView.Adapter<OurWorkBlogRecyclerAdapter.ViewHolder> {

    List<OurWorkModel> list;
    Context context;
    public OurWorkBlogRecyclerAdapter(List<OurWorkModel> list,Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(list.get(position).getCreated());
        Glide.with(context).load(list.get(position).getImageUri()).into(holder.imageView);
        holder.body.setText(list.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private TextView imageUrl;
        private TextView body;
        private TextView title;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.Postdate);
            body=itemView.findViewById(R.id.desc);
            imageView=itemView.findViewById(R.id.postImage);
        }
    }
}
