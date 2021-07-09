package com.example.stopthecrime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static com.example.stopthecrime.R.id.CustomMessageTextBox;


public class CustomMessageActivity<TextInputEditText> extends AppCompatActivity {

    EditText EditCustomMessageBox;
    Button SaveDefaultMessage;
    Button SaveCustomMessage;
    Button PreviewCustomMessage;
    String customMessage;
    String file = "customMessageFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_message);

        EditCustomMessageBox = findViewById(CustomMessageTextBox);
        SaveCustomMessage = findViewById(R.id.SaveCustomMessage);
        SaveDefaultMessage = findViewById(R.id.SaveDefaultMessage);
        PreviewCustomMessage = findViewById(R.id.PreviewCustomMessage);

        SaveCustomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCustomMessage();
            }
        });

        SaveDefaultMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDefaultMessage();
            }
        });

        PreviewCustomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewCustomMessage();
            }
        });

        Button BackButton = findViewById(R.id.backButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(20);
                finish();
            }
        });

    }

    private void saveCustomMessage(){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(20);

        customMessage = EditCustomMessageBox.getText().toString();
        if(customMessage.trim().isEmpty()){
            Toast.makeText(this, "No Message to Save!", Toast.LENGTH_SHORT).show();
        }else{
            try{
                FileOutputStream fout = openFileOutput(file,MODE_PRIVATE);
                fout.write(customMessage.getBytes());
                fout.close();
                File fileDir = new File(getFilesDir(),file);
                //Toast.makeText(CustomMessageActivity.this, "Custom Message Stored in path: "+fileDir, Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomMessageActivity.this, "Custom Message Stored.", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(CustomMessageActivity.this, "ERROR Saving Custom Message", Toast.LENGTH_SHORT).show();
                vibe.vibrate(50);
                e.printStackTrace();
            }
        }

        EditCustomMessageBox.setText("");
    }

    private void saveDefaultMessage(){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(20);

        String defaultHelpMessageString = getResources().getString(R.string.defaultHelpMessageString);
        try{
            FileOutputStream fout = openFileOutput(file,MODE_PRIVATE);
            fout.write(defaultHelpMessageString.getBytes());
            fout.close();
            File fileDir = new File(getFilesDir(),file);
            //Toast.makeText(CustomMessageActivity.this, "Default Message Stored in path: "+fileDir, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Default Message Saved.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(CustomMessageActivity.this, "ERROR Saving Default Message", Toast.LENGTH_SHORT).show();
            vibe.vibrate(50);
            e.printStackTrace();
        }
        EditCustomMessageBox.setText("");
    }

    private void previewCustomMessage(){
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(20);
        try{
            FileInputStream fin = openFileInput(file);
            int c;
            String tempMessage = "";
            while((c=fin.read())!=-1){
                tempMessage += Character.toString((char)c);
            }
            EditCustomMessageBox.setText(tempMessage);
        }catch (Exception e){
            Toast.makeText(CustomMessageActivity.this, "Error Loading Saved Message", Toast.LENGTH_SHORT).show();
            vibe.vibrate(50);
            e.printStackTrace();
        }
    }

}

