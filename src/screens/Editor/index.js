


import React, { useState, useRef, useEffect } from 'react';
import { Text, StyleSheet, View, TouchableOpacity, Dimensions, Image } from 'react-native';
import { readFile } from '../../services/file-service';
import VideoUtil from '../../services/video-service';
import RNFS from 'react-native-fs';

 function EditorScreen(props) {
    const BasePath = RNFS.DocumentDirectoryPath;
    const DEFAULT_DRAFT_FOLDER_PATH = BasePath + '/.drafts';
    const DEFAULT_DRAFT_FILE_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_' + props.route.params.session + "__.json";
    const DEFAULT_DRAFT_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__{version}.mp4";



    useEffect(()=>{
        const process = async()=>{
            let DraftData = await readFile(DEFAULT_DRAFT_FILE_PATH);
            DraftData = JSON.parse(DraftData);
            console.log("DraftDataLength", DraftData.videos.length);
      
        

            await VideoUtil.mergeVideo(DraftData);    
        }
    
    
        process();
    })
     
    



  return (
    <></>

  )
}

export default EditorScreen;
