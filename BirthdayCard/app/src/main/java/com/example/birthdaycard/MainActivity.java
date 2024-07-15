package com.example.birthdaycard;

import android.os.Bundle;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.MediaController;
import android.widget.VideoView;
import android.net.Uri;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void playMusic(View view){
        MediaPlayer ring = MediaPlayer.create(MainActivity.this,R.raw.happybirthdayjazz);
        ring.start();
    }

    public void playVideo(View view){
        VideoView v=findViewById(R.id.videoView1);
        v.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.happybirthdayvideo);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(v);
        mediaController.setMediaPlayer(v);
        v.setMediaController(mediaController);
        v.start();
    }

    public void playInternetVideo(View view){
        VideoView v=findViewById(R.id.videoView1);
        v.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.happybirthdayvideo);
        String videoURL = "https://www.youtube.com/watch?v=8xy8qIjdC3g";
        Uri uri = Uri.parse(videoURL);
        v.setVideoURI(uri);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(v);
        mediaController.setMediaPlayer(v);
        v.setMediaController(mediaController);
        v.start();
    }
}