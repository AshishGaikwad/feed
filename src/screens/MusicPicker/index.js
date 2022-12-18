import { useNavigation } from '@react-navigation/native';
import React, { useState, useRef, useEffect } from 'react';
import { FlatList, View, Text, BackHandler, StyleSheet, TouchableOpacity } from 'react-native';
import { Avatar, TabView, Tab } from 'react-native-elements';
import Button from '../../components/Button';
import RecorderView from '../../routes/BaseRoute/RecorderExample';
import music from '../../services/samplemusic.json';


function MusicPicker() {
  const navigation = useNavigation();
  const [index, setIndex] = useState(0);



  // useEffect(() => {
  //   BackHandler.addEventListener('hardwareBackPress', (e) => {

  //     console.log("back button pressed");
  //     RecorderView.NavigateMe();

  //   });
  // })






  const press = (key)=>{
    // console.log(key);
    navigation.navigate('CameraScreen',{music:music[key]})
  }
  
  return (
    <View >
      <Button>hi</Button>
      {
        Object.keys(music).map(key => {
          return (<TouchableOpacity onPress={() => press(key)}><Button style={styles.text}>{key}</Button></TouchableOpacity>);

        })
      }
    </View>
  );

  
}


const styles = StyleSheet.create({
  circle: {
    padding: 8,
    borderRadius: 50,
    borderWidth: 1,
    borderColor: 'black',
    height: 35,
    marginBottom: 10,
    alignItems: 'center'
  },
  text: {
    fontFamily: "CircularStd-Book",
    fontSize: 14,
    color: '#2f354b',
    textAlign: 'center'
  }
});

export default MusicPicker;
