package com.feed.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Filters {
    public static List<String> getAllFilters(){
        List<String> filters = new ArrayList<>();
        filters.add("none");
        filters.add("aviators");
        filters.add("flower_crown");
        filters.add("bigmouth");
        filters.add("dalmatian");
        filters.add("flowers");
        filters.add("koala");
        filters.add("lion");
        filters.add("smallface");
        filters.add("teddycigar");
        filters.add("background_segmentation");
        filters.add("tripleface");
        filters.add("sleepingmask");
        filters.add("fatify");
        filters.add("obama");
        filters.add("mudmask");
        filters.add("pug");
        filters.add("slash");
        filters.add("twistedface");
        filters.add("grumpycat");
        filters.add("Helmet_PBR_V1");
        filters.add("frankenstein");
        filters.add("manly_face");
        filters.add("plastic_ocean");
        filters.add("pumpkin");
        filters.add("scuba");

        filters.add("tape_face");
        filters.add("tiny_sunglasses");



        filters.add("fire");
        filters.add("rain");
        filters.add("heart");
        filters.add("blizzard");
        filters.add("fairy_lights");
        filters.add("beauty");

        filters.add("filmcolorperfection");
        filters.add("tv80");
        filters.add("drawingmanga");
        filters.add("sepia");
        filters.add("bleachbypass");

        return filters;
    }

    public static void copyAssetToInternalStorage(Context context, String src, String dest,String pFIleName) throws IOException {
        InputStream is = context.getAssets().open(src);


        FileOutputStream fos = context.openFileOutput(dest+pFIleName, Context.MODE_PRIVATE);
        byte[] bytes = new byte[102400];

        for (int c = is.read(bytes); c != -1; c = is.read(bytes))
            fos.write(bytes, 0, c);

        fos.close();
        is.close();

        File target = context.getFileStreamPath(dest+pFIleName);
        boolean executabled = target.setExecutable(true);

    }

    public static boolean createFolderIfNotExists(String path) {
        File folder = new File(path);
        if (folder.exists())
            return true;
        else
            return folder.mkdirs();
    }

}
