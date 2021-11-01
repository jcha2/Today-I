package com.cookandroid.todayi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ShowAllActivity extends AppCompatActivity {
    Button btnBack;
    EditText edtDiary;
    TextView tvCount;
    File file;
    File[] files;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showall);
        setTitle("My Diaries");
        showList();
    }

    void showList() {
        tvCount = (TextView) findViewById(R.id.tvCount);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        file = new File(getFilesDir().getPath());
        files = file.listFiles();

        int i = 0;
        final ArrayList<String> names = new ArrayList<String>();

        try {
            while (files[i].exists()) {
                names.add(i, files[i].getName());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvCount.setText("Diaries count: " + i);
        ListView list = (ListView) findViewById(R.id.listView1);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, names);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowAllActivity.this, WriteActivity.class);

                Object obj = parent.getAdapter().getItem(position);
                intent.putExtra("fileName", (String) obj);
                intent.putExtra("bodyDiary", readDiary((String) obj));
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ShowAllActivity.this);
                dlg.setMessage("Delete?");
                dlg.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String path = getFilesDir().getPath();
                        String item = names.get(position);
                        deleteDiary(path + "/" + item);
                        showList();
                    }
                });
                dlg.setNegativeButton("Back", null).show();
                adapter.notifyDataSetChanged();

                return false;
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
        } catch (IOException e) {
        }
        return diaryStr;
    }

    void deleteDiary(String path) {
        File file = new File(path);
        file.delete();
    }
}