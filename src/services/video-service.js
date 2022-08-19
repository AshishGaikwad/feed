import RNFS from 'react-native-fs';
import { createFile, readFile } from './file-service';



export default class VideoUtil{
    
    
    static async mergeVideo(pVideoArray){
        
        const BasePath = RNFS.DocumentDirectoryPath;
        
       
        const DEFAULT_DRAFT_FOLDER_PATH = BasePath + '/.drafts';
        
        let fileName = DEFAULT_DRAFT_FOLDER_PATH+"/ffmpeg_concat.txt";
        var videoListBuilder = "";
        // (pVideoArray.videos).forEach((value)=>{
        //     videoListBuilder = videoListBuilder+(value.filename)+"\n"
        // })

        for(var i = 0; i < pVideoArray.videos.length; i++){
            console.log(i,pVideoArray.videos[i]);
            videoListBuilder = videoListBuilder+(pVideoArray.videos[i].filename)+"\n"
        }

        // console.log("videoListBuilder",videoListBuilder)

        const fileCreation = await createFile(fileName,videoListBuilder);

        // console.log("fileCreation",fileCreation)
        console.log("fileCreationData",await readFile(fileName));



    }
}