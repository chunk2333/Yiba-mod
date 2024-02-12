package patchs;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;

public class NeowEventPatch {
    @SpirePatch(clz = NeowEvent.class, method = "blessing")
    public static class NeowEventPostFix{
        @SpirePostfixPatch
        public static void PostFix(){
            if (AbstractDungeon.floorNum < 2 && YiBaHelper.hasMod("RouZa")){
                //AbstractDungeon.cardRewardScreen.customCombatOpen(YiBaHelper.getMultiYibaRandomColorlessCards(3), "选择获得一张杂糅无色牌。", true);
                //AbstractDungeon.cardRewardScreen.reopen();
                AbstractDungeon.cardRewardScreen.open(YiBaHelper.getMultiYibaRandomColorlessCards(3), null, (CardCrawlGame.languagePack.getUIString("CardRewardScreen")).TEXT[1]);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), YiBaHelper.getYibaRandomRelics());
                YibaMod.logger.info("涅奥选项界面.");
            }
            YibaMod.logger.info("涅奥选项界面完毕.");
        }
    }
}
