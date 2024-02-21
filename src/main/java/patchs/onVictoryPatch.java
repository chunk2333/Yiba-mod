package patchs;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import cards.colorless.TheFaintLampCrows;
import cards.purple.LosingAnger;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(clz = AbstractPlayer.class, method = "onVictory")
public abstract class onVictoryPatch {

    @SpirePostfixPatch
    public static void PostFix(){
        LosingAnger.times = 0;
        YiBaHelper.LastAttackMonster = null;
        YiBaHelper.LastPlayedCard = null;
        YibaMod.usedCards.clear();
    }

}
