package org.tensorflow.lite.examples.detection;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector.Detections;
import com.google.android.gms.vision.Detector.Processor;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.gms.vision.text.TextRecognizer.Builder;
import java.io.IOException;
import java.util.Timer;

public class Read_Text extends AppCompatActivity implements OnInitListener {
    final int RequestCameraPermissionID = PointerIconCompat.TYPE_CONTEXT_MENU;
    CameraSource cameraSource;
    SurfaceView cameraView;
    private TextToSpeech repeatTTS;
    TextView textView;
    final Timer timer = new Timer();

    public void onInit(int i) {
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == PointerIconCompat.TYPE_CONTEXT_MENU && iArr[0] == 0 && ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0) {
            try {
                this.cameraSource.start(this.cameraView.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_read__text);

        this.cameraView = (SurfaceView) findViewById(R.id.surface_View);
        this.textView = (TextView) findViewById(R.id.text_view);
        this.repeatTTS = new TextToSpeech(this, this);
        TextRecognizer build = new Builder(getApplicationContext()).build();
        if (build.isOperational()) {
            this.cameraSource = new CameraSource.Builder(getApplicationContext(), build).setFacing(0).setRequestedPreviewSize(1280, 1024).setRequestedFps(2.0f).setAutoFocusEnabled(true).build();
            this.cameraView.getHolder().addCallback(new Callback() {
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                }

                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ContextCompat.checkSelfPermission(Read_Text.this.getApplicationContext(), "android.permission.CAMERA") != 0) {
                            ActivityCompat.requestPermissions(Read_Text.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PointerIconCompat.TYPE_CONTEXT_MENU);
                            return;
                        }
                        Read_Text.this.cameraSource.start(Read_Text.this.cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    Read_Text.this.cameraSource.stop();
                }
            });
            build.setProcessor(new Processor<TextBlock>() {
                public void release() {
                }

                public void receiveDetections(Detections<TextBlock> detections) {
                    final SparseArray detectedItems = detections.getDetectedItems();
                    if (detectedItems.size() != 0) {
                        Read_Text.this.textView.post(new Runnable() {
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < detectedItems.size(); i++) {
                                    stringBuilder.append(((TextBlock) detectedItems.valueAt(i)).getValue());
                                    stringBuilder.append("\n");
                                }
                                Read_Text.this.textView.setText(stringBuilder.toString());
                                Read_Text.this.repeatTTS.speak(stringBuilder.toString(), 0, null);
                                Read_Text.this.repeatTTS.setSpeechRate(1.0f);
                            }
                        });
                    }
                }
            });
            return;
        }
        Log.w("Read_Text", "Detector dependencies are not available");
    }
}