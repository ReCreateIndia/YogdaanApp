package recreate.india.yogdaan;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class NewIntroSliderAdapter extends PagerAdapter {
    /**
     * Return the number of views available.
     */
    Context context;
    LayoutInflater layoutInflater;
    public NewIntroSliderAdapter(Context context){
        this.context=context;
    }
    public int[] slide_images = {
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3
    };
    public String[] slide_texts={
            "first","second","third"
    };
    @Override
    public int getCount() {
        return slide_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.singleintroslide,container,false);
        ImageView imageView=view.findViewById(R.id.slideimages);
        TextView textView=view.findViewById(R.id.slidetexts);
        imageView.setImageResource(slide_images[position]);
        textView.setText(slide_texts[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
