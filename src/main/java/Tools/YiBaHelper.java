package Tools;


import com.evacipated.cardcrawl.modthespire.Loader;
public class YiBaHelper {
    public static String MakeSoundPath(String id){
        return "YiBa_Sound:" + id;
    }
    public static Boolean hasMod(String modId){
        return Loader.isModLoadedOrSideloaded(modId);
    }
}