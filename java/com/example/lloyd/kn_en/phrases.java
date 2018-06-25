package com.example.lloyd.kn_en;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lloyd on 5/19/2018.
 */

public class phrases extends Fragment {
    GridView gridView;
    ImageView imageView;
    TextView textView;

    String[] titless = {"GREETINGS","TRANSPORTATION","GENERAL CONVERSATION","ACCOMODATION","NUMBER","EATING OUT","TIME AND DATE","FAMILY","DIRECTIONS & PLACES","EMERGENCY"};
    int[] images ={R.drawable.greetings,R.drawable.trasportation,R.drawable.conversation,R.drawable.accomodation,R.drawable.numbers,R.drawable.eatingout,R.drawable.clock,R.drawable.family,R.drawable.direction,R.drawable.emergency};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.phrases,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     gridView = (GridView)getActivity().findViewById(R.id.gridview);

     CustomAdapter custom = new CustomAdapter();
     gridView.setAdapter(custom);
     gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

             Intent iii = new Intent(getContext(),translator_phrases.class);
             String getposition = String.valueOf(position);
             iii.putExtra("position",getposition);
             startActivity(iii);
         }
     });

    }
    private class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titless.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View mview = getLayoutInflater().inflate(R.layout.rowdata,null);

            textView = mview.findViewById(R.id.titles);
            imageView = mview.findViewById(R.id.image);

            textView.setText(titless[i]);
            imageView.setImageResource(images[i]);

            return mview;
        }
    }
}
