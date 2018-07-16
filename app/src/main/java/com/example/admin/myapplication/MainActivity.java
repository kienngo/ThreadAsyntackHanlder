package com.example.admin.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final String mError = "error";

    private final int PICK_FROM_GALLERY = 1;
    private final int SPAN_COUNT_GRIDLAYOUT = 2;
    private final int INIT_INDEX = 0;

    private GalleryAdapter mGalleryAdapter;
    private RecyclerView mRecyclerGallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inItData();
        inItView();
    }

    private void inItData() {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                new MyAsyntask().execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inItView() {
        mRecyclerGallery = (RecyclerView) findViewById(R.id.recycler_gallery);
    }

    class MyAsyntask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return getListImage();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            mGalleryAdapter = new GalleryAdapter(strings, MainActivity.this);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, SPAN_COUNT_GRIDLAYOUT);
            mRecyclerGallery.setLayoutManager(layoutManager);
            mRecyclerGallery.setAdapter(mGalleryAdapter);
        }
    }

    private ArrayList<String> getListImage() {
        ArrayList<String> listOfAllImages = new ArrayList<String>();

        int columnIndexData;
        String absolutePathOfImage = null;

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, null);

        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                if (grantResults.length > INIT_INDEX && grantResults[INIT_INDEX] == PackageManager.PERMISSION_GRANTED) {
                    new MyAsyntask().execute();
                } else {
                    Log.e(TAG, mError);
                }
                break;
        }
    }
}
