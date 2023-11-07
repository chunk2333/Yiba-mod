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
    }
}
