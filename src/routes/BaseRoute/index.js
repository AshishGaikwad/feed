import * as React from 'react';
import { AsyncStorage, View,useWindowDimensions } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import HomeScreen from '../../screens/HomeScreen';
import UserProfile from '../../screens/UserProfile';
import { FontAwesomeIcon } from '@fortawesome/react-native-fontawesome';
import {
  faHome,
  faSearch,
  faInbox,
  faUser,
  faPlus,
} from '@fortawesome/free-solid-svg-icons';

import Auth from '../../screens/Auth';
import PhoneScreen from '../../screens/Auth/PhoneScreen';
import OtpScreen from '../../screens/Auth/OtpScreen';
import RecorderView from './RecorderExample';
import SaveProfileScreen from '../../screens/UserProfile/SaveProfileScreen';
import MusicPicker from '../../screens/MusicPicker';
import { initEventListener } from '../../helpers/EventListener';
import CameraScreen from '../../screens/Camera';
import EditorScreen from '../../screens/Editor';

const Tab = createBottomTabNavigator();
const Stack = createNativeStackNavigator();

export default function App() {
  const window = useWindowDimensions();

  console.log("I was here")

  return (
    <View style={{height:window.height,width:window.width}}>
      <NavigationContainer>
        <Stack.Navigator initialRouteName="Landing">
          <Stack.Screen
            name="TabNavigator"
            component={TabNavigator}
            options={{ headerShown: false }}
          />

          <Stack.Screen
            name="PhoneScreen"
            component={PhoneScreen}
            options={{ headerShown: false }}
          />

          <Stack.Screen
            name="OtpScreen"
            component={OtpScreen}
            options={{ headerShown: false }}
          />

          <Stack.Screen
            name="SaveProfileScreen"
            component={SaveProfileScreen}
            options={{ headerShown: false }}
          />

          <Stack.Screen
            name="MusicPicker"
            component={MusicPicker}
            options={{ headerShown: false }}
          />
          <Stack.Screen
            name="CameraScreen"
            component={CameraScreen}
            options={{ headerShown: false }}
          />

          <Stack.Screen
            name="EditorScreen"
            component={EditorScreen}
            options={{ headerShown: false }}
          />
        </Stack.Navigator>
      </NavigationContainer>
    </View>
  );
}

function TabNavigator({ route, navigation }) {
  initEventListener(navigation);
  return (
    <Tab.Navigator

      screenOptions={{
        tabBarShowLabel: false,
        tabBarActiveTintColor: 'rgb(255, 77, 77)',
        tabBarInactiveTintColor: 'rgba(255,255,255,1)',
        tabBarStyle: {
          position: 'absolute',
          height: 50,
          backgroundColor: 'rgba(0,0,0,0.9)',
        },
      }}>
      <Tab.Screen
        name="Home"
        component={HomeScreen}
        options={{
          tabBarLabel: 'Home',
          tabBarIcon: ({ focused, color, size }) => (
            <FontAwesomeIcon icon={faHome} size={24} color={color} />
          ),
          headerShown: false,
        }}
      />
      <Tab.Screen
        name="Discover"
        component={HomeScreen}
        options={{
          tabBarLabel: 'Discover',
          tabBarIcon: ({ color, size }) => (
            <FontAwesomeIcon icon={faSearch} size={24} color={color} />
          ),
          headerShown: false,
        }}
      />
      <Tab.Screen
        name="Upload"
        component={HomeScreen}
        options={{
          tabBarLabel: 'Upload',
          tabBarIcon: ({ color, size }) => (
            <FontAwesomeIcon icon={faPlus} size={24} color={color} />
          ),

          headerShown: false,
        }}
        listeners={({ navigation, routes }) => ({
          tabPress: e => {
            e.preventDefault();

            // AsyncStorage.getItem('IsLoggedIn').then(data => {
            //   if (data != null && data =="Y") {
            // RecorderView.NavigateMe("");
            navigation.navigate('CameraScreen', { session: Date.now().toString() })
            // }
            // else navigation.navigate('PhoneScreen');
            // });
          },
        })}
      />
      <Tab.Screen
        name="Inbox"
        component={HomeScreen}
        options={{
          tabBarLabel: 'Inbox',
          tabBarIcon: ({ color, size }) => (
            <FontAwesomeIcon icon={faInbox} size={24} color={color} />
          ),
          headerShown: false,
        }}
        listeners={({ navigation, route }) => ({
          tabPress: e => {
            e.preventDefault();
            // navigation.navigate(isValidSession() ? 'UserProfile' : 'Inbox');
            AsyncStorage.getItem('IsLoggedIn').then(data => {
              if (data != null && data == "Y") navigation.navigate('Inbox')
              else navigation.navigate('PhoneScreen');
            });
          },
        })}
      />
      <Tab.Screen
        name="UserProfile"
        component={UserProfile}
        options={{
          tabBarLabel: 'Profile',
          tabBarIcon: ({ color, size }) => (
            <FontAwesomeIcon icon={faUser} size={24} color={color} />
          ),
          headerShown: false,
        }}
        listeners={({ navigation, route }) => ({
          tabPress: e => {
            e.preventDefault();
            AsyncStorage.getItem('IsLoggedIn').then(data => {
              if (data != null && data == "Y") navigation.navigate('UserProfile')
              else navigation.navigate('PhoneScreen');
            });
          },
        })}
      />
    </Tab.Navigator>
  );
}
