//package com.feed.editor;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ProgressDialog;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.View;
//import android.widget.MediaController;
//import android.widget.Toast;
//import android.widget.VideoView;
//
//import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//import com.feed.R;
//import com.feed.util.Constants;
//import com.feed.util.FFmpegEditor;
//
//import org.apache.commons.io.FileUtils;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
////import VideoHandle.EpEditor;
////import VideoHandle.OnEditorListener;
//
//public class PreviewActivity extends AppCompatActivity {
//    private PreviewActivity me = this;
//    private String EXT_DEF_PATH = "";
//    private String DRAFT_FOLDER_PATH = Constants.PATH_SEPARATOR + Constants.DRAFT_FOLDER + Constants.PATH_SEPARATOR;
//    private File musicFile = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Toast.makeText(me, "Getting called", Toast.LENGTH_SHORT).show();
//
//        System.out.println("Inside preview activity ========================");
//        setContentView(R.layout.activity_preview);
//        VideoView simpleVideoView = findViewById(R.id.videoView);
//
//        MediaController mediaController = new MediaController(this);
//        simpleVideoView.setMediaController(mediaController);
//        mediaController.setMediaPlayer(simpleVideoView);
//        simpleVideoView.setVisibility(View.VISIBLE);
//
//        ProgressDialog dialog = ProgressDialog.show(PreviewActivity.this, "",
//                "Loading. Please wait...", true);
//
//        dialog.show();
//        EXT_DEF_PATH =me.getExternalFilesDir(null).getAbsolutePath();
//        String draft_session_id = getIntent().getStringExtra("draft_id");
//
//        List<File> videoList = new ArrayList<>();
//
//        File sessionFolder = new File(EXT_DEF_PATH+DRAFT_FOLDER_PATH+draft_session_id);
//        File[] files = sessionFolder.listFiles();
//        System.out.println(files.length);
//
//        for(File f : files){
//            if(f.getName().startsWith(Constants.CLIP)){
//                videoList.add(f);
//            }else if(f.getName().equalsIgnoreCase("current_audio.mp3")){
//                musicFile = f;
//            }
//        }
//        Collections.sort(videoList, (File file1, File file2) -> ((file1.getName()).compareTo(file2.getName())));
//        dialog.hide();
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Handler handler = new Handler(Looper.getMainLooper());
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                try{
//                    FFmpegEditor editor = new FFmpegEditor();
//                    String videoOutputPath = DRAFT_FOLDER_PATH+draft_session_id+Constants.PATH_SEPARATOR+"build.mp4";
//                    String videoOutputWithMusicPath = DRAFT_FOLDER_PATH+draft_session_id+Constants.PATH_SEPARATOR+"build_aud.mp4";
//                    boolean isVideoMerged = editor.mergeFiles(videoList,EXT_DEF_PATH,videoOutputPath);
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(me, "video merge status == ", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    if(isVideoMerged){
////                        Toast.makeText(me, "Video is merged", Toast.LENGTH_SHORT).show();
//                        isVideoMerged =  editor.mergeAudio(EXT_DEF_PATH+videoOutputPath,musicFile.getAbsolutePath(),EXT_DEF_PATH+videoOutputWithMusicPath,0,1);
//
//                        if(isVideoMerged){
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(me, "audio merge status == ", Toast.LENGTH_SHORT).show();
//                                    simpleVideoView.setVideoPath(EXT_DEF_PATH+videoOutputWithMusicPath);
//                                    simpleVideoView.start();
//                                }
//                            });
//                        }
//                    }
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
//
////        String audioPath = getIntent().getStringExtra("audioPath");
////
////
////        String viewPath = videoPath.replaceAll("build","show");
////
////        EpEditor.music(videoPath, audioPath,viewPath , 0, 1, new OnEditorListener() {
////            @Override
////            public void onSuccess() {
////                System.out.println("Music merged successfully");
////                simpleVideoView.setVideoPath(viewPath);
////                simpleVideoView.start();
////            }
////
////            @Override
////            public void onFailure() {
////                System.out.println("Music merged failed");
////            }
////
////            @Override
////            public void onProgress(float progress) {
////                System.out.println("Music merged progress");
////            }
////        });
////
////
//
//
//    }
//
//
//    public File getAssets(String assetName,String path)  {
//        InputStream in = null;
//        try {
//            in = getResources().getAssets().open(assetName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        OutputStream out = null;
//        try {
//            out = new FileOutputStream(new File(path));
//            final byte[] b = new byte[8192];
//            for (int r; (r = in.read(b)) != -1;) {
//                out.write(b, 0, r);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new File(path);
//    }
//}