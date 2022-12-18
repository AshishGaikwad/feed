const PostStyles =(height)=>{
  return {
    container: {
     height:height,
     width:'100%',
    },
    video:{
      position:'absolute',
      top:0,
      left:0,
      right:0,
      bottom:0,
      backgroundColor:'rgba(0,0,0,1)'
      
    },
    sidebar:{
      right:10,
      bottom:100,
      position:'absolute',
    },
    bottombar:{
      width:'100%',
      position:'absolute',
      bottom:0,
      color:"#eee",
      // backgroundColor:'rgba(0, 0, 0, 0.4)',
      padding:10,
      textAlign:'center'
    },
    userName:{
      color:'#ffffff',
      fontWeight:'bold',
      textShadowColor: 'rgba(0, 0, 0, 0.9)',
      textShadowOffset: {width: -1, height: 1},
      textShadowRadius: 10,

    },
    description:{
      color:'#f2f2f2',
      textShadowColor: 'rgba(0, 0, 0, 0.9)',
      textShadowOffset: {width: -1, height: 1},
      textShadowRadius: 10
    },
    hashtags:{
      color:'#f2f2f2',
      textShadowColor: 'rgba(0, 0, 0, 0.9)',
      textShadowOffset: {width: -1, height: 1},
      textShadowRadius: 10
    },
    music:{
      color:'#f2f2f2',
      textShadowColor: 'rgba(0, 0, 0, 0.9)',
      textShadowOffset: {width: -1, height: 1},
      textShadowRadius: 10
    },

    sidebarIcon:{
      textShadowColor: 'rgba(0, 0, 0, 0.7)',
      textShadowOffset: {width: -1, height: 1},
      textShadowRadius: 10
    },
    sideIconContainer:{
      margin:10,
    },
    sideBarLikes:{
      textAlign:'center',
      color:"#fff"
    },
    imageProfile: {
      width: 50,
      height: 50,
      borderColor: 'red',
      padding:10,
      marging:10,
      borderWidth: 2,
      borderRadius: 75,
      right:3,
      bottom:10
    },

  }
};

export default PostStyles;