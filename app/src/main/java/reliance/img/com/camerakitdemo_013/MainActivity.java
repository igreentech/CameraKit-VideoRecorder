package reliance.img.com.camerakitdemo_013;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CameraView cameraView;

    private Button btnStartRec, btnStopRec;

    private String recordingVideoFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraView = (CameraView) findViewById(R.id.camera);
        cameraView.setVideoBitRate(480);
        cameraView.setVideoQuality(CameraKit.Constants.VIDEO_QUALITY_480P);

        btnStartRec = findViewById(R.id.btn_start_rec);
        btnStartRec.setOnClickListener(videoOnClickListener);

        btnStopRec = findViewById(R.id.btn_stop_rec);
        btnStopRec.setOnClickListener(stopRecOnClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    private View.OnClickListener videoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            btnStartRec.setVisibility(View.GONE);
            btnStopRec.setVisibility(View.VISIBLE);
            cameraView.captureVideo(getRecordingVideoFilePath(), new CameraKitEventCallback<CameraKitVideo>() {
                @Override
                public void callback(CameraKitVideo cameraKitVideo) {

                    Log.e("Rec Video Path",cameraKitVideo.getVideoFile().getAbsolutePath());
                }
            });

        }
    };

    private View.OnClickListener stopRecOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cameraView.stopVideo();
            btnStartRec.setVisibility(View.VISIBLE);
            btnStopRec.setVisibility(View.GONE);
        }
    };

    public File getRecordingVideoFilePath() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/CameraKitDemo0.13", "/" + "Full");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("createFullFolder", "failed to create the directory "+mediaStorageDir);
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HHmmss", Locale.getDefault()).format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "_" + timeStamp + "_Full" + ".mp4");
    }


}
