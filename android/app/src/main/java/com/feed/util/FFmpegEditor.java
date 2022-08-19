//package com.feed.util;
//
//import android.media.MediaExtractor;
//import android.media.MediaFormat;
//import android.util.Log;
//
//import com.arthenica.mobileffmpeg.Config;
//import com.arthenica.mobileffmpeg.FFmpeg;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class FFmpegEditor {
//
//
//
//    public boolean mergeFiles(List<File> pFiles,String pContextAbsoulutePath,String pOutputPath ){
//
//        String appDir = pContextAbsoulutePath + "/videoeditingmeta/";
    //        String fileName = "ffmpeg_concat.txt";
//        List<String> videos = new ArrayList<>();
//        for (File e : pFiles) {
//            videos.add(e.getAbsolutePath());
//        }
//        writeTxtToFile(videos, appDir, fileName);
//
//
//        StringBuilder cmdBuilder = new StringBuilder();
//        cmdBuilder.append("-y").append(" ");
//        cmdBuilder.append("-f").append(" ");
//        cmdBuilder.append("concat").append(" ");
//        cmdBuilder.append("-safe").append(" ");
//        cmdBuilder.append("0").append(" ");
//        cmdBuilder.append("-i").append(" ");
//        cmdBuilder.append(appDir+fileName).append(" ");
//        cmdBuilder.append("-c").append(" ");
//        cmdBuilder.append("copy").append(" ");
//        cmdBuilder.append(pContextAbsoulutePath+pOutputPath).append(" ");
//
//        int rc = FFmpeg.execute(cmdBuilder.toString());
//
//        if (rc == 0) {
//            Log.i(Config.TAG, "Command execution completed successfully.");
//            return true;
//        } else if (rc == 255) {
//            Log.i(Config.TAG, "Command execution cancelled by user.");
//            return false;
//        } else {
//            Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
//            Config.printLastCommandOutput(Log.INFO);
//            return false;
//        }
//    }
//
//
//    public boolean mergeAudio(String pVideoPath,String pAudioPath,String pOutputPath,int pVideoVolume,int pAudioVolume){
//        MediaExtractor mediaExtractor = new MediaExtractor();
//        try {
//            mediaExtractor.setDataSource(pVideoPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        int at = TrackUtils.selectAudioTrack(mediaExtractor);
//
//        StringBuilder cmdBuilder = new StringBuilder();
//        cmdBuilder.append(" ").append("-y").append(" ").append("-i").append(" ").append(pVideoPath).append(" ");
//        if (at == -1) {
//            int vt = TrackUtils.selectVideoTrack(mediaExtractor);
//            float duration = (float) mediaExtractor.getTrackFormat(vt).getLong(MediaFormat.KEY_DURATION) / 1000 / 1000;
//            cmdBuilder.append(" ").append("-ss").append(" ").append("0").append(" ").append("-t").append(" ").append(duration).append(" ").append("-i").append(" ").append(pAudioPath).append(" ").append("-acodec").append(" ").append("copy").append(" ").append("-vcodec").append(" ").append("copy").append(" ");
//        } else {
//            cmdBuilder.append("-i").append(" ").append(pAudioPath).append(" ").append("-filter_complex").append(" ")
//                    .append("[0:a]aformat=sample_fmts=fltp:sample_rates=44100:channel_layouts=stereo,volume=" + pVideoVolume + "[a0];[1:a]aformat=sample_fmts=fltp:sample_rates=44100:channel_layouts=stereo,volume=" + pAudioVolume + "[a1];[a0][a1]amix=inputs=2:duration=first[aout]").append(" ")
//                    .append(" ").append("-map").append(" ").append("[aout]").append(" ")
//                    .append("-ac").append(" ").append("2").append(" ").append("-c:v")
//                    .append(" ").append("copy").append(" ").append("-map").append(" ").append("0:v:0").append(" ");
//        }
//        cmdBuilder.append(pOutputPath);
//        mediaExtractor.release();
//
//
//        int rc = FFmpeg.execute(cmdBuilder.toString());
//
//        if (rc == 0) {
//            Log.i(Config.TAG, "Command execution completed successfully.");
//            return true;
//        } else if (rc == 255) {
//            Log.i(Config.TAG, "Command execution cancelled by user.");
//            return false;
//        } else {
//            Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
//            Config.printLastCommandOutput(Log.INFO);
//            return false;
//        }
//
//    }
//
//
//
//
//
//
//
//
//    public static void writeTxtToFile(List<String> strcontent, String filePath, String fileName) {
//        makeFilePath(filePath, fileName);
//        String strFilePath = filePath + fileName;
//        String strContent = "";
//        for (int i = 0; i < strcontent.size(); i++) {
//            strContent += "file " + strcontent.get(i) + "\r\n";
//        }
//        try {
//            File file = new File(strFilePath);
//            if (file.isFile() && file.exists()) {
//                file.delete();
//            }
//            file.getParentFile().mkdirs();
//            file.createNewFile();
//            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
//            raf.seek(file.length());
//            raf.write(strContent.getBytes());
//            raf.close();
//            Log.e("TestFile", "写入成功:" + strFilePath);
//        } catch (Exception e) {
//            Log.e("TestFile", "Error on write File:" + e);
//        }
//    }
//
//
//    public static File makeFilePath(String filePath, String fileName) {
//        File file = null;
//        makeRootDirectory(filePath);
//        try {
//            file = new File(filePath + fileName);
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
//
//    public static void makeRootDirectory(String filePath) {
//        File file = null;
//        try {
//            file = new File(filePath);
//            if (!file.exists()) {
//                file.mkdir();
//            }
//        } catch (Exception e) {
//            Log.i("error:", e + "");
//        }
//    }
//}
