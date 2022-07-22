package com.feed.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class SampleSong {

    public static String getMusic(Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open("samplemusic.json");
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }


}
