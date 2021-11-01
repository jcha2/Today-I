package com.cookandroid.todayi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker dp;
    TextView tvDiary;
    Button btnWrite;
    String fileName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        Intent intent;
        switch (item.getItemId()) {
            case R.id.itemShowAll:
                String str = tvDiary.getText().toString();
                intent = new Intent(getApplicationContext(), ShowAllActivity.class);
                intent.putExtra("fileName", fileName);
                intent.putExtra("bodyDiary", str);
                startActivity(intent);
                return true;
            case R.id.itemSetPassword:
                intent = new Intent(getApplicationContext(), PasswordSetActivity.class);
                startActivity(intent);
                return true;
            case R.id.itemAbout:
                intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Today, I");

        dp = (DatePicker) findViewById(R.id.datePicker1);
        tvDiary = (TextView) findViewById(R.id.tvDiary);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);


        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName = Integer.toString(year) + "_" + Integer.toString(monthOfYear + 1) + "_" + Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);
                tvDiary.setText(str);
                btnWrite.setEnabled(true);
            }
        });
        btnWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str = tvDiary.getText().toString();
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra("fileName", fileName);
                intent.putExtra("bodyDiary", str);
                startActivity(intent);
            }
        });
    }


    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnWrite.setText("Edit");
            btnWrite.setEnabled(true);
        } catch (IOException e) {
            btnWrite.setText("Add Diary");
            btnWrite.setEnabled(true);
        }
        return diaryStr;
    }
}

