package com.example.lloyd.kn_en;

import android.content.Intent;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Lloyd on 5/19/2018.
 */

public class translator extends Fragment{

    ImageView imageView;
    TextView textView;
    View mview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.translator,container,false);

        return view;


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textView = (TextView)getActivity().findViewById(R.id.the);
        mview = getLayoutInflater().inflate(R.layout.translator, null);
        imageView = (ImageView)getActivity().findViewById(R.id.speakNow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak now");
                if(intent.resolveActivity(getContext().getPackageManager())!=null){
                    startActivityForResult(intent,10);
                }else{
                    Toast.makeText(getActivity(), "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10: {
                if (requestCode == 10 && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    myDatabase db = new myDatabase(getContext());
                    db.OpenDatabase();
                    Cursor cursor1 = db.getWritableDatabase().rawQuery("Select karaya from translator where english LIKE '%"+result.get(0)+"%'",null);
                    Toast.makeText(getContext(),result.get(0),Toast.LENGTH_SHORT).show();
                    while(cursor1.moveToNext()){
                        Toast.makeText(getContext(),cursor1.getString(0),Toast.LENGTH_SHORT).show();
                        textView.setText(cursor1.getString(0));
                    }

                }
                break;
            }
        }
    }
}
