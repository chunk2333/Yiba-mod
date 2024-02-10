package patchs;

import cards.status.AcidLiquor;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
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
            return SpireReturn.Return();
        }
    }
    @SpirePatch(clz = ScrapOoze.class, method = "buttonEffect")
    public static class buttonEffectFix{
        @SpireInsertPatch(loc = 97)
        public static void InsertFix(ScrapOoze __instance){
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new AcidLiquor(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }
    }
}
