import {View, StyleSheet, ScrollView, TextInput as FTI, Text} from 'react-native';
export default function TextInput({errorText, ...props}) {
  return (
    <View style={styles.inputContainer}>
      <Text style={styles.inputText}>Enter Your Full Name</Text>
      <FTI
        // style={styles.input}
        // placeholder="Your Full Name"
        // keyboardType="default"
        // onChangeText={text => {
        //   setFullName({text: text, error: false, errorMsg: ''});
        // }}
        // value={fullName}
        {...props}
      />
      <Text style={styles.errorText}>Error will shown here</Text>
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
      color:"#e2e2e2"
    },
    errorText:{
      color:"red",
      margin: 2,
    }
  });