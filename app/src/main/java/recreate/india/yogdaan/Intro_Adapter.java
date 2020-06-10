package recreate.india.yogdaan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Intro_Adapter extends PagerAdapter {
    private Context context;
    private int[] layouts;
    private LayoutInflater inflater;
    private ImageButton lgn;
    private Spinner spin;
    private Activity activity;
    public Intro_Adapter(Context context, int[] layouts, Activity activity)
    {
        this.context = context;
        this.layouts = layouts;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = inflater.inflate(layouts[position],container,false);
        if(position==0){
            spin=view.findViewById(R.id.spinner2);
            List<String> list=new ArrayList<String>();
            list.add(0,"Select language");
            list.add("English");
            list.add("Hindi");
            list.add("Bengali");
            list.add("Urdu");
            list.add("Gujarati");
            list.add("Marathi");
            list.add("Assamese");
            list.add("Kannada");
            list.add("Kashmiri");
            list.add("Konkani");
            list.add("Maithili");
            list.add("Malayalam");
            list.add("Manipuri");
            list.add("Nepali");
            list.add("Oriya");
            list.add("Punjabi");
            list.add("Sanskrit");
            list.add("Santhali");
            list.add("Sindhi ");
            list.add("Tamil");
            list.add("Telugu");
            list.add("Bodo");
            list.add("Dogri");

            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,list);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(arrayAdapter);
            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(parent.getItemAtPosition(position).equals("Select problem")){

                    }
                    else
                    {
                        String item=parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), "Selected:"+item, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });
        }

        if(position==3) {
            lgn = view.findViewById(R.id.lgn);
            lgn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    context.startActivity(intent);
                    activity.finish();

                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
