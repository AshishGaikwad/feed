import { FFmpegKit } from 'ffmpeg-kit-react-native';
import RNFS from 'react-native-fs';
import isFileExist, { createFile, readFile } from './file-service';



export default class VideoUtil {


    static async mergeVideo(fileName, pFinalPath) {
        try {
            var videoCmdBuilder = "";
            videoCmdBuilder += "-y ";
            videoCmdBuilder += "-f ";
            videoCmdBuilder += "concat ";
            videoCmdBuilder += "-safe ";
            videoCmdBuilder += "-0 ";
            videoCmdBuilder += "-i ";
            videoCmdBuilder += "" + fileName + " ";
            videoCmdBuilder += "-c ";
            videoCmdBuilder += "copy ";
            videoCmdBuilder += "-c:a aac ";
            videoCmdBuilder += " " + pFinalPath + " ";
            console.log("final mrge commnd", videoCmdBuilder)
            const mergestatus = await FFmpegKit.execute(videoCmdBuilder);
            console.log("merge status", mergestatus)

            return await isFileExist(pFinalPath);
        } catch (e) {
            console.error("Error occured while merging:", e);
            return false;

        }



    }
}