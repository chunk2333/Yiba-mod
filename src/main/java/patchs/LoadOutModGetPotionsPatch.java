package patchs;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;

@SpirePatch(cls = "loadout.screens.AbstractSelectScreen", method = "doneSelecting", optional = true)
public class LoadOutModGetPotionsPatch {

    @SpireInsertPatch(loc = 121, localvars = {"this.doneSelecting"})
    public static void LoadOutModGetPotionsPatchFix(boolean doneSelecting){
        //YibaMod.logger.info("loadoutMod选择状态：" + doneSelecting);
        YiBaHelper.LoadOutModIsClose = doneSelecting;

    }
}
