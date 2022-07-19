import { DeviceEventEmitter } from "react-native";

export const initEventListener=(navigation)=>{
    DeviceEventEmitter.addListener('CallMusicPicker',function (e){
        console.log('CallMusicPicker is called');
        console.log(e);
        console.log("1234"+navigation)
        navigation.replace("MusicPicker");
    });
}