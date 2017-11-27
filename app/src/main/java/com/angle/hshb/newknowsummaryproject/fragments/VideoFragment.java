package com.angle.hshb.newknowsummaryproject.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.angle.hshb.newknowsummaryproject.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 多媒体
 */
public class VideoFragment extends Fragment {
    public static final String TAG = "VideoFragment";
    @BindView(R.id.video_view)
    VideoView videoView;
    Unbinder unbinder;
    @BindView(R.id.tv_swith)
    TextView tvSwith;
    @BindView(R.id.btn_stop)
    Button btnStop;

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private static final int REQUEST_CODE = 3;
    private int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission
                .WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            initMediaPlayer();
            initVideoPath();
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    initMediaPlayer();
                    initVideoPath();
                } else {
                    Toast.makeText(getContext(), "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 初始化VideoView
     */
    private void initVideoPath() {
//        File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        Uri uri = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        videoView.setMediaController(new MediaController(getContext()));
        videoView.setVideoURI(uri);
    }

    /**
     * 初化Mediaplayer
     */
    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            mediaPlayer.setDataSource(file.getPath());
            Log.i(TAG, file.getPath());
            mediaPlayer.prepare();//初始化MediaPlayer
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_stop, R.id.tv_swith})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                play();
                break;
            case R.id.btn_pause:
                pause();
                break;
            case R.id.btn_stop:
                stop();
                break;
            case R.id.tv_swith:
                tvSwith.setText(i % 2 == 0 ? "视频" : "音频");
                tvSwith.setTextColor(i % 2 == 0 ? Color.WHITE : Color.BLACK);
                videoView.setVisibility(i % 2 == 0 ? View.VISIBLE : View.GONE);
                btnStop.setText(TextUtils.equals("音频", tvSwith.getText().toString()) ? "STOP" :
                        "REPLAY");
                i++;
                break;
        }
    }

    /**
     * 开始播放
     */
    private void play() {
        if (TextUtils.equals("音频", tvSwith.getText().toString())) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } else {
            if (!videoView.isPlaying()) {
//                videoView.start();
                videoView.requestFocus();
            }
        }
    }

    private void stop() {
        try {
            if (TextUtils.equals("音频", tvSwith.getText().toString())) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();//停止播放
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(0);
                }
            } else {
                if (videoView.isPlaying()) {
                    videoView.resume();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pause() {
        if (TextUtils.equals("音频", tvSwith.getText().toString())) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();//暂停播放
            }
        } else {
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (videoView != null){
            videoView.suspend();
        }
    }

}
