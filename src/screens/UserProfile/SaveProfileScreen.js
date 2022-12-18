import React, { useRef, useState } from 'react';
import {
  View,
  StyleSheet,
  ScrollView,
  TextInput,
  Text,
  Pressable,
  Alert,
  Button,
  AsyncStorage

} from 'react-native';
import DatePicker from 'react-native-date-picker';
import SelectDropdown from 'react-native-select-dropdown';
import { saveUserDetails } from '../../services/auth-service';
const months = [
  'January',
  'February',
  'March',
  'April',
  'May',
  'June',
  'July',
  'August',
  'September',
  'October',
  'November',
  'December',
];

const GenderList = ['Male', 'Female', 'Other'];
export default function SaveProfileScreen({ route, navigation }) {
  const { mobileNo, token } = route.params;

  const [fullName, setFullName] = useState({ text: '', errorMsg: '' });
  const fullNameRef = useRef();
  const [userName, setUserName] = useState({ text: '', errorMsg: '' });
  const userNameRef = useRef();
  const [date, setDate] = useState(new Date());
  const [open, setOpen] = useState(false);
  const [dob, setDob] = useState({ text: '', errorMsg: '' });
  const [gender, setGender] = useState({ text: '', errorMsg: '' });
  const [genderI, setGenderI] = useState("");


  const onUsernameCallback = (text) => {
    if (isValidateUsername(text) == "") {
      setUserName({ text: text, error: false, errorMsg: '' });
    } else {
      setUserName({ text: text, error: false, errorMsg: isValidateUsername(text) });
    }
  }

  const onSaveUserDetails = async () => {
    console.log("save userdetails pressed");
    var MyDateString = ('0' + date.getDate()).slice(-2) + ''
      + ('0' + (date.getMonth() + 1)).slice(-2) + ''
      + date.getFullYear();

    var currDate = new Date()


    var currDateString = ('0' + currDate.getDate()).slice(-2) + ''
      + ('0' + (currDate.getMonth() + 1)).slice(-2) + ''
      + currDate.getFullYear();

    if (MyDateString == currDateString) {
      MyDateString = "";
    }
 

    let userData = {
      userName: userName.text,
      fullName: fullName.text,
      dob: MyDateString,
      gender: genderI,
      mobile: mobileNo
    }
    // AsyncStorage.getItem('SessionToken').then(async token => {
    // token = token;
    var ud = await saveUserDetails(userData, token);
    console.log(ud);

    if (ud.responseCode == 200) {

      Alert.alert(ud.responseMsg)

      AsyncStorage.setItem('SessionToken', token);
      AsyncStorage.setItem('UserDetails', JSON.stringify(ud.body));
      AsyncStorage.setItem('IsLoggedIn', "Y");

      //go to home.
      navigation.navigate('Home');


    } else {
      if (ud.responseCode == 206 || ud.responseCode == 207) {
        userNameRef.current.focus();
        setUserName({ text: userName.text, errorMsg: ud.responseMsg });
      }

      if (ud.responseCode == 208) {
        fullNameRef.current.focus();
        setFullName({ text: fullName.text, errorMsg: ud.responseMsg });
      }

      if (ud.responseCode == 210) {
        setDob({ text: dob.text, errorMsg: ud.responseMsg });
      }
    }


    // });



  }



  return (
    <View style={styles.container}>
      <View style={{ color: '#ffffff', textAlign: 'center', height: 50 }}>
        <Text style={{ color: "#ffffff", textAlign: 'center', marginTop: 20 }}>Profile Details</Text>
      </View>
      <ScrollView>
        <View style={styles.inputContainer}>
          <Text style={styles.inputText}>Enter your full name</Text>
          <TextInput
            ref={fullNameRef}
            style={styles.input}
            placeholder="Your Full Name"
            keyboardType="default"
            onChangeText={text => {
              setFullName({ text: text, error: false, errorMsg: '' });
            }}
            value={fullName.text}
          />
          <Text style={styles.errorText}>
            {fullName.errorMsg != '' ? fullName.errorMsg : ''}
          </Text>
        </View>

        <View style={styles.inputContainer}>
          <Text style={styles.inputText}>Choose your username</Text>
          <TextInput
            ref={userNameRef}
            style={styles.input}
            placeholder="Your username"
            keyboardType="default"
            value={userName.text}
            onChangeText={onUsernameCallback}
          />
          <Text style={styles.errorText}>
            {userName.errorMsg != '' ? userName.errorMsg : ''}
          </Text>
        </View>

        <View style={styles.inputContainer}>
          <Text style={styles.inputText}>When you born</Text>
          <Pressable
            onPress={() => {
              console.log('on press called');
              setOpen(true);
            }}>
            <TextInput
              style={styles.input}
              placeholder="Select DOB"
              value={dob.text}
              editable={false}
              selectTextOnFocus={false}
            />
          </Pressable>
          <Text style={styles.errorText}>
            {dob.errorMsg != '' ? dob.errorMsg : ''}
          </Text>
        </View>

        <View style={styles.inputContainer}>
          <Text style={styles.inputText}>Your Mobile</Text>
          <TextInput
            style={styles.input}
            value={mobileNo}
            editable={false}
            selectTextOnFocus={false}
          />

          {/* <Text style={styles.errorText}>
            {dob.errorMsg != '' ? dob.errorMsg : ''}
          </Text> */}
        </View>



        <View style={styles.inputContainer}>
          <Text style={styles.inputText}>Select your gender</Text>
          <SelectDropdown
            data={GenderList}
            onSelect={(selectedItem, index) => {
              console.log(selectedItem, index)
              setGender(selectedItem);
              setGenderI(selectedItem)
            }}
            buttonTextAfterSelection={(selectedItem, index) => {
              // text represented after item is selected
              // if data array is an array of objects then return selectedItem.property to render after item is selected
              return selectedItem
            }}
            rowTextForSelection={(item, index) => {
              // text represented for each item in dropdown
              // if data array is an array of objects then return item.property to represent item in dropdown
              return item
            }}

            buttonStyle={styles.select}
            // buttonTextStyle={styles.select}
            buttonTextStyle={{ color: 'white' }}
            defaultButtonText="Choose your Gender"
          />
          <Text style={styles.errorText}>
            {gender.errorMsg != '' ? gender.errorMsg : ''}
          </Text>
        </View>


        <DatePicker
          modal
          open={open}
          date={date}
          onConfirm={date => {
            setOpen(false);
            setDate(date);
            setDob({
              text:
                months[date.getMonth()] +
                ', ' +
                date.getDate() +
                ', ' +
                date.getFullYear(),
              errorMsg: '',
            });
          }}
          onCancel={() => {
            setOpen(false);
            setDob({ text: '', errorMsg: 'Please Select DOB' });
          }}
          mode="date"
          textColor="#e2e2e2"
        />


      </ScrollView>
      <View style={styles.inputContainer}>
        <Button
          title="Save"
          onPress={onSaveUserDetails}

        />
      </View>
    </View>
  );
}
//#9c9c9c
//#e2e2e2
const styles = StyleSheet.create({
  container: {
    padding: 10,
    backgroundColor: '#121212',
    height: '100%',
  },
  inputContainer: {
    backgroundColor: '#1e1e1e',
    borderWidth: 1,
    borderColor: '#1d1d1d',
    padding: 5,
    margin: 5,
    borderRadius: 10,
  },
  input: {
    height: 50,
    margin: 5,
    padding: 5,
    borderWidth: 1,
    borderColor: '#9c9c9c',
    padding: 10,
    color: '#e2e2e2',
    borderRadius: 10,
    paddingLeft: 20,
    fontSize: 17,
  },
  inputText: {
    margin: 2,
    padding: 5,
    fontSize: 17,
    color: '#e2e2e2',
  },
  errorText: {
    color: 'red',
    margin: 2,
  },
  select: {
    height: 50,
    margin: 5,
    padding: 5,
    borderWidth: 1,
    borderColor: '#9c9c9c',
    backgroundColor: '#1e1e1e',
    padding: 10,
    color: '#e2e2e2',
    borderRadius: 10,
    paddingLeft: 20,
    fontSize: 17,
    width: '100%'
  }
});


function isValidateUsername(pUsername) {
  const unReg = /^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{0,14}$/igm

  if (!unReg.test(pUsername)) {
    return "Only allowed a-z,A-Z,0-9,dot (.) ,underscore (_)";
  }


  return "";

}