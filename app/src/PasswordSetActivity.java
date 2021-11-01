package com.cookandroid.todayi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PasswordSetActivity extends AppCompatActivity {

    TextView tvPassword;
    String passCheck, getPass;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton, setButton, deleteButton;
    int thou, hund, tenn, onee, result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordset);
        setTitle("Password");

        oneButton = (Button) findViewById(R.id.oneButton);
        twoButton = (Button) findViewById(R.id.twoButton);
        threeButton = (Button) findViewById(R.id.threeButton);
        fourButton = (Button) findViewById(R.id.fourButton);
        fiveButton = (Button) findViewById(R.id.fiveButton);
        sixButton = (Button) findViewById(R.id.sixButton);
        sevenButton = (Button) findViewById(R.id.sevenButton);
        eightButton = (Button) findViewById(R.id.eightButton);
        nineButton = (Button) findViewById(R.id.nineButton);
        zeroButton = (Button) findViewById(R.id.zeroButton);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        setButton = (Button) findViewById(R.id.setButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPassword.getText().equals("* * * *") && getPass != null) {
                    setPassword(getPass);
                    gotoMain();
                } else reset();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        buttonSet(oneButton);
        buttonSet(twoButton);
        buttonSet(threeButton);
        buttonSet(fourButton);
        buttonSet(fiveButton);
        buttonSet(sixButton);
        buttonSet(sevenButton);
        buttonSet(eightButton);
        buttonSet(nineButton);
        buttonSet(zeroButton);
    }

    void buttonSet(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passCheck = btn.getText().toString().trim();
                if (tvPassword.getText().equals("_ _ _ _")) {
                    thou = Integer.parseInt(passCheck) * 1000;
                    tvPassword.setText("* _ _ _");
                } else if (tvPassword.getText().equals("* _ _ _")) {
                    hund = Integer.parseInt(passCheck) * 100;
                    tvPassword.setText("* * _ _");
                } else if (tvPassword.getText().equals("* * _ _")) {
                    tenn = Integer.parseInt(passCheck) * 10;
                    tvPassword.setText("* * * _");
                } else if (tvPassword.getText().equals("* * * _")) {
                    onee = Integer.parseInt(passCheck) * 1;
                    tvPassword.setText("* * * *");
                    result = thou + hund + tenn + onee;
                    getPass = Integer.toString(result);
                } else if (tvPassword.getText().equals("* * * *")) {
                    Toast.makeText(PasswordSetActivity.this, "네자리를 모두 입력하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void reset() {
        Intent intent = new Intent(this, PasswordSetActivity.class);
        startActivity(intent);
        finish();
    }

    void setPassword(String password) {
        try {
            FileOutputStream outFs = openFileOutput("password.txt", Context.MODE_PRIVATE);
            outFs.write(password.getBytes());
            outFs.close();
            Toast.makeText(getApplicationContext(),
                    "Password saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
        }
    }
}
