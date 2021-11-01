package com.cookandroid.todayi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkFile() == true) {
            Intent intent = new Intent(this, PasswordActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    boolean checkFile() {
        File file = new File(getFilesDir().getPath());
        File[] files = file.listFiles();
        int i = 0;
        final ArrayList<String> names = new ArrayList<String>();

        try {

            while (files[i].getName().trim().contains(".txt")) {
                if (files[i].getName().trim().contains("password"))
                    return true;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}