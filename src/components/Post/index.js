import React, {
  forwardRef,
  useImperativeHandle,
  useRef,
  useEffect,
  useState,
} from 'react';
import {
  Text,
  View,
  TouchableWithoutFeedback,
  Dimensions,
  StyleSheet,Image
} from 'react-native';
import {PostStyle} from './styles';
import Video from 'react-native-video';
import {FontAwesomeIcon} from '@fortawesome/react-native-fontawesome';
import {faHeart, faComments, faShare,faMusic} from '@fortawesome/free-solid-svg-icons';

import AWS from 'aws-sdk';
import AppConfig from '../../../config.json';
import * as Progress from 'react-native-progress';
import PostStyles from './styles';

const Post = forwardRef(({post, flatHeight}, parentRef) => {
  const [isPaused, setPaused] = useState(true);
  const ref = useRef(null);
  const [sUrl, setSUrl] = useState('');
  const [playBackTime, setPLayBackTime] = useState(0);
  const [loading, setLoading] = useState(true);
  useImperativeHandle(parentRef, () => ({
    play,
    unload,
    stop,
  }));

  const play = async () => {
    setPaused(false);
  };

  const stop = async () => {
    setPaused(true);
  };

  const unload = async () => {
    // console.log('unload video');
  };

  const playOrPaused = () => {
    if (!isPaused) stop();
    else play();
  };
  useEffect(() => {
    unload();
    AWS.config.update({
      accessKeyId: AppConfig.accessKeyId,
      secretAccessKey: AppConfig.secretAccessKey,
      region: AppConfig.region,
    });
    const s3 = new AWS.S3();
    const myBucket = 'grastone-feed-bucket';
    const myKey = 'posts/' + post.videoURL;
    const signedUrlExpireSeconds = 60 * 5;

    const signedURL = s3.getSignedUrl('getObject', {
      Bucket: myBucket,
      Key: myKey,
      Expires: signedUrlExpireSeconds,
    });

    // console.log(signedURL, 'signedURL');
    setSUrl(signedURL);
  }, []);

  const PostStyle = StyleSheet.create(PostStyles(flatHeight));

  return (
    <View style={PostStyle.container}>
      <TouchableWithoutFeedback onPress={() => playOrPaused()}>
        <Video
          ref={ref}
          style={PostStyle.video}
          source={{uri: sUrl}}
          repeat={false}
          resizeMode={'cover'}
          paused={isPaused}
          muted={false}
          playInBackground={false}
          playWhenInactive={false}
          onBuffer={() => {
            // console.log('onBuffer');
            // this.setState({
            //   loading: true,
            // });
          }}
          onLoadStart={() => {
            // console.log('Loading started');
            setLoading(true);
          }}
          onLoad={() => {
            setLoading(false);
          }}
          onProgress={({currentTime, playableDuration, seekableDuration}) => {
            // console.log(
            //   'onProgress ==' + currentTime,
            //   playableDuration,
            //   seekableDuration,
            // );

            let val = parseFloat((1 / playableDuration) * currentTime).toFixed(
              2,
            );

            try {
              // console.log(val)
              if (!isNaN(val)) setPLayBackTime(val);
              else {
                setPLayBackTime(0.0);
              }
            } catch (e) {
              setPLayBackTime(0.0);
              console.error(e);
            }
          }}
        />
      </TouchableWithoutFeedback>
      <View style={PostStyle.sidebar}>
      <Image
          style={PostStyle.imageProfile}
          source={{uri:'https://picsum.photos/200'}}
          resizeMode={"cover"} // <- needs to be "cover" for borderRadius to take effect on Android
        />
        <View style={PostStyle.sideIconContainer}>
       


          <FontAwesomeIcon
            icon={faHeart}
            style={PostStyle.sidebarIcon}
            size={25}
            color={'white'}
          />
          <Text style={PostStyle.sideBarLikes}>{post.likes}</Text>
        </View>

        <View style={PostStyle.sideIconContainer}>
          <FontAwesomeIcon
            icon={faComments}
            style={PostStyle.sidebarIcon}
            size={25}
            color={'white'}
          />
        </View>
        <View style={PostStyle.sideIconContainer}>
          <FontAwesomeIcon
            icon={faShare}
            style={PostStyle.sidebarIcon}
            size={25}
            color={'white'}
          />
        </View>
      </View>
      <View style={PostStyle.bottombar}>
        <Text style={PostStyle.userName}>{'@' + post.uploadedBy.userName}</Text>
        <Text style={PostStyle.description}>
          {post.description}
          <Text style={PostStyle.hashtags}>{' ' + post.hashtags}</Text>
        </Text>

        <Text style={PostStyle.music}>
          <FontAwesomeIcon
            icon={faMusic}
            size={16}
            color={'white'}
          />
          {" "+ post.title}
        </Text>
      </View>
       <Progress.Bar progress={playBackTime}  indeterminate={loading} animated ={true} useNativeDriver={true} borderWidth={0} borderRadius={0} color={'white'} width={Dimensions.get('window').width} height={2} style={{position:'absolute',zIndex:9999,bottom:0,right:0}}/>
    </View>
  );
});

export default Post;
