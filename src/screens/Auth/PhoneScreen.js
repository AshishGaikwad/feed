import React, { useState } from 'react'
import Background from '../../components/Background'
import Logo from '../../components/Logo'
import Header from '../../components/Header'
import Button from '../../components/Button'
import TextInput from '../../components/TextInput'
import BackButton from '../../components/BackButton'
import sentOTP from '../../services/auth-service'

export default function PhoneScreen({ navigation }) {
  const [mobile, setMobile] = useState({ value: '', error: '' })

  const onLoginPressed = async () => {
    const otp = await  sentOTP(mobile.value);

    if(otp.status){
      navigation.navigate('OtpScreen',{otp:otp});
    }
  }

  return (
    <Background>
      <BackButton goBack={navigation.goBack} />
      <Logo />
      <Header>Welcome. pal</Header>
      <TextInput
        label="Mobile"
        returnKeyType="next"
        value={mobile.value}
        onChangeText={(text) => setMobile({ value: text, error: '' })}
        error={!!mobile.error}
        errorText={mobile.error}
        autoCapitalize="none"
        autoCompleteType="phone"
        textContentType="phone"
        keyboardType="numeric"
        maxLength={10}
      />
      
      <Button mode="contained" onPress={onLoginPressed}>
        Send OTP
      </Button>
    </Background>
  )
}

function getOTPDetails(){
  
}

