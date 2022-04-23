import * as React from 'react';
import {Text, View,NativeModules} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import HomeScreen from '../../screens/HomeScreen';
import {FontAwesomeIcon} from '@fortawesome/react-native-fontawesome';
import {
  faHome,
  faSearch,
  faInbox,
  faUser,
  faPlus,
} from '@fortawesome/free-solid-svg-icons';

import RecorderView   from './RecorderExample';
function SettingsScreen() {
 


  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Text>Settings!</Text>
    </View>
  );
}

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={{
          tabBarShowLabel: false,
          tabBarActiveTintColor: 'rgb(255, 77, 77)',
          tabBarInactiveTintColor: 'rgba(255,255,255,1)',
          tabBarStyle: {
            position: 'absolute',
            height: 50,
            backgroundColor:'rgba(0,0,0,0.9)',
          },
        }}>
        <Tab.Screen
          name="Home"
          component={HomeScreen}
          options={{
            tabBarLabel: 'Home',
            tabBarIcon: ({focused, color, size}) => (
              <FontAwesomeIcon icon={faHome} size={24} color={color} />
            ),
            headerShown: false,
          }}
        />
        <Tab.Screen
          name="Discover"
          component={SettingsScreen}
          options={{
            tabBarLabel: 'Discover',
            tabBarIcon: ({color, size}) => (
              <FontAwesomeIcon icon={faSearch} size={24} color={color}/>
            ),
            headerShown: false,
          }}
        />
        <Tab.Screen
          name="Upload"
          component={SettingsScreen}
          options={{
            tabBarLabel: 'Upload',
            tabBarIcon: ({color, size}) => (
               <FontAwesomeIcon icon={faPlus} size={24} color={color}/>
            ),
            
            headerShown: false,
            
          }}
          listeners={{
            tabPress: (e) => {
              e.preventDefault();
              RecorderView.NavigateMe();
            },
          }}
          
        />
        <Tab.Screen
          name="Inbox"
          component={SettingsScreen}
          options={{
            tabBarLabel: 'Inbox',
            tabBarIcon: ({color, size}) => (
              <FontAwesomeIcon icon={faInbox} size={24} color={color}/>
            ),
            headerShown: false,
          }}
        />
        <Tab.Screen
          name="Me"
          component={SettingsScreen}
          options={{
            tabBarLabel: 'Me',
            tabBarIcon: ({color, size}) => (
              <FontAwesomeIcon icon={faUser} size={24} color={color}/>
            ),
            headerShown: false,
          }}
        />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
