package patchs;

import cards.status.AcidLiquor;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.exordium.ScrapOoze;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class ScrapOozePatch {

    @SpirePatch(clz = ScrapOoze.class, method = SpirePatch.CONSTRUCTOR)
    public static class addShowCard{

        @SpireInsertPatch(loc = 40)
        public static SpireReturn<Void> SpireInsertPatchFix(ScrapOoze __instance){
            EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Scrap Ooze");
            String[] OPTIONS = eventStrings.OPTIONS;
            __instance.imageEventText.setDialogOption(OPTIONS[3] + " #r但是你手上沾着肮脏的东西!!!", new AcidLiquor());
            buttonEffectFix.isTake = false;
            return SpireReturn.Return();
        }
    }
    @SpirePatch2(clz = ScrapOoze.class, method = "buttonEffect")
    public static class buttonEffectFix{

        public static boolean isTake;
        @SpireInsertPatch(loc = 56)
        public static void InsertFix02(ScrapOoze __instance){
            isTake = true;
        }

        @SpireInsertPatch(loc = 87, localvars = {"screenNum"})
        public static SpireReturn<Void> InsertFix03(ScrapOoze __instance, @ByRef int[] ___screenNum){
            isTake = false;
            EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Scrap Ooze");
            String[] OPTIONS = eventStrings.OPTIONS;
            __instance.imageEventText.updateDialogOption(0, OPTIONS[3] + " #r但是你手上沾着肮脏的东西!!!", new AcidLiquor());
            __instance.imageEventText.removeDialogOption(1);
            ___screenNum[0] = 1;
            return SpireReturn.Return();
        }

        @SpireInsertPatch(loc = 97)
        public static void InsertFix01(ScrapOoze __instance){
            if(!isTake){
                isTake = true;
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new AcidLiquor(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }

        }
    }
}
