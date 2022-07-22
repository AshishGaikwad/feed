package com.feed.modules;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.feed.editor.RecorderActivity;

import javax.annotation.Nonnull;

public class RecoderViewModule extends ReactContextBaseJavaModule {
    ReactApplicationContext context = getReactApplicationContext();

    RecoderViewModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext = context;
    }

    /**
     * @return the name of this module. This will be the name used to {@code require()} this module
     * from javascript.
     */
    @NonNull
    @Override
    public String getName() {
        return "RecorderView";
    }


    @ReactMethod
    public void NavigateMe(){
        Intent intent = new Intent(context, RecorderActivity.class);
        if(intent.resolveActivity(context.getPackageManager()) != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

     @ReactMethod
    public void NavigateMe(String pMusicBase64){

        Intent intent = new Intent(context, RecorderActivity.class);
        if(intent.resolveActivity(context.getPackageManager()) != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            intent.putExtra("MusicData",pMusicBase64);
            context.startActivity(intent);
        }
    }

}
