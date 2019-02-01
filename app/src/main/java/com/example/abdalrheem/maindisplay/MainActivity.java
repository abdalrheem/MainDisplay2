package com.example.abdalrheem.maindisplay;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static File extStore = Environment.getExternalStorageDirectory();
    static String videoPath =extStore.getAbsolutePath()+"/sounds/";
    static StringBuilder builder,builder1;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,c1,c2,c3,c4,c5,c6,c7,c8;
    TextView []TV={t1,t2,t3,t4,t5,t6,t7,t8};
    TextView []CN={c1,c2,c3,c4,c5,c6,c7,c8};
    static MediaController mediaController1;
    static int counter,counter1=0,counter3=0;
    static int d1,d2,d3,d4,mod,tick,tick1,countCall=0,flag=0;
    static String  myQueu [][] = new String [1000][1000];
    static MediaPlayer s1,s2,s3,s4,s5,and;
    private  RequestQueue requestQueue;
    static MediaPlayer media[]=new MediaPlayer[6];
    static int currentMedia =0,Ccounter =0;
    String arrayofcounter[]={"empty.wav","one.wav","two.wav","three.wav","four.wav","five.wav","six.wav"};
    String firstD[]={"empty.wav","onesawsen.wav","twosawsen.wav","threesawsen.wav"};
    String secondD[]={"empty.wav","handret.wav","twohandret.wav","threehandred.wav","fourhandret.wav","fivehandret.wav"};
    String third[]={"empty.wav","teen.wav","twenty.wav","therty.wav","fourty.wav","fifty.wav","sixty.wav","seventy.wav","eighty.wav","ninty.wav"};
    String forth[]={"empty.wav","one.wav","two.wav","three.wav","four.wav","five.wav","six.wav","seven.wav","eight.wav","nine.wav"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TV[0] = findViewById(R.id.t1);
        CN[0] = findViewById(R.id.c1);
        TV[1] = findViewById(R.id.t2);
        CN[1] = findViewById(R.id.c2);
        TV[2] = findViewById(R.id.t3);
        CN[2] = findViewById(R.id.c3);
        TV[3] = findViewById(R.id.t4);
        CN[3] = findViewById(R.id.c4);
        TV[4] = findViewById(R.id.t5);
        CN[4] = findViewById(R.id.c5);
        TV[5] = findViewById(R.id.t6);
        CN[5] = findViewById(R.id.c6);
        TV[6] = findViewById(R.id.t7);
        CN[6] = findViewById(R.id.c7);
        TV[7] = findViewById(R.id.t8);
        CN[7] = findViewById(R.id.c8);

        /*  Video steps     */
        VideoView videoView=findViewById(R.id.Video);
        String videoPath1=extStore.getAbsolutePath()+"/video.mp4";
        File extStore = Environment.getExternalStorageDirectory();
        Uri uri=Uri.parse(videoPath1);
        videoView.setVideoURI(uri);
        MediaController mediaController =new MediaController(this);
        mediaController1 =new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        videoView.start();
        // function for request the calling every 2 secons
        scheduleSendLocation();
    }

    // move to the setting activity using this funtion
    public void sendMessage(View view)
    {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }
    private final int FIVE_SECONDS = 5000;
    public void scheduleSendLocation() {
        final Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                requestQueue=null;
                NameOfRunnable.run();          // this method will contain your almost-finished HTTP calls
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }

    // waiting function until the current sound compelete then change the flag
    public void waitFun() {

        final Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, FIVE_SECONDS);
            }
        }, FIVE_SECONDS);
        return;
    }

    // get ticket funtion (send request to the API using volly, get the response on json object in json array then forward the ticket number to the first calling function)
    public Runnable NameOfRunnable = new Runnable()
    {
        @Override
        public void run()
        {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if(flag==0) {
            flag=1;
            String servIP = "http://172.20.10.3/MainDisplay/MainDisplayAPI.php";//pref.getString("serverIP","");
            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(MainActivity.this);
            }
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, servIP, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        counter1 = counter;
                        JSONArray jsonArray = response.getJSONArray("Res");
                        JSONObject object1 = jsonArray.getJSONObject(0);
                        String check =object1.getString("Total");
                        if (check.equals("noTicket")) {
                            flag=0;
                            waitFun();
                        }
                        else{
                            jsonArray = response.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String TicketNumber = object.getString("TicketNumber");
                                String CounterNumber = object.getString("CounterNumber");
                                CounterNumber = CounterNumber.substring(4,5);
                                myQueu[counter][0] = TicketNumber;
                                myQueu[counter][1] = CounterNumber;
                                counter++;
                            }
                            callTicket(myQueu);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(request);
        }
        else
        {
            waitFun();
        }
        }
    };
    // first calling fun() get array fo string content ticket number at 0 and counter number at 1 then set them at textView and genret the sounds in array of
    // sounds and send the sounds to the second calling fun()
    private void callTicket(String[][] queu) {
        TV[counter3].setText(queu[counter1][0]);
        CN[counter3].setText(queu[counter1][1]);
        final MediaPlayer ticketNumber =  MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+"ticketnumber.wav"));
        final MediaPlayer counNumber =  MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+"counternumber.wav"));
        TV[counter3].setTextColor(Color.rgb(0,0,0));
        CN[counter3].setTextColor(Color.rgb(0,0,0));
        tick=Integer.parseInt(queu[counter1][0]);
        tick1=tick;
        int counterNumber=Integer.parseInt(queu[counter1][1]);
        countCall=counterNumber;
        d1=tick/1000;
        tick=tick%1000;
        d2=tick/100;
        tick=tick%100;
        d3=tick/10;
        tick=tick%10;
        d4=tick;
        and =MediaPlayer.create(MainActivity.this,Uri.parse(videoPath+"and.wav"));
        char[] arabicChars = {'٠','١','٢','٣','٤','٥','٦','٧','٨','٩'};
        builder = new StringBuilder();
        builder1 = new StringBuilder();
        String Ticket = tick1+"";
        String counter = counterNumber+"";
        for(int j =0;j<Ticket.length();j++)
        {
            if(Character.isDigit(Ticket.charAt(j)))
            {
                builder.append(arabicChars[(int)(Ticket.charAt(j))-48]);
            }
            else
            {
                builder.append(Ticket.charAt(j));
            }
        }
        for(int j =0;j<counter.length();j++)
        {
            if(Character.isDigit(counter.charAt(j)))
            {
                builder1.append(arabicChars[(int)(counter.charAt(j))-48]);
            }
            else
            {
                builder1.append(counter.charAt(j));
            }
        }
        s5=MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+arrayofcounter[counterNumber]));
        s1 = MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+firstD[d1]));
        s2 = MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+secondD[d2]));
        s3 = MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+third[d3]));
        s4 = MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+forth[d4]));
            media[Ccounter] = ticketNumber;
            Ccounter++;
        if(d1 !=0) {
            media[Ccounter] = s1;
            Ccounter++;
            media[Ccounter]=and;
            Ccounter++;
        }
        if(d2!=0){
            media[Ccounter] = s2;
            Ccounter++;
            media[Ccounter]=and;
            Ccounter++;
        }
        if((tick1%100)==11) {
            media[Ccounter] = MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+"eleven.wav"));
            Ccounter++;
        }
        else
        if((tick1%100)==12) {
            media[Ccounter] = MediaPlayer.create(MainActivity.this, Uri.parse(videoPath+"twelve.wav"));
            Ccounter++;
        }
         else {
            if (d4 != 0 && d3 != 0) {
                media[Ccounter] = s4;
                Ccounter++;
                media[Ccounter] = and;
                Ccounter++;
            } else if (d4 != 0 && d3 == 0) {
                media[Ccounter] = s4;
                Ccounter++;
            }
            if (d3 != 0) {
                media[Ccounter] = s3;
                Ccounter++;
            }
        }
        media[Ccounter] = counNumber;
        Ccounter++;
        media[Ccounter] = s5;
        mediaPlay();
        }

        //it's reqerjen function  get the sounds in an array and inishlize one media player then play all sounds in array and change the color of the textview and set zem in arabic and
    // change to english every sound played after the last sound compelete set the flag =0 and release the sound and empty tha array of sound
    public void mediaPlay(){

        MediaPlayer mediaPlayer =new MediaPlayer();
        mediaPlayer=media[currentMedia];
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(currentMedia < Ccounter){
                    media[currentMedia]=null;
                    currentMedia++;

                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlay();
                    if(currentMedia%2==0){
                        TV[counter3].setText(builder.toString());
                        CN[counter3].setText(builder1.toString());
                        TV[counter3].setTextSize(50);
                        CN[counter3].setTextSize(50);
                        TV[counter3].setTextColor(Color.rgb(255,0,0));
                        CN[counter3].setTextColor(Color.rgb(255,0,0));
                    }
                    else
                    {
                        TV[counter3].setText(tick1+"");
                        CN[counter3].setText(countCall+"");
                        TV[counter3].setTextSize(30);
                        CN[counter3].setTextSize(30);
                        TV[counter3].setTextColor(Color.rgb(0,0,0));
                        CN[counter3].setTextColor(Color.rgb(0,0,0));
                    }
                }
                else
                {
                    requestQueue=null;

                    mediaPlayer.reset();
                    mediaPlayer.release();
                    flag=0;
                    Ccounter =0;
                    currentMedia=0;
                    if(counter3==7)
                        counter3=0;
                    else
                        counter3++;
                }
            }
        });
        mediaPlayer.start();
    }
}