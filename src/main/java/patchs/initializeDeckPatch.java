package patchs;
//实现：消逝，关键词。
import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayList;
import java.util.Iterator;

@SpirePatch2(clz = AbstractPlayer.class, method = "preBattlePrep")
public class initializeDeckPatch {

    @SpireInsertPatch(loc = 1983, localvars = {"drawPile"})
    public static void fix(AbstractPlayer __instance, @ByRef CardGroup[] drawPile){
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
        Iterator<AbstractCard> iterator = drawPile[0].group.iterator();
        while (iterator.hasNext()) {
            AbstractCard c = iterator.next();
            if (c.hasTag(YibaMod.VANISH)) {
                cardsToRemove.add(c);
            }
        }
        drawPile[0].group.removeAll(cardsToRemove);
        drawPile[0].group.addAll(0, cardsToRemove);

    }
}
