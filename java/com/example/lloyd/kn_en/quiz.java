package com.example.lloyd.kn_en;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Lloyd on 5/19/2018.
 */

public class quiz extends Fragment {
    Button ek;
    Button ke;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.quiz, container, false);

        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ek = (Button)getActivity().findViewById(R.id.ek);
        ke= (Button)getActivity().findViewById(R.id.ke);
        ek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iii = new Intent(getActivity(),quizes.class);
                iii.putExtra("mess","ek");
                startActivity(iii);
            }
        });
        ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iii = new Intent(getActivity(),quizes.class);
                iii.putExtra("mess","ke");
                startActivity(iii);
            }
        });
    }
}
