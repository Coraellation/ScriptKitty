package com.example.cora.scriptkitty;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import java.io.BufferedReader;

public class speech extends Activity {

    private TextToSpeech tts;
    private TextView txtDisplay;
    private Button btnSpeak;
    private ArrayList<String> lineList;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Important Android stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        // Initializing stuff
        txtDisplay = (TextView)findViewById(R.id.txtScript);
        tts=new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            tts.setLanguage(Locale.UK);
                        }
                    }
                });
        // Global variables
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "script.txt";
        File myScript = new File(fileDir + File.separatorChar + fileName);
        lineList = readFile(myScript);
        txtDisplay.setText(lineList.get(0));

    }

    public static ArrayList<String> readFile(File f) {
        ArrayList<String> lineList = new ArrayList<String>();
        if (f.isFile() && f.canRead()) {
            // Initialize thefilereaders
            FileReader fr = null;
            BufferedReader br;
            String lineThis;

            // Initialize fileReader
            try {
                fr = new FileReader(f);
            } catch (Exception e) {

            }
            // Initialize bufferedReader
            try {
                br = new BufferedReader(fr);
                while ((lineThis = br.readLine()) != null) {
                    lineList.add(lineThis);
                }
                br.close();
            }
            catch (IOException e) {

            }
        } else {
        }
        return lineList;
    }

    @Override
    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speech, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void speakText(View view){
        if (i < lineList.size()) {
            String toSpeak = lineList.get(i);
            Toast.makeText(getApplicationContext(), toSpeak,
                    Toast.LENGTH_SHORT).show();
            tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            i++;
        }
    }
}
