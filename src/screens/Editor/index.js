


import React, { useState, useRef, useEffect } from 'react';
import { Text, StyleSheet, View, TouchableOpacity, Dimensions, Image } from 'react-native';
import { readFile } from '../../services/file-service';
import VideoUtil from '../../services/video-service';
import RNFS from 'react-native-fs';
import Video from 'react-native-video';

 function EditorScreen(props) {
  
    const BasePath = RNFS.DocumentDirectoryPath;
    const DEFAULT_DRAFT_FOLDER_PATH = BasePath + '/.drafts';
    const DEFAULT_DRAFT_FILE_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_' + props.route.params.session + "__.json";
    const DEFAULT_DRAFT_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__{version}.mp4";
    const DEFAULT_DRAFT_FINAL_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__final.mp4";


    const ref = useRef(null);



  return (
    <>
       <Video
          ref={ref}
          style={{
            position:'absolute',
            top:0,
            left:0,
            right:0,
            bottom:0,
            backgroundColor:'rgba(0,0,0,1)'
            
          }}
          source={{uri: DEFAULT_DRAFT_FINAL_VIDEO_PATH}}
          repeat={true}
          resizeMode={'cover'}
          muted={false}
         
         
          
        />
    
    
    </>

  )
}

export default EditorScreen;
