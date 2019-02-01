package com.example.abdalrheem.maindisplay;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.example.abdalrheem.maindisplay.MainActivity.d1;
import static com.example.abdalrheem.maindisplay.MainActivity.myQueu;

/**
 * Created by Abdalrheem on 1/30/2019.
 */

public final class DigitsToWords {

    static File extStore = Environment.getExternalStorageDirectory();
    static String videoPath =extStore.getAbsolutePath()+"/";
    String arrayofcounter[]={"empty.wav","one.wav","two.wav","three.wav","four.wav","five.wav","six.wav"};
    String firstD[]={"empty.wav","onesawsen.wav","twosawsen.wav","threesawsen.wav"};
    String secondD[]={"empty.wav","handret.wav","twohandret.wav","threehandred.wav","fourhandret.wav","fivehandret.wav"};
    String third[]={"empty.wav","teen.wav","twenty.wav","therty.wav","fourty.wav","fifty.wav","sixty.wav","seventy.wav","eighty.wav","ninty.wav"};
    String forth[]={"empty.wav","one.wav","two.wav","three.wav","four.wav","five.wav","six.wav","seven.wav","eight.wav","nine.wav"};

    static final Map<Integer,String> UNITS_MAP;
    static final Map<Integer,String> TENS_MAP;
    static final Map<Integer,String> ZEROS_MAP;

    MediaPlayer mpUnit ;

    double digits;

    Context context;

    static{
        UNITS_MAP = new HashMap();
        TENS_MAP = new HashMap();
        ZEROS_MAP = new HashMap();

        UNITS_MAP.put(0, "empty.wav");
        UNITS_MAP.put(1, "one.wav");
        UNITS_MAP.put(2, "two.wav");
        UNITS_MAP.put(3, "three.wav");
        UNITS_MAP.put(4, "four.wav");
        UNITS_MAP.put(5, "five.wav");
        UNITS_MAP.put(6, "six.wav");
        UNITS_MAP.put(7, "seven.wav");
        UNITS_MAP.put(8, "eight.wav");
        UNITS_MAP.put(9, "nine.wav");

        TENS_MAP.put(10, "teen.wav");
        TENS_MAP.put(11, "eleven.wav");
        TENS_MAP.put(12, "twelve.wav");
        TENS_MAP.put(13, "Thirteen");
        TENS_MAP.put(14, "Fourteen");
        TENS_MAP.put(15, "Fifteen");
        TENS_MAP.put(16, "Sixteen");
        TENS_MAP.put(17, "Seventeen");
        TENS_MAP.put(18, "Eighteen");
        TENS_MAP.put(19, "Nineteen");

        TENS_MAP.put(20, "twenty.wav");
        TENS_MAP.put(30, "therty.wav");
        TENS_MAP.put(40, "fourty.wav");
        TENS_MAP.put(50, "fifty.wav");
        TENS_MAP.put(60, "sixty.wav");
        TENS_MAP.put(70, "seventy.wav");
        TENS_MAP.put(80, "eighty.wav");
        TENS_MAP.put(90, "ninty.wav");

        ZEROS_MAP.put(2,"Hundred");
        ZEROS_MAP.put(3,"onesawsen.wav");
        ZEROS_MAP.put(4,"twosawsen.wav");
        ZEROS_MAP.put(6,"Million");
        ZEROS_MAP.put(9,"Billion");
        ZEROS_MAP.put(12,"Trillion");
    }

    public DigitsToWords() {
    }

    public DigitsToWords(Context c, long digits){
        this.digits = digits;
        this.context = c;
    }

    public DigitsToWords(double digits){
        this.digits = digits;
    }

    private int length(){
        return String.valueOf(digits).length();
    }

    private String getUnit(int unit){
        return UNITS_MAP.get(unit);
    }



     MediaPlayer playUnit(int unit){

        mpUnit = MediaPlayer.create(context, Uri.parse(videoPath+UNITS_MAP.get(unit)));

        return mpUnit;

    }

