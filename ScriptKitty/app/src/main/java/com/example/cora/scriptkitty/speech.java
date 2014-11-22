package com.example.cora.scriptkitty;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
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

public class speech extends Activity implements TextToSpeech.OnInitListener {
    // SUPER IMPORTANT GLOBAL VARIABLES

    private String strComputer;
    private String strUser;
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
        btnSpeak = (Button)findViewById(R.id.btnSpeak);
        txtDisplay = (TextView)findViewById(R.id.txtScript);
        tts = new TextToSpeech(this, this);
        strComputer = "Regina";
        strUser = "Cady";

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                speakOut();
            }

        });

        // Global variables
        String fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "script.txt";
        File myScript = new File(fileDir + File.separatorChar + fileName);
        lineList = readFile(myScript, strComputer);
        txtDisplay.setText("uhh this is just the initialized value");

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public static ArrayList<String> readFile(File f, String character) {
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

    private void speakOut() {
        if (i < lineList.size()) {
            String text = lineList.get(i);
            txtDisplay.setText(text);
            tts.speak(text.substring(strComputer.length() + 1), TextToSpeech.QUEUE_FLUSH, null);
            i+=2;
        } else {
            tts.speak("End of script", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
