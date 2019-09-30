package com.ashikrabbani.oresturent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class orderDetails extends Activity {

    TextView ordered__list,bdt_price_view,show_price_view,orderlistText;
    TextToSpeech t1;
    String bdt_price,usd_price;
    Button b1,b2,b3;
    String sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetails);



        b1= findViewById(R.id.listen);
        b2= findViewById(R.id.call);
        b3 = findViewById(R.id.sms);

        Bundle bundle = getIntent().getExtras();
        String data= bundle.getString("choice_iteams");
        bdt_price= bundle.getString("bdt_price");
        usd_price = bundle.getString("usd_price");
        // data converted for sms
        sms = data;


       // int total = bundle.getInt("total_price");

        orderlistText = findViewById(R.id.orderlistTextview);
       ordered__list = findViewById(R.id.orderDetailstextView);
       //bdt_price_view = findViewById(R.id.bdtprice);
      // bdt_price_view.setText(bdt_price);

       show_price_view = findViewById(R.id.showprice);
       show_price_view.setText("BDT : "+bdt_price+" TK\n"+"USD : "+usd_price+" $");

        ordered__list.setText(data);



        Typeface gatholic = ResourcesCompat.getFont(this, R.font.gatholic);

        ordered__list.setTypeface(gatholic);
        b1.setTypeface(gatholic);
        b2.setTypeface(gatholic);
        b3.setTypeface(gatholic);

        Typeface french = ResourcesCompat.getFont(this, R.font.french);

        orderlistText.setTypeface(french);


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ordered__list.getText().toString();
                t1.setPitch(0.8f);
                t1.setSpeechRate(0.8f);

                //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    t1.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null,null);
                } else {
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        /*
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+01943352550";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });*/
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("smsto:"));
                    i.setType("vnd.android-dir/mms-sms");
                    i.putExtra("address", new String("01617205200"));
                    i.putExtra("sms_body",sms);
                    startActivity(Intent.createChooser(i, "Send sms via:"));
                }
                catch(Exception e){
                    Toast.makeText(orderDetails.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });



            }



    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }



    public void call(View view)
    {
        String phone = "+0123456789";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }



    }








