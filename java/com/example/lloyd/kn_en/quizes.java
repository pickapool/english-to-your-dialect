package com.example.lloyd.kn_en;

import android.app.AlertDialog;
import android.content.Intent;

import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class quizes extends AppCompatActivity {

    AlertDialog.Builder builder;
    View mview;

    AlertDialog[] dialog;
    TextView titles;
    TextView quest;
    ImageView imageView;
    Button button1,button2,button3,button4;
    CountDownTimer countDownTimer;
    Random r;
    myDatabase db;
    TextView timer;
    TextView scores;

    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_quiz);

        mview = getLayoutInflater().inflate(R.layout.dialog_game_over, null);
        builder = new AlertDialog.Builder(quizes.this);
        builder.setView(mview);
        dialog = new AlertDialog[1];
        builder.setCancelable(false);

        score= (TextView)mview.findViewById(R.id.score);

        timer = (TextView)findViewById(R.id.timers);
        scores = (TextView)findViewById(R.id.scores);
        imageView = (ImageView)findViewById(R.id.pause);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.play);
            }
        });

        r = new Random();
        db = new myDatabase(getApplicationContext());
        titles = (TextView)findViewById(R.id.titled);

        if(getPrefix().equals("ek")){
            titles.setText("ENGLISH TO KINARAY-A");
        }else if(getPrefix().equals("ke")){
            titles.setText("KINARAY-A TO ENGLISH");
        }
        quest = (TextView)findViewById(R.id.question);
        button1 = (Button)findViewById(R.id.choices1);
        button2 = (Button)findViewById(R.id.choices2);
        button3 = (Button)findViewById(R.id.choices3);
        button4 = (Button)findViewById(R.id.choices4);


    getData();
        timerStart();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans = button1.getText().toString();
                getCorrectAnswer(ans);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans = button2.getText().toString();
                getCorrectAnswer(ans);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans = button3.getText().toString();
                getCorrectAnswer(ans);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans = button4.getText().toString();
                getCorrectAnswer(ans);
            }
        });
    }
private void getData(){
    db.OpenDatabase();
    String gets = "Select question,c1,c2,c3,c4 from "+getPrefix()+"_quizes where qID="+r.nextInt(11);
    Cursor cursor = db.getWritableDatabase().rawQuery(gets,null);
    while (cursor.moveToNext()){
        quest.setText(cursor.getString(0));
        button1.setText(cursor.getString(1));
        button2.setText(cursor.getString(2));
        button3.setText(cursor.getString(3));
        button4.setText(cursor.getString(4));

    }
    db.closeDatabase();
}

private void getCorrectAnswer(String answer){
    db.OpenDatabase();
    String correctAnswer = "Select qID from "+getPrefix()+"_quizes where question='"+quest.getText()+"'";
    Cursor cursor = db.getWritableDatabase().rawQuery(correctAnswer,null);
    if (cursor.moveToNext()){
        int ids = Integer.parseInt(cursor.getString(0));
        Cursor cursor1= db.getWritableDatabase().rawQuery("Select correctAnswer from "+getPrefix()+"_quizes where qID="+ids,null);
        if(cursor1.moveToNext()){
            String getanswer = cursor1.getString(0);
           // Toast.makeText(quizes.this,getanswer, Toast.LENGTH_SHORT).show();
            if(getanswer.equals(answer)){
                String gets = "Select question,c1,c2,c3,c4 from "+getPrefix()+"_quizes where qID="+r.nextInt(11);
                Cursor cursor2 = db.getWritableDatabase().rawQuery(gets,null);
                while (cursor2.moveToNext()){
                    quest.setText(cursor2.getString(0));
                    button1.setText(cursor2.getString(1));
                    button2.setText(cursor2.getString(2));
                    button3.setText(cursor2.getString(3));
                    button4.setText(cursor2.getString(4));

                }
                countDownTimer.cancel();
                randomScore();
                timerStart();
            }else{
               gameOver();
            }
            //Toast.makeText(quizes.this,cursor1.getString(0), Toast.LENGTH_SHORT).show();
        }
    }
    db.closeDatabase();
}
private String getPrefix(){
    Intent iii = getIntent();
    String get_i = iii.getStringExtra("mess");
    return get_i;
}
private void timerStart(){

    int seconds = 11;
    countDownTimer = new CountDownTimer(seconds*1000,1000) {
        @Override
        public void onTick(long millis) {
            timer.setText(String.valueOf((int)millis/1000));
        }

        @Override
        public void onFinish() {
            timer.setText("0");
           gameOver();
        }
    }.start();
}
private void randomScore(){
    int sum;
    int score = r.nextInt(310+79);
    // sum =+ Integer.parseInt(scores.getText().toString())+score;
    scores.setText(String.valueOf(Integer.parseInt(scores.getText().toString())+score));
}

private void gameOver(){

    dialog[0] = builder.create();
    countDownTimer.cancel();
    if(mview.getParent()!=null){
        ((ViewGroup)mview.getParent()).removeView(mview);
    }
    sendScore();
    dialog[0].show();

}

private void sendScore(){

    String getScore = scores.getText().toString();
    score.setText(getScore);
}

}
