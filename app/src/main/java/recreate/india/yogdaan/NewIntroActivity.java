package recreate.india.yogdaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewIntroActivity extends AppCompatActivity {
    private ViewPager pager;
    private LinearLayout dots;
    private TextView[] threedots;
    private NewIntroSliderAdapter newIntroSliderAdapter;
    private Button next;
    public int currentpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intro);
        next=findViewById(R.id.next);
        pager=findViewById(R.id.viewPager1);

        newIntroSliderAdapter=new NewIntroSliderAdapter(this);
        pager.setAdapter(newIntroSliderAdapter) ;
        if (currentpage==2){

        }
        else{
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(currentpage+1,true);
                }
            });
        }
//        adddotsindicator(0);
        pager.addOnPageChangeListener(viewListner);
    }
//    public void adddotsindicator(int position){
//        threedots=new TextView[3];
//        dots.removeAllViews();
//        for(int i =0;i<threedots.length;i++){
//            threedots[i]=new TextView(this);
//            threedots[i].setText(Html.fromHtml("&#8226;"));
//            threedots[i].setTextSize(35);
//            threedots[position].setTextColor(getResources().getColor(R.color.black));
//            dots.addView(threedots[i]);
//
////            if(threedots.length>0){
////                threedots[position].setTextColor(getResources().getColor(R.color.black));
////            }
//        }
//    }
    ViewPager.OnPageChangeListener viewListner=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
          //  adddotsindicator(position);
            currentpage=position;
            if (currentpage==2){
                next.setText("get started");
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(NewIntroActivity.this,LoginActivity.class));
                    }
                });
            }
            else{

                next.setText("next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}