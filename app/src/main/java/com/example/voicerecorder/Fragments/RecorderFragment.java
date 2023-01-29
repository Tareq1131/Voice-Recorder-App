package com.example.voicerecorder.Fragments;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.voicerecorder.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class RecorderFragment extends Fragment {

    View view;
    ImageButton btnRec;
    TextView textRecStatus;
    Chronometer timeRec;
    GifImageView gifView;

    private static String fileName;
    private MediaRecorder recorder;
    boolean isRecording;

    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/VRecorder");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_recoder,container,false);

       btnRec = view.findViewById(R.id.btnRec);
       textRecStatus = view.findViewById(R.id.textRecStatus);
       timeRec = view.findViewById(R.id.timeRec);
       gifView = view.findViewById(R.id.gifView);

       isRecording = false;

       askruntimePermission();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String date = format.format(new Date());

        fileName = path + "/recording " + date + ".amr";
        if (!path.exists()){
            path.mkdirs();
        }

        btnRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording){
                    try {
                        startRecording();
                        gifView.setVisibility(View.VISIBLE);
                        timeRec.setBase(SystemClock.elapsedRealtime());
                        timeRec.start();
                        textRecStatus.setText("Recording....");
                        btnRec.setImageResource(R.drawable.ic_stop);
                        isRecording = true;
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Couldn't Record...", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (isRecording){
                    stopRecording();
                    gifView.setVisibility(View.GONE);
                    timeRec.setBase(SystemClock.elapsedRealtime());
                    timeRec.stop();
                    textRecStatus.setText("");
                    btnRec.setImageResource(R.drawable.ic_record);
                    isRecording = false;
                }
            }
        });
       return view;
    }
    private void startRecording(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recorder.start();
    }

    private void stopRecording(){
            recorder.stop();
            recorder.release();
            recorder = null;
    }

    private void askruntimePermission() {
       Dexter.withContext(getContext()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
               Manifest.permission.WRITE_EXTERNAL_STORAGE,
               Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {
           @Override
           public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

               Toast.makeText(getContext(), "Granted!!!", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

               permissionToken.continuePermissionRequest();
           }
       }).check();
    }
}
