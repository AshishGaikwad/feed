import React, { useRef } from 'react';
import { View, TouchableOpacity } from 'react-native';
import RNFS from 'react-native-fs';
import Video from 'react-native-video';
import Icons from '../../components/Icons';
import { Slider } from 'react-native-elements/dist/slider/Slider';
// import { VESDK, VideoEditorModal, Configuration } from 'react-native-videoeditorsdk';
import { useNavigation } from '@react-navigation/native';

function EditorScreen(props) {

  const BasePath = RNFS.DocumentDirectoryPath;
  const DEFAULT_DRAFT_FOLDER_PATH = BasePath + '/.drafts';
  const DEFAULT_DRAFT_FILE_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_' + props.route.params.session + "__.json";
  const DEFAULT_DRAFT_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__{version}.mp4";
  const DEFAULT_DRAFT_FINAL_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__final.mp4";
  // const DEFAULT_DRAFT_CACHED_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__cached.mp4";
  const navigation = useNavigation();

  // VESDK.unlockWithLicense(require('./vesdk_license'));

  const ref = useRef(null);



  return (
    <View style={{
      position: 'absolute',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(0,0,0,1)'

    }}>
      {/* <Video
          ref={ref}
          style={{
            position:'absolute',
            top:20,
            left:20,
            right:20,
            bottom:150,
            backgroundColor:'rgba(0,0,0,1)'
          }}
          source={{uri: DEFAULT_DRAFT_CACHED_VIDEO_PATH}}
          repeat={false}
          resizeMode={'contain'}
          muted={false}
          controls={true}
        />

        <View
          style={{
            position:'absolute',
            backgroundColor:'#000',
            height:50,
            bottom:0,
            left:0,
            right:0
          }}
        >
      <TouchableOpacity onPress={()=>{navigation.navigate('MusicPicker')}}>
                  <Icons name='music' />
       </TouchableOpacity>
        </View>

        <View
          style={{
            position:'absolute',
            backgroundColor:'#eee',
            height:50,
            bottom:50,
            left:0,
            right:0
          }}
        >
          <Slider
          onValueChange={(value)=>{
            console.log(value,"asas")
          }}
          >

          </Slider>
          

        </View> */}
      {/* <VideoEditorModal 
        visible={true} 
        video={DEFAULT_DRAFT_FINAL_VIDEO_PATH} 
        onExport={(result)=>{
          console.log("result", result);
        }}
        onCancel={()=>{
          navigation.navigate('CameraScreen',props.route.params)
        }}

        /> */}

    </View>

  )
}

export default EditorScreen;
