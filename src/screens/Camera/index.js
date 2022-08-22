


import React, { useState, useRef, useEffect } from 'react';
import { Text, StyleSheet, View, TouchableOpacity, Dimensions, Image } from 'react-native';
import { Camera, useCameraDevices } from 'react-native-vision-camera';
import RecorderView from '../../routes/BaseRoute/RecorderExample';
import * as Progress from 'react-native-progress';
import Icons from '../../components/Icons';
import isFileExist, { copyFile, createDirectory, createFile, readFile } from '../../services/file-service';
import RNFS from 'react-native-fs';
import { useFocusEffect, useNavigation } from '@react-navigation/native';
import VideoUtil from '../../services/video-service';
import SoundPlayer from 'react-native-sound-player';

function CameraScreen(props) {
  const BasePath = RNFS.DocumentDirectoryPath;
  const DEFAULT_DRAFT_FOLDER_PATH = BasePath + '/.drafts';
  const DEFAULT_DRAFT_FILE_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_' + props.route.params.session + "__.json";
  const DEFAULT_DRAFT_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__{version}.mp4";
  const DEFAULT_DRAFT_FINAL_VIDEO_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_VID_' + props.route.params.session + "__final.mp4";
  const DEFAULT_DRAFT_FINAL_MUSIC_PATH = DEFAULT_DRAFT_FOLDER_PATH + '/DRAFT_MUSIC_' + props.route.params.session + "__final.mp3";
  // console.log("props.route.params.music",props.route.params.music)

  const navigation = useNavigation();
  const camera = useRef(null);
  const devices = useCameraDevices();
  const frontCamera = devices.front;
  const backCamera = devices.back;
  const [isClicked, setIsClicked] = useState(false);
  var maxTime = 15;
  const [progress, setProgress] = useState(0.0)
  const [recordingStatus, setRecordingStatus] = useState("STOP");
  const [cameraPosition, setCameraPosition] = useState(0);
  const [flash, setFlash] = useState("off");
  const intervalRef = useRef();
  const [count, setCount] = useState(0);
  const [disableRecorder, setDisableRecorder] = useState(false)
  var musicPlayer = null;

  useEffect(() => {

    // SoundPlayer.loadUrl("https://file-examples.com/storage/fe5467a6a163010b197fb20/2017/11/file_example_MP3_700KB.mp3")  

    var s = Audio("https://file-examples.com/storage/fe5467a6a163010b197fb20/2017/11/file_example_MP3_700KB.mp3");
    s.play();
    const processFiles = async () => {
      const isDraftExist = await isFileExist(DEFAULT_DRAFT_FOLDER_PATH);
      if (!isDraftExist) {
        const isDirectoryCreated = createDirectory(DEFAULT_DRAFT_FOLDER_PATH);
        if (isDirectoryCreated) {
          console.log("Directory is created");
        }
      }

      const isDraftFileExist = await isFileExist(DEFAULT_DRAFT_FILE_PATH);
      if (!isDraftFileExist) {
        const jsonMeta = {
          "name": props.route.params.session,
          "videos": [],
        };

        await createFile(DEFAULT_DRAFT_FILE_PATH, JSON.stringify(jsonMeta));

        if(props.route.params.music != null && props.route.params.music != undefined){
          if(await createFile(DEFAULT_DRAFT_FINAL_MUSIC_PATH, Buffer.from(props.route.params.music))){
            musicPlayer = SoundPlayer.loadSoundFile(DEFAULT_DRAFT_FINAL_MUSIC_PATH);
            musicPlayer.play();
          }
          
        }
      }

      let DraftData = await readFile(DEFAULT_DRAFT_FILE_PATH);
      DraftData = JSON.parse(DraftData);
      //console.log("DraftData", DraftData)
      // let videoFileName
    }

    processFiles();
  })


  




  useEffect(() => {
    Camera.getCameraPermissionStatus().then((status) => {
      if (status != 'authorized') {
        Camera.requestCameraPermission();
      }
    });
    Camera.getMicrophonePermissionStatus().then((status) => {

      if (status != 'authorized') {
        Camera.requestMicrophonePermission()
      }
    });

    let val = parseFloat((1 / maxTime) * count).toFixed(2);
    // console.log(maxTime, count, val)
    setProgress(val);

    //console.log("stopped",camera.current.isStopped)

    if (count >= maxTime) {
      //console.log("time is up")
      //clearInterval(intervalRef.current)
      const stopme = async () => {await camera.current.stopRecording()}
      stopme();
      setRecordingStatus("STOP");
      setDisableRecorder(true);
      


      // navigation.navigate('EditorScreen',{session:props.route.params.session})

    } else {
      setDisableRecorder(false)
    }

    //console.log("Recording status", recordingStatus)

    if (recordingStatus == "STOP") {
      if (intervalRef.current != null || intervalRef.current != undefined) {
        clearInterval(intervalRef.current);
        stopRecording();
      }

    }
  })
  const buttonClickedHandler = () => {
    startRecording();
  };


  // const buttonPressOutHandler = () => {
  //   if (isLongPressed)
  //     stopRecording();
  // }

  const startRecording = () => {

    if (recordingStatus == "START") {
      // setRecordingStatus("STOP");
      // setIsClicked(false);
      stopRecording();
      
    } else {
      // SoundPlayer.resume();
      setRecordingStatus("START");
      intervalRef.current = setInterval(onVideoRecord, 1000);
      setIsClicked(true);
      camera.current.startRecording({
        flash: flash,
        onRecordingFinished: async (video) => {
          console.log(video);
          let DraftData = await readFile(DEFAULT_DRAFT_FILE_PATH);
          DraftData = JSON.parse(DraftData);
          let videoFileName = DEFAULT_DRAFT_VIDEO_PATH;
          videoFileName = videoFileName.replace("{version}", DraftData.videos.length);
          await copyFile(video.path, videoFileName);
          DraftData.videos.push({ filename: videoFileName, duration: video.duration });


          var dur = 0;
          console.log(dur,"START")
          DraftData.videos.forEach((value)=>{
            dur += value.duration
            console.log(dur);
          })
          
          console.log(parseInt(dur),maxTime,parseInt(dur)>=maxTime)
          await createFile(DEFAULT_DRAFT_FILE_PATH, JSON.stringify(DraftData));
          
          if(parseInt(dur)>=maxTime){
            mergeVideos();
          }
          console.log("count inside : "+count);
        },
        onRecordingError: (error) => console.error(error),
      });
    }
  }

  const stopRecording = async () => {
    // SoundPlayer.pause();
    setRecordingStatus("STOP");
    setIsClicked(false);
    await camera.current.stopRecording()
  }

  const onVideoRecord = () => {
    setCount((count) => count + 1);
  }


  const mergeVideos = async () => {
    let DraftData = await readFile(DEFAULT_DRAFT_FILE_PATH);
    DraftData = JSON.parse(DraftData);
    let pVideoArray = DraftData.videos;
    let fileName = DEFAULT_DRAFT_FOLDER_PATH + "/ffmpeg_concat.txt";
    var videoListBuilder = "";  
    for (var i = 0; i < pVideoArray.length; i++) {
      console.log(i, pVideoArray[i]);
      videoListBuilder +="file "+ (pVideoArray[i].filename) + "\r\n"
    }

    // console.log("videoListBuilder",videoListBuilder)

    const fileCreation = await createFile(fileName, videoListBuilder);

    // console.log("fileCreation",fileCreation)
    console.log("fileCreationData", await readFile(fileName));


    const isMerged = VideoUtil.mergeVideo(fileName, DEFAULT_DRAFT_FINAL_VIDEO_PATH);

    if(await isMerged){
      navigation.navigate('EditorScreen',{session:props.route.params.session})
    }else{
      console.log("Merging failed")
    }



  }



  if ((cameraPosition == 0 ? frontCamera : backCamera) == null) return <Text>Camera is not ready</Text>
  return (
    <View style={{
      flex: 1,
      justifyContent: "center",
      alignItems: "center",
    }}>
      <Camera
        ref={camera}
        style={StyleSheet.absoluteFill}
        device={cameraPosition == 0 ? frontCamera : backCamera}
        isActive={true}
        photo={false}
        video={true}
        audio={true}
        orientation="portrait"
        frameProcessorFps={1}
        enableZoomGesture={true}
        torch={flash}
        videoStabilizationMode='auto'
        VideoFileType='mp4'
        CameraVideoCodec='h264'
        fps={60}        
      />


      <TouchableOpacity
        activeOpacity={1}
        onPress={buttonClickedHandler}
        disabled={disableRecorder}
        style={{
          position: 'absolute',
          width: 80 + 20,
          height: 80 + 20,
          justifyContent: 'center',
          alignItems: 'center',
          bottom: 5,
        }}>

        <View style={styles.cameraButtonOuterCircle}></View>
        <View style={!isClicked ? styles.cameraButtonStart : styles.cameraButtonStop}></View>
      </TouchableOpacity>
      <Progress.Bar
        progress={progress}
        indeterminate={false}
        animated={true}
        useNativeDriver={true}
        borderWidth={0}
        borderRadius={0}
        color={'red'}
        width={Dimensions.get('window').width}
        height={4}
        style={styles.progressBar} />
      <Text style={{ fontSize: 30 }}>{count}</Text>


      <View style={styles.toolbox}>
        <View >
          <TouchableOpacity onPress={() => {
            setCameraPosition(cameraPosition == 0 ? 1 : 0)
          }}>
            <Icons name='flip' />
          </TouchableOpacity>
          <TouchableOpacity onPress={() => {
            setFlash(flash == "on" ? "off" : "on");
          }}>
            <Icons name={flash == "on" ? "flash_off" : "flash_on"} />
          </TouchableOpacity>
          <TouchableOpacity onPress={() => {
            if (maxTime == 15) {
              maxTime = 30;
            } else {
              maxTime = 15;
            }
            console.log(maxTime, "maxtime");
          }}>
            <Icons name='timer' />
          </TouchableOpacity>
          <TouchableOpacity onPress={()=>{navigation.navigate('MusicPicker')}}>
            <Icons name='music' />
          </TouchableOpacity>
          {
            count > 0 ? <TouchableOpacity>
              <Icons name='delete' />
            </TouchableOpacity>
              : <></>
          }

        </View>

      </View>
    </View>

  )
}

const styles = StyleSheet.create({

  cameraButtonStart: {
    position: 'absolute',
    width: 80,
    height: 80,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    borderRadius: 100,
    backgroundColor: '#ff0000',
  },
  cameraButtonStop: {
    position: 'absolute',
    width: 5,
    height: 5,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 15,
    borderRadius: 3,
    backgroundColor: '#ff0000',
  },
  cameraButtonOuterCircle: {
    position: 'absolute',
    width: 80 + 20,
    height: 80 + 20,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    borderRadius: 100,
    backgroundColor: 'rgba(52, 52, 52, 0.0)',
    borderColor: '#fff',
    borderStyle: 'solid',
    borderWidth: 4
  },
  progressBar: { position: 'absolute', zIndex: 9999, top: 0, right: 0 },
  toolbox: {
    width: 50,
    backgroundColor: 'rgba(0,0,0,0.5)',
    position: 'absolute',
    top: 30,
    right: 10,
    borderRadius: 10
  }
});

export default CameraScreen;
