package com.cookandroid.todayi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    EditText edtDiary;
    Button btnSave, btnCancel;
    String fileName, bodyDiary;
    TextView tvFileName;
    String photoPath, photoName;
    File galDir;
    File albumFile = null;
    Intent galIntent;
    ImageView photos;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.itemAddGallery:
                galIntent = new Intent(Intent.ACTION_PICK);
                galIntent.setType("image/*");
                galIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(Intent.createChooser(galIntent, "Add Photo"), 2);
                return true;
        }
        return false;
    }

    private File createPhoto() throws IOException {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        photoName = name + ".jpg";
        galDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/todayI/" + photoName);
        photoPath = galDir.getAbsolutePath();
        return galDir;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            albumFile = createPhoto();
            Toast.makeText(this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/todayI/" + photoName, Toast.LENGTH_SHORT).show();
            if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                int minHeight = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap minimized = Bitmap.createScaledBitmap(bitmap, 1024, minHeight, true);
                photos.setVisibility(View.VISIBLE);
                photos.setImageBitmap(minimized);
            } else if (albumFile != null) {
                Uri.fromFile(albumFile);
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Can't Load the photo ;(", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        setTitle("Write");

        Intent intent = getIntent();
        fileName = ((Intent) intent).getStringExtra("fileName");
        bodyDiary = ((Intent) intent).getStringExtra("bodyDiary");

        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvFileName = (TextView) findViewById(R.id.tvFileName);
        photos = (ImageView) findViewById(R.id.photos);

        String str = readDiary(fileName);
        tvFileName.setText(fileName);
        edtDiary.setText(str);
        btnSave.setEnabled(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (createPhoto().exists()) {
                        FileOutputStream outFs = openFileOutput("IMG_" + fileName, Context.MODE_PRIVATE);
                        String str = photoName;
                        outFs.write(str.getBytes());
                        outFs.close();
                    }
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = edtDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(),
                            fileName + "- Diary saved", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (IOException e) {
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            byte[] txt = new byte[500];
            inFs = openFileInput(fName);
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnSave.setText("Edit");
        } catch (
                IOException e) {
            edtDiary.setHint("Write here...");
            btnSave.setText("Add Diary");
        }
        return diaryStr;
    }
}

