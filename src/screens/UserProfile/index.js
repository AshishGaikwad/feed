import React, {useState, useRef, useEffect} from 'react';
import {FlatList, View, Text} from 'react-native';
import {Avatar, TabView, Tab} from 'react-native-elements';

function UserProfile() {
  const [index, setIndex] = useState(0);
  return (
    <View>
        <Text style={{textAlign: 'center'}}>Profile</Text>
      <View
        style={{
          margin: 20,
        }}>
        <View
          style={{
            alignItems: 'center',

            justifyContent: 'center',
          }}>
          <Avatar
            rounded
            size="xlarge"
            source={{
              uri: 'https://picsum.photos/id/237/200/300',
            }}></Avatar>
          <Text>Ashish Gaikwad</Text>
          <Text>_ashish_</Text>
          <View style={{flexDirection: 'row'}}>
            <View style={{margin: 10}}>
              <Text style={{textAlign: 'center'}}>100M</Text>
              <Text style={{textAlign: 'center'}}>Likes</Text>
            </View>
            <View style={{margin: 10}}>
              <Text style={{textAlign: 'center'}}>1M</Text>
              <Text style={{textAlign: 'center'}}>Followers</Text>
            </View>
            <View style={{margin: 10}}>
              <Text style={{textAlign: 'center'}}>100</Text>
              <Text style={{textAlign: 'center'}}>Followings</Text>
            </View>
          </View>
        </View>
      </View>

      <View>
        <Tab
          value={index}
          onChange={e => setIndex(e)}
          indicatorStyle={{
            backgroundColor: 'white',
            height: 3,
          }}
          variant="primary">
          <Tab.Item
            title="Feeds"
            titleStyle={{fontSize: 12}}
            // icon={{name: 'timer', type: 'ionicon', color: 'white'}}
          />
          <Tab.Item
            title="Liked Feeds"
            titleStyle={{fontSize: 12}}
            // icon={{name: 'heart', type: 'ionicon', color: 'white'}}
          />
        </Tab>
        
        <TabView value={index} onChange={setIndex} animationType="spring">
          <TabView.Item style={{backgroundColor: 'red', width: '100%'}}>
            <Text style={{backgroundColor:'red'}}>Recent</Text>
          </TabView.Item>
          <TabView.Item style={{backgroundColor: 'blue', width: '100%'}}>
            <Text>Favorite</Text>
          </TabView.Item>
        </TabView>
      </View>
    </View>
  );
}

export default UserProfile;
