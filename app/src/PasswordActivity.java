package com.cookandroid.todayi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class PasswordActivity extends AppCompatActivity {

    TextView tvPassword;
    String passCheck, getPass;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton;
    int thou, hund, tenn, onee, result, getPassInt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);
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
                    result = thou + hund + tenn + onee;
                    getPass = getPassword("password.txt");
                    getPassInt = Integer.parseInt(getPass);
                    if (result == getPassInt) gotoMain();
                    else reset();
                } else reset();
            }
        });

    }

    void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    void reset() {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
        finish();
    }

    String getPassword(String fName) {
        String pass = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            pass = (new String(txt)).trim();
        } catch (IOException e) {
        }
        return pass;
    }
}
