
import RNFS from 'react-native-fs';

const BasePath = RNFS.DocumentDirectoryPath;

export default async function isFileExist(pFilePath){
    if(await RNFS.exists(pFilePath)){
        return true;
    }else{
        return false;
    }
}

export async function createDirectory(pDirPath){
    try{
        await RNFS.mkdir(pDirPath);
        return true;
    }catch(e){
        console.error(e,"Error occured while creating dir");
        return false;
    }
}

export  async function createFile(pPath,pContent){
    try{
        await RNFS.writeFile(pPath, pContent, 'utf8');
        return true;
    }catch(e){
        console.error(e,"Error occured while Writing file");
        return false;
    }
    return 
}

export async function readFile(pPath){
    try {
        return await RNFS.readFile(pPath, 'utf8');
    } catch (error) {
        console.error(error)
        return "";
    }
}

export async function readFileAsBase64(pPath){
    try {
        return await RNFS.readFile(pPath, 'base64');
    } catch (error) {
        console.error(error)
        return "";
    }
}

export async function copyFile(pFrom,pTo){
    try {
        return await RNFS.copyFile(pFrom, pTo);
    } catch (error) {
        
    }
}

export async function deleteFile(pPath){
    try {
        RNFS.exists(filePath)
        .then((res) => {
          if (res) {
            RNFS.unlink(filePath)
              .then(() => console.log('FILE DELETED'))
          }
        }) 
    } catch (error) {
        console.log(error)
    }
}