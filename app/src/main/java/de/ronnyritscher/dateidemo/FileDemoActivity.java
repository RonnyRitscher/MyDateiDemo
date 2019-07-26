package de.ronnyritscher.dateidemo;
/*
* DATEI speichern und laden -> auf externes Speichermedium
* */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileDemoActivity extends AppCompatActivity {

    //LOGTAG
    private static final String TAG = FileDemoActivity.class.getSimpleName();
    //DATEI-NAME
    private static final String FILENAME = TAG + ".txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.et_main_text);

        final Button load = findViewById(R.id.btn_laden);
        load.setOnClickListener( v -> editText.setText(load()));

        final Button save = findViewById(R.id.btn_speichern);
        save.setOnClickListener( v -> save(editText.getText().toString()));

        final  Button clear = findViewById(R.id.btn_leeren);
        clear.setOnClickListener( v -> editText.setText(""));

        //das Verzeichnis/ unser Pfad in den wir schreiben
        // angabe als absoluter Pfad wo unser output die datei ablegt
        File dir = getFilesDir();
        Log.d(TAG, "onCreate: PATH: " + dir.getAbsolutePath());


    }

    //SPEICHERN-----------------------------------------------------
    private void save(String text) {
        //MODE_PRIVATE -> nur die App darf darauf zugreifen, ersetzt die Datei
        //MODE_APPEND ->
        try(FileOutputStream fos = openFileOutput(FILENAME , MODE_PRIVATE );
            OutputStreamWriter osw = new OutputStreamWriter(fos)){

            //schreibe den Text in die Datei->
            osw.write(text);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "save: FileNotFoundException -> Datei nicht gefunden!", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "save: IOException -> ERROR ist aufgetreten", e);
            e.printStackTrace();
        }
    }

    //LADEN-----------------------------------------------------
    private String load() {

        StringBuilder sb = new StringBuilder();

        // statt einem InputStream(FILENAME) verwenden wir für Datein einen openFileInput(FILENAME)
        try(BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)))){

            String line;

            while(( line = br.readLine()) != null ){

                //Zeilenumbruch
                if( sb.length() > 0){
                    sb.append('\n');
                }
                //hinzufügen der Zeile/des Inhalts
                sb.append(line);
            }

        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException -> Datei nicht gefunden! ", e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException -> ERROR ist aufgetreten! ", e);
            e.printStackTrace();
        }

        return sb.toString();
    }
}
