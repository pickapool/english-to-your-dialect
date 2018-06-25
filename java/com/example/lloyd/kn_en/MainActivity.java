package com.example.lloyd.kn_en;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    private  PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        setUpViewPager(viewPager);


        myDatabase db;
        db = new myDatabase(this);
        try {
            File database = this.getDatabasePath(myDatabase.DATABASE_NAME);
            if (false == database.exists()) {
                db.getReadableDatabase();
                if (copyDatabase(this)) {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    return;
                }
            }

        } catch (Exception ee) {
            Toast.makeText(this, ee.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void setUpViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new home(),"HOME");
        adapter.addFragment(new phrases(), "PHRASES");
        adapter.addFragment(new translator(), "TRANSLATOR");
        adapter.addFragment(new quiz(),"QUIZ");
        adapter.addFragment(new about_us(), "ABOUT US");

        viewPager.setAdapter(adapter);
        //tabLayout.getTabAt(0).setIcon(R.drawable.user);
       // tabLayout.getTabAt(1).setIcon(R.drawable.list);
       // tabLayout.getTabAt(2).setIcon(R.drawable.chart);


    }
    private boolean copyDatabase (Context context){

        try{
            InputStream inputStream = context.getAssets().open(myDatabase.DATABASE_NAME);
            String outFileName  = myDatabase.LOCATION + myDatabase.DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int lenth = 0;
            while ((lenth=inputStream.read(buff))>0){
                outputStream.write(buff,0,lenth);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("Mainactivity","DB copied");
            return true;
        }catch (Exception ee){
            Toast.makeText(this,ee.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        }
    }
    }

