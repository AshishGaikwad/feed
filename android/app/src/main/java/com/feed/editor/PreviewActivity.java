package com.feed.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.feed.R;
import com.feed.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import VideoHandle.EpEditor;
import VideoHandle.OnEditorListener;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        String videoPath = getIntent().getStringExtra("videoPath");
        File f = new File(getExternalFilesDir(null), videoPath);
        File audio = getAssets("MeetSong.mp3",f.getAbsolutePath().replaceAll("mp4","mp3"));
//        EpEditor.music(f.getAbsolutePath(), audio.getAbsolutePath(), (f.getAbsolutePath().replaceAll("build","build_aud")), 0, 1, new OnEditorListener() {
//            @Override
//            public void onSuccess() {
//                System.out.println("Music merged successfully");
//            }
//
//            @Override
//            public void onFailure() {
//                System.out.println("Music merged failed");
//            }
//
//            @Override
//            public void onProgress(float progress) {
//                System.out.println("Music merged progress");
//            }
//        });

        EpEditor.execCmd("", 0, new OnEditorListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onProgress(float progress) {

            }
        });



        VideoView simpleVideoView = findViewById(R.id.videoView);
        simpleVideoView.setVideoPath(f.getAbsolutePath());
        MediaController mediaController = new MediaController(this);
        simpleVideoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(simpleVideoView);
        simpleVideoView.setVisibility(View.VISIBLE);
        simpleVideoView.start();
    }


    public File getAssets(String assetName,String path)  {
        InputStream in = null;
        try {
            in = getResources().getAssets().open(assetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(path));
            final byte[] b = new byte[8192];
            for (int r; (r = in.read(b)) != -1;) {
                out.write(b, 0, r);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(path);
    }
}