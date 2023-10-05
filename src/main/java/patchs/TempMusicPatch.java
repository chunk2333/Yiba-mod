package patchs;
//添加自定义BGM
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.badlogic.gdx.audio.Music;

@SpirePatch(clz = TempMusic.class, method = "getSong")
public class TempMusicPatch {
    @SpireInsertPatch(loc = 118, localvars={"key"})
    public static SpireReturn<Music> addMusic(String key){
        if (key.equals("Dio_BGM")){
            return SpireReturn.Return(MainMusic.newMusic("sound/Dio_BGM.mp3"));
        }
        return SpireReturn.Continue();
    }
}
