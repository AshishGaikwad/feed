import React, { useState } from 'react'
import Background from '../../components/Background'
import Logo from '../../components/Logo'
import Header from '../../components/Header'
import Button from '../../components/Button'
import TextInput from '../../components/TextInput'
import BackButton from '../../components/BackButton'
import {verifyOTP,getUserDetails} from '../../services/auth-service'
import { AsyncStorage } from 'react-native'

export default function OTPScreen({ route, navigation }) {
  const [OTP, setOTP] = useState({ value: '', error: '' })
  const { otp } = route.params;
  const onLoginPressed = async () => {
    const resp = await  verifyOTP(OTP.value,otp.refno,otp.mobile);
    

    if(resp.status){
        const token = resp.token;

        console.log("token",token)
        const userData = await getUserDetails(otp.mobile,token);

        if(userData.status ){

          if(userData.body == null && userData.responseCode == 201){

            
            

          }else{
            AsyncStorage.setItem('SessionToken', token) ;
            AsyncStorage.setItem('UserDetails', JSON.stringify(userData.body) );
            AsyncStorage.setItem('IsLoggedIn',"Y");

            //go to home.
            navigation.navigate('Home');

          }
          
        }


        console.log("userData",userData)
    }else{
        console.log("failed to verify OTP");
    }


    console.log("OTP",otp);

  }

  return (
    <Background>
      <BackButton goBack={navigation.goBack} />
      <Logo />
      <Header>Welcome. pal</Header>
      <TextInput
        label="OTP"
        returnKeyType="next"
        value={OTP.value}
        onChangeText={(text) => setOTP({ value: text, error: '' })}
        error={!!OTP.error}
        errorText={OTP.error}
        autoCapitalize="none"
        autoCompleteType="phone"
        textContentType="phone"
        keyboardType="numeric"
        maxLength={6}
      />
      
      <Button mode="contained" onPress={onLoginPressed}>
        Send OTP
      </Button>
    </Background>
  )
}

