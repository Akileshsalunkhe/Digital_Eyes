package org.tensorflow.lite.examples.detection;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Locale;
import java.util.Timer;

public class dashboard extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    TextToSpeech t1;
    private static Context mContext;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_dashboard);
        mContext = getApplicationContext();
        /*
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    t1.setLanguage(Locale.UK);
                }
                t1.speak("Very good morning", TextToSpeech.QUEUE_FLUSH, null);
            }
        });*/

        CardView cv1 = findViewById(R.id.scanobject);
        CardView cv2 = findViewById(R.id.readtext);
        MediaPlayer play = MediaPlayer.create(dashboard.this, R.raw.welcome_msg);
        play.start();

        final MediaPlayer create = MediaPlayer.create(this, R.raw.object_detection);
        final MediaPlayer create2 = MediaPlayer.create(this, R.raw.read_text);

        final Timer timer = new Timer();
        cv1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                create.start();
                dashboard.this.startActivity(new Intent(dashboard.this, DetectorActivity.class));

            }
        });
        cv2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                create2.start();
                dashboard.this.startActivity(new Intent(dashboard.this, Read_Text.class));

                /* create2.start();
                dashboard.this.startActivity(new Intent(dashboard.this, Read_Text.class));
                timer.schedule(new TimerTask() {
                    public void run() {
                        create3.start();
                    }
                }, 3000);*/
            }
        });


    }
    public Context getContext() {
        return mContext;
    }
    /*
    public void speak_object(String object){
    t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
      @Override
      public void onInit(int i) {
        if(i!=TextToSpeech.ERROR){
          t1.setLanguage(Locale.UK);
        }
      }
    });
    t1.speak(object,TextToSpeech.QUEUE_FLUSH,null);
        Toast toast =
                Toast.makeText(
                        this, object, Toast.LENGTH_SHORT);
        toast.show();
    }*/
}
