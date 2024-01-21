package patchs;


import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.Sfx;

import java.util.HashMap;

@SpirePatch(cls = "com.megacrit.cardcrawl.audio.SoundMaster", method = SpirePatch.CONSTRUCTOR)
public class SoundMasterPatch {
    @SpireInsertPatch(loc = 410, localvars={"map"})
    public static void addVoice(HashMap<String, Sfx> ___map) {
        YibaMod.logger.info("开始加载自定义声音...");
        ___map.put("HelloLBW", new Sfx("sound/HelloLBW.mp3", false));
        ___map.put("Fall_Steel", new Sfx("sound/Steelpipefalling.mp3", false));
        ___map.put("BlackHand", new Sfx("sound/BlackHandVoice.mp3", false));
        ___map.put("TimeReversal", new Sfx("sound/TimeReversalVoice.mp3", false));
        ___map.put("YuanShenStartBgm", new Sfx("sound/YuanShenStartBgm.mp3", false));
        ___map.put("TimeStop", new Sfx("sound/TimeStop.mp3", false));
        ___map.put("TimeStop01", new Sfx("sound/TimeStop01.mp3", false));
        ___map.put("TimeStop02", new Sfx("sound/TimeStop02.mp3", false));
        ___map.put("AUGHHHH", new Sfx("sound/AUGHHHH.mp3", false));
        ___map.put("KingCrimson", new Sfx("sound/KingCrimson.mp3", false));
        YibaMod.logger.info("自定义声音加载完毕...");
    }
}
