import React, {useState, useRef, useEffect} from 'react';
import {FlatList, View, Text} from 'react-native';
import Post from '../../components/Post';
// import * as Progress from 'react-native-progress';
// const posts = [
//   {
//     id:"001",
//     uri: 'http://resumeonweb.esy.es/videos/1.mp4',
//   },
//   {
//     id:"002",
//     uri: 'http://resumeonweb.esy.es/videos/2.mp4',
//   },
//   {
//     id:"003",
//     uri: 'http://resumeonweb.esy.es/videos/3.mp4',
//   },
//   {
//     id:"004",
//     uri: 'http://resumeonweb.esy.es/videos/4.mp4',
//   },
// ];

function HomeScreen() {
  const [height, setHeight] = useState(0);
  const mediaRefs = useRef([]);
  const [progressBar, setProgressBar] = useState(0);
  const [posts, setPosts] = useState([]);
  const changed = data => {
    // let val = parseFloat(data).toFixed(1);
    // try{
    //   console.log(val)
    //   if(!isNaN(val))
    //    setProgressBar(val)
    //    else{
    //     //setProgressBar(0.0)
    //    }
    // }catch(e){
    //   //setProgressBar(0.0)
    //   console.error(e)
    // }
  };

  /**
   * Called any time a new post is shown when a user scrolls
   * the FlatList, when this happens we should start playing
   * the post that is viewable and stop all the others
   */
  const onViewableItemsChanged = useRef(({changed}) => {
    changed.forEach(element => {
      const cell = mediaRefs.current[element.key];
      if (cell) {
        if (element.isViewable) {
          cell.play();
        } else {
          cell.stop();
        }
      }
    });
  });

  useEffect(() => {
    fetch('http://192.168.0.105:8080/post/')
      .then(response => response.json())
      .then(json => setPosts(json))
      .catch(error => console.error("Error",error));
  }, []);

  const onPageLayout = event => {
    const {width, height} = event.nativeEvent.layout;
    setHeight(height);
  };

  return (
    <View
      style={{
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        backgroundColor: 'rgba(0,0,0,1)',
      }}>
      <Text
        style={{
          zIndex: 99999,
          color: '#fff',
          alignItems: 'center',
          flex: 1,
          justifyContent: 'center',
          textAlign: 'center',
          marginTop: 20,
          textShadowColor: 'black',
          textShadowOffset: {width: 2, height: 2},
          textShadowRadius: 15,
          fontWeight:'bold'
        }}>
        <Text>Following</Text>
        <Text> | </Text>
        <Text>For You</Text>
      </Text>
      {/* <Progress.Bar progress={(progressBar)} animationType={'decay'} indeterminate={false}  width={Dimensions.get('window').width} style={{position:'absolute',zIndex:9999,bottom:50}}/> */}
      <FlatList
        data={posts}
        renderItem={({item}) => (
          <Post
            post={item}
            flatHeight={height}
            ref={PostSingleRef => (mediaRefs.current[item.id] = PostSingleRef)}
          />
        )}
        showsVerticalScrollIndicator={false}
        snapToInterval={height}
        snapToAlignment={'start'}
        decelerationRate={'fast'}
        viewabilityConfig={{
          itemVisiblePercentThreshold: 0,
        }}
        onViewableItemsChanged={onViewableItemsChanged.current}
        style={{
          position: 'absolute',
          top: 0,
          left: 0,
          right: 0,
          bottom: 50,
          backgroundColor: 'rgba(0,0,0,1)',
        }}
        onLayout={onPageLayout}
      />
    </View>
  );
}

export default HomeScreen;
