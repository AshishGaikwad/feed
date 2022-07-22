import React, {useState, useRef, useEffect} from 'react';
import {FlatList, View, Text, BackHandler} from 'react-native';
import {Avatar, TabView, Tab} from 'react-native-elements';
import RecorderView from '../../routes/BaseRoute/RecorderExample';


function CameraScreen() {
  

  useEffect(()=>{
   


    // BackHandler.addEventListener('hardwareBackPress', (e)=>{
    //     console.log("back button pressed");
    //     RecorderView.NavigateMe();

    // });
  })




  return (
    <View>
      
    </View>
  );
}

export default CameraScreen;
