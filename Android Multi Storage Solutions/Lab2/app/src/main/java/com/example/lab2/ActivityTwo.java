package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ActivityTwo extends AppCompatActivity {
    TextView phoneEditText, messageEditText;
    Button closeBtn, writeSH, readSh, writeIS, readIS, readSQL, writeSQL;
    final String userDataStr = "userData";
    final String msgKey = "msg";
    final String phoneKey = "phone";
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        init();
        handleAction();


    }

    void init() {
        sharedPreferences = getSharedPreferences(userDataStr, getApplicationContext().MODE_PRIVATE);
        editor = sharedPreferences.edit();
        phoneEditText = findViewById(R.id.phoneEditText);
        messageEditText = findViewById(R.id.messageEditText);
        closeBtn = findViewById(R.id.closeBtn);
        readSh = findViewById(R.id.readSh);
        writeSH = findViewById(R.id.writeSH);
        writeIS = findViewById(R.id.writeIS);
        readIS = findViewById(R.id.readIS);
        readSQL = findViewById(R.id.readSQL);
        writeSQL = findViewById(R.id.writeSQL);
        intent = getIntent();
        if (intent != null) {

            phoneEditText.setText(intent.getStringExtra(MainActivity.Phone));
            messageEditText.setText(intent.getStringExtra(MainActivity.Message));
        }
    }
    void clearText(){
        phoneEditText.setText("");
        messageEditText.setText("");
    }
    void handleAction(){
        closeBtn.setOnClickListener((view)->{
            onBackPressed();
        });
        writeSH.setOnClickListener((view)->{
            editor.putString(msgKey, intent.getStringExtra(MainActivity.Message));
            editor.putString(phoneKey, intent.getStringExtra(MainActivity.Phone));
            editor.commit();
            clearText();
        });
        readSh.setOnClickListener((view)->{
            String msg = sharedPreferences.getString(msgKey,"");
            String phone = sharedPreferences.getString(phoneKey,"");
            phoneEditText.setText(phone);
            messageEditText.setText(msg);
        });
        writeIS.setOnClickListener(((view) -> {
            try {
                FileOutputStream fileOutputStream = openFileOutput("_msg",getApplicationContext().MODE_PRIVATE);
                fileOutputStream.write(String.valueOf(intent.getStringExtra(MainActivity.Message)).getBytes());
                FileOutputStream fileOutputStream2 = openFileOutput("_phone",getApplicationContext().MODE_PRIVATE);
                fileOutputStream2.write(String.valueOf(intent.getStringExtra(MainActivity.Phone)).getBytes());
                fileOutputStream.flush();
                fileOutputStream2.flush();
                fileOutputStream.close();
                fileOutputStream2.close();
                clearText();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
        readIS.setOnClickListener((view)->{
            try {
                FileInputStream fileInputStream = openFileInput("_msg");

                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
                StringBuilder msg = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    msg.append(line);
                }
                FileInputStream fileInputStreamPhone = openFileInput("_phone");
                BufferedReader readerPhoen = new BufferedReader(new InputStreamReader(fileInputStreamPhone));
                StringBuilder phone = new StringBuilder();
                String line2;
                while ((line2 = readerPhoen.readLine()) != null) {
                    phone.append(line2);
                }
                phoneEditText.setText(String.valueOf(phone));
                messageEditText.setText(String.valueOf(msg));
                fileInputStream.close();
                fileInputStreamPhone.close();
                reader.close();
                readerPhoen.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        writeSQL.setOnClickListener((view)->{
            ContactData contactData = new ContactData(String.valueOf(intent.getStringExtra(MainActivity.Message)),String.valueOf(intent.getStringExtra(MainActivity.Phone)));
            if (!SQliteHelper.SQLAdapter.getInstance(getApplicationContext()).insertIntoContacts(contactData))
            Toast.makeText(ActivityTwo.this, "duplicate phone number",Toast.LENGTH_SHORT).show();
            clearText();

        });
        readSQL.setOnClickListener((view)->{
            ContactData contactData = SQliteHelper.SQLAdapter.getInstance(getApplicationContext()).getContactDataByPhoneNumber(String.valueOf(intent.getStringExtra(MainActivity.Phone)));
            phoneEditText.setText(contactData.getPhone());
            messageEditText.setText(contactData.getMsg());
        });


    }


}
