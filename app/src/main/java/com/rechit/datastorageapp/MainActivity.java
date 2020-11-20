package com.rechit.datastorageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100; // gunakan untuk eksternal storage

    public static final String FILENAME = "namafile.txt";
    //public static final String PREFNAME = "com.rech.datastorageapp.PREF";
    private EditText editText;
    private TextView textView;

    // fungsii hasPermission untuk eksternal storage
    private static boolean hasPermission(Context context, String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && permissions!=null){
            for(String permission:permissions){
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    // gunakan ini untuk eksternal storage
    private static final String[] PERMISSIONS={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_baca);
        editText = findViewById(R.id.edit_text);
    }



// jika ingin memakai shared preference ganti fungsi simpanFile dkk jadi SP
// jika ingin memakai internal storage ganti fungsi jadi IS
// jika ingin memakai external storage ganti fungsi jadi ES
    public void simpan(View view) {
        simpanFileES();
    }

    public void hapus(View view) {
        hapusFileES();
    }

    public void baca(View view) {
        bacaFileES();
    }

//    public void simpanFileSP(){
//        String isiFile = editText.getText().toString();
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFNAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(FILENAME, isiFile);
//        editor.commit();
//    }
//    public void bacaFileSP(){
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFNAME, MODE_PRIVATE);
//        if(sharedPreferences.contains(FILENAME)){
//            String mytext = sharedPreferences.getString(FILENAME, "");
//            textBaca.setText(mytext);
//        } else{
//            textBaca.setText("");
//        }
//    }
//
//    public void hapusFileSP(){
//        SharedPreferences sharedPreferences = getSharedPreferences(PREFNAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();
//    }


// internal storage, uncomment kode dibawah dan comment kode sejenis lain untuk menjalankan internal storage app
/*
    public void simpanFileIS(){
        String content = editText.getText().toString();
        File path = getDir("NEWFOLDER", MODE_PRIVATE); // Buat direktori baru
        File file = new File(path.toString(), FILENAME);

        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file, false);

             //Append false -> timpa file yang ada
             //Append true -> isi baru digabung dengan yang sudah ada
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bacaFileIS(){
        File path = getDir("NEWFOLDER", MODE_PRIVATE); // Buat direktori baru
        File file = new File(path.toString(), FILENAME);

        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null){
                    text.append(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textView.setText(text.toString());
        } else {
            textView.setText("");
        }
    }

    public void hapusFileIS(){
        File path = getDir("NEWFOLDER", MODE_PRIVATE); // Buat direktori baru
        File file = new File(path.toString(), FILENAME);

        if (file.exists()) file.delete();
    }
*/


//
//    //  deklarasi variabel
//



    // fungsi lain

    public void simpanFileES(){
        if(hasPermission(this, PERMISSIONS)){
            String content = editText.getText().toString();
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path.toString(), FILENAME);

            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);

                //Append false -> timpa file yang ada
                //Append true -> isi baru digabung dengan yang sudah ada
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else{
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);
        }

    }

    public void hapusFileES() {
        File path = Environment.getExternalStorageDirectory(); // ganti path Environment.getExternalStorageDirectory(); untuk eksternal storage
        File file = new File(path.toString(), FILENAME);

        if (file.exists()) file.delete();
    }

    public void bacaFileES(){
        File path = Environment.getExternalStorageDirectory(); // Buat direktori baru
        File file = new File(path.toString(), FILENAME);

        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null){
                    text.append(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textView.setText(text.toString());
        } else {
            textView.setText("");
        }
    }

}