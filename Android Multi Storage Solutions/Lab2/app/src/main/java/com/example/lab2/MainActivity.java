package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText phoneEditText,messageEditText;
    Button nextBtn,closeBtn;
    public static final String Message = "message";
    public static final String Phone = "phone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleActions();

    }

    void init(){

        phoneEditText = findViewById(R.id.phoneEditText);
        messageEditText = findViewById(R.id.messageEditText);
        nextBtn = findViewById(R.id.nextBtn);
        closeBtn = findViewById(R.id.closeBtn);
    }

    void handleActions(){
        nextBtn.setOnClickListener((view)->{
            if (messageEditText.getText().toString().isEmpty() || phoneEditText.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this,"please enter a massage and a phone number",Toast.LENGTH_LONG).show();
            }else {
                String pattern = "(01)[0-9]{9}";

                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(phoneEditText.getText().toString());
                if (!m.matches()){
                    Toast.makeText(MainActivity.this,"not a valid phone number",Toast.LENGTH_LONG).show();
                }else

                    startActivity(new Intent(MainActivity.this, ActivityTwo.class).putExtra(Message, messageEditText.getText().toString()).putExtra(Phone, phoneEditText.getText().toString()));
            }
            });
        closeBtn.setOnClickListener((view)->{
            finish();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SQliteHelper.SQLAdapter.getInstance(getApplicationContext()).close();
    }
}
