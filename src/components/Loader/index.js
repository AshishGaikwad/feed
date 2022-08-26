import React from 'react';
import { ActivityIndicator, View,Text } from "react-native";


 const Loader =()=>{
    return (
        <View style={{
            position: "absolute",
            zIndex: 1,
            left: 0,
            right: 0,
            top: 0,
            bottom: 0,
            alignItems: "center",
            justifyContent: "center",
            backgroundColor: "#00000080",
          }}>
            <ActivityIndicator size="large" color="#0000ff" />
            <Text style={{color:"#fff"}}>Please wait..</Text>
          </View>
    )
}

export default Loader;