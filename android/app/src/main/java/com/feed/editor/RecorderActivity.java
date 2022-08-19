//package com.feed.editor;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ImageAnalysis;
//import androidx.camera.core.ImageProxy;
//import androidx.camera.core.Preview;
//import androidx.camera.lifecycle.ProcessCameraProvider;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.lifecycle.LifecycleOwner;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.res.AssetFileDescriptor;
//import android.media.MediaPlayer;
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.media.Image;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.DisplayMetrics;
//import android.util.Size;
//import android.view.LayoutInflater;
//import android.view.Surface;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RadioButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.auth.CognitoCachingCredentialsProvider;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.amazonaws.util.Base64;
//import com.facebook.react.bridge.Arguments;
//import com.facebook.react.bridge.WritableMap;
//import com.facebook.react.modules.core.DeviceEventManagerModule;
//import com.feed.FilterSheetFragment;
//import com.feed.MainActivity;
//import com.feed.MainApplication;
//import com.feed.R;
//import com.feed.entity.FilterEntityParser;
//import com.feed.util.Constants;
//import com.feed.util.Filters;
//import com.feed.util.SampleSong;
//import com.feed.view.ProgressBarListener;
//import com.feed.view.RecorderView;
//import com.feed.view.SegmentedProgressBar;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.common.util.concurrent.ListenableFuture;
//
//
//import org.apache.commons.io.FileUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
////import VideoHandle.EpEditor;
////import VideoHandle.EpVideo;
////import VideoHandle.OnEditorListener;
//import ai.deepar.ar.ARErrorType;
//import ai.deepar.ar.AREventListener;
//import ai.deepar.ar.CameraResolutionPreset;
//import ai.deepar.ar.DeepAR;
//import ai.deepar.ar.DeepARImageFormat;
//import ai.deepar.deepar_example.ARSurfaceProvider;
//
//public class RecorderActivity extends AppCompatActivity implements SurfaceHolder.Callback, AREventListener {
//
//    private RecorderActivity me = this;
//    FilterSheetFragment filterSheetFragment ;
//    // Default camera lens value, change to CameraSelector.LENS_FACING_BACK to initialize with back camera
//    private int defaultLensFacing = CameraSelector.LENS_FACING_FRONT;
//    private ARSurfaceProvider surfaceProvider = null;
//    private int lensFacing = defaultLensFacing;
//    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
//    private ByteBuffer[] buffers;
//    private int currentBuffer = 0;
//    private static final int NUMBER_OF_BUFFERS=2;
//    private static final boolean useExternalCameraTexture = true;
//
//    private DeepAR deepAR;
//    private TextView currentFilterName;
//
//    private int currentMask=0;
//    private int currentEffect=0;
//    private int currentFilter=0;
//
//    private int screenOrientation;
//
//    ArrayList<String> masks;
//    ArrayList<String> effects;
//    ArrayList<String> filters;
//
//    private int activeFilterType = 0;
//    private boolean recording = false;
//    private boolean currentSwitchRecording = false;
//
//    private int width = 0;
//    private int height = 0;
//
//    private File videoFileName;
//    private RecorderView recorder;
//    private SegmentedProgressBar video_progress;
//    private long recordTimeInMillis = 30*1000;
//    int sec_passed = 0;
//    long time_in_milis = 0;
//    private String currentSession = ""+System.currentTimeMillis();
//    private List<File> clipList = new ArrayList<>();
//    private ImageView OpenBottomSheet ;
//    private ImageView musicButton ;
//    MediaPlayer music;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recorder);
//        currentSession = ""+System.currentTimeMillis();
//        clipList = new ArrayList<>();
//        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, Filters.getAllFilters());
//        File sessionDraftPath = new File(getExternalFilesDir(null), Constants.DRAFT_FOLDER+Constants.PATH_SEPARATOR+currentSession);
//        if(!sessionDraftPath.exists()) {
//            sessionDraftPath.mkdirs();
//        }
//
//        Bundle extras = getIntent().getExtras();
//        String musicData;
//
//        if (extras != null) {
//            musicData = extras.getString("MusicData");
//            System.out.println("MusicData============="+musicData);
//
//            if(musicData != null && !musicData.equalsIgnoreCase("")) {
//                String mdirPath = me.getExternalFilesDir(null).getAbsolutePath();
//                String mfilePath = mdirPath + Constants.PATH_SEPARATOR + Constants.DRAFT_FOLDER + Constants.PATH_SEPARATOR +currentSession+ Constants.PATH_SEPARATOR + "current_audio.mp3";
//                File musicPath = new File(mfilePath);
//
//                byte[] b = Base64.decode(musicData);
//                try {
//                    FileUtils.copyInputStreamToFile(new ByteArrayInputStream(b), musicPath);
//                    music = new MediaPlayer();
//                    try {
//                        music.setDataSource(mfilePath);
//                        music.prepare();
//                        System.out.println("music.getDuration()============="+music.getDuration());
//                        recordTimeInMillis = music.getDuration();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    music.setVolume(1f, 1f);
//                    music.setLooping(false);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        currentFilterName = findViewById(R.id.current_filter_name);
//        recorder = findViewById(R.id.recorder);
//        OpenBottomSheet = findViewById(R.id.bottom_sheet_button);
//        musicButton = findViewById(R.id.music_button);
//        recorder.invalidate();
//        initProgressBar();
//        //AssetFileDescriptor descriptor = null;
//
//
//
//
//
//
//
//        recorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(recording) {
//                   pause();
//                } else {
//                    resume();
//                }
//                recording = !recording;
//
//
//            }
//        });
//
//        OpenBottomSheet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                filterSheetFragment = new FilterSheetFragment(me);
//                filterSheetFragment.show(getSupportFragmentManager(),filterSheetFragment.getTag());
//            }
//        });
//
//        musicButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("Caliing react native function1");
//                try{
//                    System.out.println("Caliing react native function2");
//                    WritableMap map = Arguments.createMap();
//                    map.putString("test","test");
//                    MainApplication
//                            .getInstance()
//                            .getReactNativeHost()
//                            .getReactInstanceManager()
//                            .getCurrentReactContext()
//                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                            .emit("CallMusicPicker",map);
//
//
//                    Intent intent = new Intent(RecorderActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    System.out.println("Caliing react native function3");
//                }catch (Exception e){
//                    e.printStackTrace();
//                    System.out.println("Caliing react native function error");
//                }
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//             ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO },
//                    1);
//        } else {
//            initialize();
//        }
//        super.onStart();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1 && grantResults.length > 0) {
//            for (int grantResult : grantResults) {
//                if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                    return; // no permission
//                }
//            }
//            initialize();
//        }
//    }
//
//    private void initialize() {
//        initializeDeepAR();
////        initializeFilters();
//        initalizeViews();
//    }
//
////    private void initializeFilters() {
////        masks = new ArrayList<>();
////        masks.add("none");
////        masks.add("aviators");
////        masks.add("flower_crown");
////        masks.add("bigmouth");
////        masks.add("dalmatian");
////        masks.add("flowers");
////        masks.add("koala");
////        masks.add("lion");
////        masks.add("smallface");
////        masks.add("teddycigar");
////        masks.add("background_segmentation");
////        masks.add("tripleface");
////        masks.add("sleepingmask");
////        masks.add("fatify");
////        masks.add("obama");
////        masks.add("mudmask");
////        masks.add("pug");
////        masks.add("slash");
////        masks.add("twistedface");
////        masks.add("grumpycat");
////        masks.add("Helmet_PBR_V1");
////        masks.add("frankenstein");
////        masks.add("manly_face");
////        masks.add("plastic_ocean");
////        masks.add("pumpkin");
////        masks.add("scuba");
////
////        masks.add("tape_face");
////        masks.add("tiny_sunglasses");
////        effects = new ArrayList<>();
////        effects.add("none");
////        effects.add("fire");
////        effects.add("rain");
////        effects.add("heart");
////        effects.add("blizzard");
////        effects.add("fairy_lights");
////        effects.add("beauty");
////        filters = new ArrayList<>();
////        filters.add("none");
////        filters.add("filmcolorperfection");
////        filters.add("tv80");
////        filters.add("drawingmanga");
////        filters.add("sepia");
////        filters.add("bleachbypass");
////
////    }
//
//    private void initalizeViews() {
////        ImageButton previousMask = findViewById(R.id.previousMask);
////        ImageButton nextMask = findViewById(R.id.nextMask);
////
////        final RadioButton radioMasks = findViewById(R.id.masks);
////        final RadioButton radioEffects = findViewById(R.id.effects);
////        final RadioButton radioFilters = findViewById(R.id.filters);
//
//        SurfaceView arView = findViewById(R.id.surface);
//
//        arView.getHolder().addCallback(this);
//
//
//        // Surface might already be initialized, so we force the call to onSurfaceChanged
//        arView.setVisibility(View.GONE);
//        arView.setVisibility(View.VISIBLE);
//
//        final ImageButton screenshotBtn = findViewById(R.id.recordButton);
//        screenshotBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deepAR.takeScreenshot();
//            }
//        });
//
//        ImageButton switchCamera = findViewById(R.id.switchCamera);
//        switchCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lensFacing = lensFacing ==  CameraSelector.LENS_FACING_FRONT ?  CameraSelector.LENS_FACING_BACK :  CameraSelector.LENS_FACING_FRONT ;
//                //unbind immediately to avoid mirrored frame.
//                ProcessCameraProvider cameraProvider = null;
//                try {
//                    cameraProvider = cameraProviderFuture.get();
//                    cameraProvider.unbindAll();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                setupCamera();
//            }
//        });
//
//
////        previousMask.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                gotoPrevious();
////            }
////        });
////
////        nextMask.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                gotoNext();
////            }
////        });
////
////        radioMasks.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                radioEffects.setChecked(false);
////                radioFilters.setChecked(false);
////                activeFilterType = 0;
////            }
////        });
////        radioEffects.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                radioMasks.setChecked(false);
////                radioFilters.setChecked(false);
////                activeFilterType = 1;
////            }
////        });
////        radioFilters.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                radioEffects.setChecked(false);
////                radioMasks.setChecked(false);
////                activeFilterType = 2;
////            }
////        });
//    }
//    /*
//            get interface orientation from
//            https://stackoverflow.com/questions/10380989/how-do-i-get-the-current-orientation-activityinfo-screen-orientation-of-an-a/10383164
//         */
//    private int getScreenOrientation() {
//        int rotation = getWindowManager().getDefaultDisplay().getRotation();
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        width = dm.widthPixels;
//        height = dm.heightPixels;
//        int orientation;
//        // if the device's natural orientation is portrait:
//        if ((rotation == Surface.ROTATION_0
//                || rotation == Surface.ROTATION_180) && height > width ||
//                (rotation == Surface.ROTATION_90
//                        || rotation == Surface.ROTATION_270) && width > height) {
//            switch(rotation) {
//                case Surface.ROTATION_0:
//                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//                    break;
//                case Surface.ROTATION_90:
//                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//                    break;
//                case Surface.ROTATION_180:
//                    orientation =
//                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
//                    break;
//                case Surface.ROTATION_270:
//                    orientation =
//                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
//                    break;
//                default:
//                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//                    break;
//            }
//        }
//        // if the device's natural orientation is landscape or if the device
//        // is square:
//        else {
//            switch(rotation) {
//                case Surface.ROTATION_0:
//                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//                    break;
//                case Surface.ROTATION_90:
//                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//                    break;
//                case Surface.ROTATION_180:
//                    orientation =
//                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
//                    break;
//                case Surface.ROTATION_270:
//                    orientation =
//                            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
//                    break;
//                default:
//                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//                    break;
//            }
//        }
//
//        return orientation;
//    }
//    private void initializeDeepAR() {
//        deepAR = new DeepAR(this);
//        deepAR.setLicenseKey("c214071f213981884a24eaf729ac9443a743d2fbadb72977e646db521b934033bd4581be9e6417d1");
//        deepAR.initialize(this, this);
//        setupCamera();
//    }
//
//    private void setupCamera() {
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
//        cameraProviderFuture.addListener(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
//                    bindImageAnalysis(cameraProvider);
//                } catch (ExecutionException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, ContextCompat.getMainExecutor(this));
//    }
//
//    private void bindImageAnalysis(@NonNull ProcessCameraProvider cameraProvider) {
//        CameraResolutionPreset cameraResolutionPreset = CameraResolutionPreset.P1920x1080;
//        int width;
//        int height;
//        int orientation = getScreenOrientation();
//        if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE || orientation ==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//            width = cameraResolutionPreset.getWidth();
//            height =  cameraResolutionPreset.getHeight();
//        } else {
//            width = cameraResolutionPreset.getHeight();
//            height = cameraResolutionPreset.getWidth();
//        }
//
//        Size cameraResolution = new Size(width, height);
//        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();
//
//        if(useExternalCameraTexture) {
//            Preview preview = new Preview.Builder()
//                    .setTargetResolution(cameraResolution)
//                    .build();
//
//            cameraProvider.unbindAll();
//            cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
//            if(surfaceProvider == null) {
//                surfaceProvider = new ARSurfaceProvider(this, deepAR);
//            }
//            preview.setSurfaceProvider(surfaceProvider);
//            surfaceProvider.setMirror(lensFacing == CameraSelector.LENS_FACING_FRONT);
//        } else {
//            buffers = new ByteBuffer[NUMBER_OF_BUFFERS];
//            for (int i = 0; i < NUMBER_OF_BUFFERS; i++) {
//                buffers[i] = ByteBuffer.allocateDirect(width * height * 3);
//                buffers[i].order(ByteOrder.nativeOrder());
//                buffers[i].position(0);
//            }
//            ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
//                    .setTargetResolution(cameraResolution)
//                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                    .build();
//            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), imageAnalyzer);
//            cameraProvider.unbindAll();
//            cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis);
//        }
//    }
//
//    private ImageAnalysis.Analyzer imageAnalyzer = new ImageAnalysis.Analyzer() {
//        @Override
//        public void analyze(@NonNull ImageProxy image) {
//            byte[] byteData;
//            ByteBuffer yBuffer = image.getPlanes()[0].getBuffer();
//            ByteBuffer uBuffer = image.getPlanes()[1].getBuffer();
//            ByteBuffer vBuffer = image.getPlanes()[2].getBuffer();
//
//            int ySize = yBuffer.remaining();
//            int uSize = uBuffer.remaining();
//            int vSize = vBuffer.remaining();
//
//            byteData = new byte[ySize + uSize + vSize];
//
//            //U and V are swapped
//            yBuffer.get(byteData, 0, ySize);
//            vBuffer.get(byteData, ySize, vSize);
//            uBuffer.get(byteData, ySize + vSize, uSize);
//
//            buffers[currentBuffer].put(byteData);
//            buffers[currentBuffer].position(0);
//            if (deepAR != null) {
//                deepAR.receiveFrame(buffers[currentBuffer],
//                        image.getWidth(), image.getHeight(),
//                        image.getImageInfo().getRotationDegrees(),
//                        lensFacing == CameraSelector.LENS_FACING_FRONT,
//                        DeepARImageFormat.YUV_420_888,
//                        image.getPlanes()[1].getPixelStride()
//                );
//            }
//            currentBuffer = (currentBuffer + 1) % NUMBER_OF_BUFFERS;
//            image.close();
//        }
//    };
//
//
////    private String getFilterPath(String filterName) {
////        if (filterName.equals("none")) {
////            return null;
////        }
////
////        Toast.makeText(RecorderActivity.this,""+filterName,Toast.LENGTH_SHORT).show();
////
////        return "file:///android_asset/" + filterName;
////
////
////    }
//
////    private void gotoNext() {
////        if (activeFilterType == 0) {
////            currentMask = (currentMask + 1) % masks.size();
////            deepAR.switchEffect("mask", getFilterPath(masks.get(currentMask)));
////        } else if (activeFilterType == 1) {
////            currentEffect = (currentEffect + 1) % effects.size();
////            deepAR.switchEffect("effect", getFilterPath(effects.get(currentEffect)));
////        } else if (activeFilterType == 2) {
////            currentFilter = (currentFilter + 1) % filters.size();
////            deepAR.switchEffect("filter", getFilterPath(filters.get(currentFilter)));
////        }
////    }
//
////    private void gotoPrevious() {
////        if (activeFilterType == 0) {
////            currentMask = (currentMask - 1 + masks.size()) % masks.size();
////            deepAR.switchEffect("mask", getFilterPath(masks.get(currentMask)));
////        } else if (activeFilterType == 1) {
////            currentEffect = (currentEffect - 1 + effects.size()) % effects.size();
////            deepAR.switchEffect("effect", getFilterPath(effects.get(currentEffect)));
////        } else if (activeFilterType == 2) {
////            currentFilter = (currentFilter - 1 + filters.size()) % filters.size();
////            deepAR.switchEffect("filter", getFilterPath(filters.get(currentFilter)));
////        }
////    }
//
//    @Override
//    protected void onStop() {
//        recording = false;
//        currentSwitchRecording = false;
//        ProcessCameraProvider cameraProvider = null;
//        try {
//            cameraProvider = cameraProviderFuture.get();
//            cameraProvider.unbindAll();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if(surfaceProvider != null) {
//            surfaceProvider.stop();
//            surfaceProvider = null;
//        }
//        deepAR.release();
//        deepAR = null;
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(surfaceProvider != null) {
//            surfaceProvider.stop();
//        }
//        if (deepAR == null) {
//            return;
//        }
//        deepAR.setAREventListener(null);
//        deepAR.release();
//        deepAR = null;
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        // If we are using on screen rendering we have to set surface view where DeepAR will render
//        deepAR.setRenderSurface(holder.getSurface(), width, height);
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        if (deepAR != null) {
//            deepAR.setRenderSurface(null, 0, 0);
//        }
//    }
//
//    @Override
//    public void screenshotTaken(Bitmap bitmap) {
//
//
//    }
//
//    @Override
//    public void videoRecordingStarted() {
//
//    }
//
//    @Override
//    public void videoRecordingFinished() {
//
//    }
//
//    @Override
//    public void videoRecordingFailed() {
//
//    }
//
//    @Override
//    public void videoRecordingPrepared() {
//
//    }
//
//    @Override
//    public void shutdownFinished() {
//
//    }
//
//    @Override
//    public void initialized() {
//        // Restore effect state after deepar release
////        deepAR.switchEffect("mask", getFilterPath(masks.get(currentMask)));
////        deepAR.switchEffect("effect", getFilterPath(effects.get(currentEffect)));
////        deepAR.switchEffect("filter", getFilterPath(filters.get(currentFilter)));
//    }
//
//    @Override
//    public void faceVisibilityChanged(boolean b) {
//
//    }
//
//    @Override
//    public void imageVisibilityChanged(String s, boolean b) {
//
//    }
//
//    @Override
//    public void frameAvailable(Image image) {
//
//    }
//
//    @Override
//    public void error(ARErrorType arErrorType, String s) {
//
//    }
//
//
//    @Override
//    public void effectSwitched(String s) {
//
//    }
//
//
//    private void pause() {
//        deepAR.stopVideoRecording();
////        Toast.makeText(getApplicationContext(), "Recording " + videoFileName.getName() + " saved.", Toast.LENGTH_LONG).show();
//        recorder.pause();
//        video_progress.pause();
//        video_progress.addDivider();
//        if(music!=null)
//            music.pause();
//    }
//
//    private void resume() {
//        videoFileName = new File(getExternalFilesDir(null), Constants.DRAFT_FOLDER+Constants.PATH_SEPARATOR+currentSession+Constants.PATH_SEPARATOR+Constants.CLIP+(clipList.size()+1));
//        clipList.add(videoFileName);
//        deepAR.startVideoRecording(videoFileName.toString(), width/2, height/2);
////        Toast.makeText(getApplicationContext(), "Recording started.", Toast.LENGTH_SHORT).show();
//        recorder.start();
//        video_progress.resume();
//        if(music!=null)
//        music.start();
//    }
//
//    private void preview() {
//        pause();
//
//        ProgressDialog dialog = ProgressDialog.show(RecorderActivity.this, "",
//                "Loading. Please wait...", true);
//
//        dialog.show();
//        //CameraResolutionPreset cameraResolutionPreset = CameraResolutionPreset.P1920x1080;
//
//        Intent previewIntent = new Intent(RecorderActivity.this, PreviewActivity.class);
//        previewIntent.putExtra("draft_id",currentSession);
//        previewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(previewIntent);
//        finish();
//        dialog.dismiss();
//
////        ArrayList<EpVideo> epVideos =  new  ArrayList<>();
//       // EpDraw epDraw = new EpDraw("file:///android_asset/app_logo.png",10,10,50,50,false);
//
////        for(File file : clipList){
////            EpVideo epVideo =new EpVideo (file.getAbsolutePath());
////           // epVideo.addDraw(epDraw);
////            epVideos.add(epVideo);
////        }
//
//
//
////        String finalVideo = new File(getExternalFilesDir(null), Constants.DRAFT_FOLDER+Constants.PATH_SEPARATOR+currentSession+Constants.PATH_SEPARATOR+"build.mp4").getAbsolutePath();
////        String finalVideoAud = new File(getExternalFilesDir(null), Constants.DRAFT_FOLDER+Constants.PATH_SEPARATOR+currentSession+Constants.PATH_SEPARATOR+"build_Aud.mp4").getAbsolutePath();
////        String mdirPath = me.getExternalFilesDir(null).getAbsolutePath();
////        String mfilePath = mdirPath + Constants.PATH_SEPARATOR + Constants.DRAFT_FOLDER + Constants.PATH_SEPARATOR + "MUSIC" + Constants.PATH_SEPARATOR + "music.mp3";
////        EpEditor.mergeByLc(this,epVideos, new EpEditor.OutputOption(finalVideo), new OnEditorListener() {
////            @Override
////            public void onSuccess(){
////                Intent previewIntent = new Intent(RecorderActivity.this, PreviewActivity.class);
////                previewIntent.putExtra("videoPath",finalVideo);
////                previewIntent.putExtra("audioPath",mfilePath);
////                startActivity(previewIntent);
////                System.out.println("Video merged succesfully");
////                dialog.dismiss();
////            }
////
////            @Override
////            public void onFailure() {
////                System.out.println("Video merged failed");
////                dialog.dismiss();
////            }
////
////            @Override
////            public void onProgress(float progress) {
////                System.out.println("Video merged progress");
////                dialog.dismiss();
////            }
////        });
//
//
//
//
//    }
//
//    public void initProgressBar() {
//        sec_passed = 0;
//        video_progress = findViewById(R.id.video_progress);
//        video_progress.enableAutoProgressView(recordTimeInMillis);
//        video_progress.setDividerColor(Color.WHITE);
//        video_progress.setDividerEnabled(true);
//        video_progress.setDividerWidth(4);
//        video_progress.setShader(new int[]{Color.rgb(255, 0, 0), Color.rgb(255, 0, 0), Color.rgb(255, 0, 0)});
//
//        video_progress.setListener(new ProgressBarListener() {
//            @Override
//            public void TimeinMill(long mills) {
//                time_in_milis = mills;
//                sec_passed = (int) (mills / 1000);
//               //System.out.println("sec_passed"+sec_passed);
//                //System.out.println("recordTimeInMillis"+(recordTimeInMillis / 1000));
//                //System.out.println("(recordTimeInMillis / 1000) - 1"+((recordTimeInMillis / 1000) - 1));
//                if (time_in_milis >= recordTimeInMillis) {
//                    System.out.println("previewing data");
//                    preview();
//
//                }
//
////                if (is_recording_timer_enable && sec_passed >= recording_time) {
////                    is_recording_timer_enable = false;
////                    start_or_Stop_Recording();
////                }
//
//            }
//        });
//    }
//
//    public void setFilters(FilterEntityParser.FilterEntity pFilterEntity){
//        if(pFilterEntity.getFilterPath() == null){
//            deepAR.switchEffect("filter", (String) null);
//            filterSheetFragment.dismiss();
//            currentFilterName.setText("");
//            System.out.println("Filter is none");
//            return;
//        }
//        String dirPath = me.getExternalFilesDir(null).getAbsolutePath();
//        String filePath = dirPath +Constants.PATH_SEPARATOR+ Constants.DRAFT_FOLDER+Constants.PATH_SEPARATOR+Constants.CACHED_FILTER+Constants.PATH_SEPARATOR+pFilterEntity.getFilterPath();
//        File filterPath = new File(filePath);
//        if(filterPath.exists()){
//            deepAR.switchEffect("filter", filterPath.getAbsolutePath());
//            filterSheetFragment.dismiss();
//            System.out.println("Filter is loaded from cached");
//            currentFilterName.setText(pFilterEntity.getFilterName());
//            return;
//        }
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAXYS3SWHQ4FGICLUQ","lsJ/XSGYprv5z37iEmwcCmsbfRTVRb2NiF/B12sq"));
//
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                try{
//                    S3Object s3object = s3client.getObject("grastone-feed-bucket", "filters/"+pFilterEntity.getFilterPath());
//                    S3ObjectInputStream inputStream = s3object.getObjectContent();
//
//                    FileUtils.copyInputStreamToFile(inputStream, filterPath);
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            deepAR.switchEffect("filter", filterPath.getAbsolutePath());
//                            currentFilterName.setText(pFilterEntity.getFilterName());
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(me, "Unable to load filter", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//
//        });
//
//
//
//
//        filterSheetFragment.dismiss();
//    }
//}