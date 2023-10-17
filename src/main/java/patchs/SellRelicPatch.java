package patchs;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(clz = AbstractRelic.class, method = "update")
public class SellRelicPatch {

    public static boolean Rclick = false;

    public static boolean RclickStart = false;


    @SpirePrefixPatch
    public static void sellRelic(AbstractRelic __instance){

        if(SellRelicPatch.RclickStart && InputHelper.justReleasedClickRight){
            if(__instance.hb.hovered)
                SellRelicPatch.Rclick = true;
            SellRelicPatch.RclickStart = false;
        }
        if (__instance.isObtained && __instance.hb != null && __instance.hb.hovered && InputHelper.justClickedRight)
            SellRelicPatch.RclickStart = true;
        if (SellRelicPatch.Rclick) {
            SellRelicPatch.Rclick = false;
            YibaMod.logger.info("你右键点击了遗物：" + __instance.name);
        }
    }
}