    MediaPlayer mpTens;
    MediaPlayer playTens(int tens){
        int d = tens % 10;

        int t = tens - d;

        mpTens = MediaPlayer.create(context, Uri.parse(videoPath+UNITS_MAP.get(t)));

        if (d != 0 && tens > 19){
            MediaPlayer mp = playUnit(d);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    MediaPlayer mp = playAnd();
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mpTens.start();
                        }
                    });
                }
            });
            return mpTens;
        }

        mpTens = MediaPlayer.create(context, Uri.parse(videoPath+UNITS_MAP.get(tens)));

        return mpTens;
    }

    private MediaPlayer playAnd() {
        MediaPlayer mpAnd = MediaPlayer.create(context, Uri.parse(videoPath+"and.wav"));

        return mpAnd;
    }

    private String getTens(int tens){

        int d = tens % 10;

        int t = tens - d;

        if (d != 0 && tens > 19){
            return TENS_MAP.get(t) + " " + getUnit(d);
        }
        return TENS_MAP.get(tens);
    }

    private String getHundreds(int hundreds){

        String hWord;

        int h = hundreds/100;

        int rem = hundreds % 100;

        hWord = convert(h);

        hWord += " " + ZEROS_MAP.get(2);

        if (rem != 0)
            hWord += " and " + convert(rem);

        return hWord;

    }

    private String getThousands(int thousands){

        String tWord;

        int t = thousands/1000;

        int rem = thousands % 1000;

        tWord = convert(t);

        if (t == 1){
            tWord = ZEROS_MAP.get(3);
        }
        else if (t == 2){
            tWord = ZEROS_MAP.get(4);
        }
        else {
            tWord += " " + ZEROS_MAP.get(3);
        }

        if (rem != 0 ){
            tWord += (rem < 100) ? " and " : " ";
            tWord += convert(rem);
        }

        return tWord;
    }

    private String getMillions(int millions){

        String mWord;

        int m = millions / 1000000;

        int rem = millions % 1000000;

        mWord = convert(m);

        mWord += " " + ZEROS_MAP.get(6);

        if (rem != 0){
            mWord += (rem < 100) ? " and " : " ";
            mWord += convert(rem);
        }


        return mWord;
    }

    private String getBillions(long billions){

        String bWord;

        int b = (int) (billions / 1000000000);

        int rem = (int) (billions % 1000000000);

        bWord = convert(b);

        bWord += " " + ZEROS_MAP.get(9);

        if (rem != 0){
            bWord += (rem < 100) ? " and " : " ";
            bWord += convert(rem);
        }

        return bWord;
    }

    private String getTrillions(long trillions){

        String tWord;

        int b = (int) (trillions / 1000000000000l);

        int rem = (int) (trillions % 1000000000000l);

        tWord = convert(b);

        tWord += " " + ZEROS_MAP.get(12);

        if (rem != 0){
            tWord += (rem < 100) ? " and " : " ";
            tWord += convert(rem);
        }

        return tWord;
    }


    @Override
    public String toString(){
        return convert(this.digits);
    }

    public String convert(long digits){

        String words = "";

        if (digits < 0){
            words += "Negative ";
            digits = -digits;
        }

        int len = String.valueOf(digits).length();

        if (len == 1)
//            words += this.getUnit((int) digits);
            this.playUnit((int) digits).start();
        else if (len == 2)
//            words += this.getTens((int) digits);
            this.playTens((int) digits);
        else if (len == 3)
            words += this.getHundreds((int) digits);
        else if (len >= 4 && len <= 6)
            words += this.getThousands((int) digits);
        else if (len >= 7 && len <= 9)
            words += this.getMillions((int) digits);
        else if (len >= 10 && len <= 12)
            words += this.getBillions(digits);
        else if (len >= 11 && len <= 15)
            words += this.getTrillions(digits);

        return words;

    }

    public String convert(double digits){

        String s = String.valueOf(digits);

        String inWords;

        int index = s.indexOf(".");

        int wholeNumber = Integer.parseInt(s.substring(0,index));

        inWords = convert(wholeNumber);

        int fraction = Integer.parseInt(s.substring(index+1));

        if (fraction != 0){
            inWords += " Point";

            char[] ch = String.valueOf(fraction).toCharArray();

            for (char c : ch){

                inWords += " " + convert(Integer.parseInt(Character.toString(c)));
            }

        }

        return inWords;
    }

}
