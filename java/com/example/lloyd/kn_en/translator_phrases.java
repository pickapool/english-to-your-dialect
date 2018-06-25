package com.example.lloyd.kn_en;

import android.content.Intent;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class translator_phrases extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listadapter;
    private List<String> listDataHeader;
    private HashMap<String,String> listHashMap;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greetings);


        listView = (ExpandableListView) findViewById(R.id.listview);
        initData();
        listadapter = new ExpandableListAdapter(this, listDataHeader, listHashMap);
        listView.setAdapter(listadapter);

        final int[] previous = {-1};
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if (previous[0] >= 0 && previous[0] != i) {
                    listView.collapseGroup(previous[0]);

                }
                previous[0] = i;
            }
        });
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long b) {
                String get = String.valueOf(expandableListView.getExpandableListAdapter().getChild(i, 0));

                t1.speak(get, TextToSpeech.QUEUE_FLUSH, null);
                return false;
            }
        });
        //text to speech
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void initData() {

        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        List<String> exam = new ArrayList<>();

        myDatabase db = new myDatabase(getApplicationContext());
        db.OpenDatabase();
        Cursor cursor = db.getWritableDatabase().rawQuery("Select english from phrases_"+getPrefix(),null);
        while (cursor.moveToNext()){
            listDataHeader.add(cursor.getString(0));
        }
        db.closeDatabase();
        db.OpenDatabase();
        Cursor cursor1 = db.getWritableDatabase().rawQuery("Select kinaraya from phrases_"+getPrefix(),null);
        while (cursor1.moveToNext()){
            exam.add(cursor1.getString(0));
        }
        db.closeDatabase();

        for(int i=0;i<listDataHeader.size();i++){
            listHashMap.put(listDataHeader.get(i),exam.get(i));

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        if(t1!=null){
            t1.stop();
            t1.shutdown();
        }

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public String getPrefix(){
        Intent iii = getIntent();
        String gets = iii.getStringExtra("position");
        return gets;
    }
}
