package patchs;
//实现关键词：沉底。
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.random.Random;

import java.util.ArrayList;
import java.util.Iterator;

public class CardGroupPatch {

    @SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
    public static class initializeDeck{
        @SpirePrefixPatch
        public static void Prefix(){
            YiBaHelper.isInitializeDeck = true;
        }
    }


    @SpirePatch2(clz = CardGroup.class, method = "shuffle", paramtypez = {})
    public static class shuffleFix01{
        @SpireInsertPatch(rloc = 1, localvars = {"group"})
        public static void InsertFix(CardGroup __instance, @ByRef ArrayList<AbstractCard>[] ___group){
            if (YiBaHelper.isInitializeDeck){
                YiBaHelper.isInitializeDeck = false;
                return;
            }
            ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
            Iterator<AbstractCard> iterator = ___group[0].iterator();
            while (iterator.hasNext()) {
                AbstractCard c = iterator.next();
                if (c.hasTag(YibaMod.LAST)) {
                    cardsToRemove.add(c);
                    YibaMod.logger.info(c.name + "放入抽牌堆最底部");
                }
            }
            ___group[0].removeAll(cardsToRemove);
            ___group[0].addAll(0, cardsToRemove);
        }
    }

    @SpirePatch2(clz = CardGroup.class, method = "shuffle", paramtypez = {Random.class})
    public static class shuffleFix02{
        @SpireInsertPatch(rloc = 1, localvars = {"group"})
        public static void InsertFix(CardGroup __instance, @ByRef ArrayList<AbstractCard>[] ___group){
            if (YiBaHelper.isInitializeDeck){
                YiBaHelper.isInitializeDeck = false;
                return;
            }
            ArrayList<AbstractCard> cardsToRemove = new ArrayList<>();
            Iterator<AbstractCard> iterator = ___group[0].iterator();
            while (iterator.hasNext()) {
                AbstractCard c = iterator.next();
                if (c.hasTag(YibaMod.LAST)) {
                    cardsToRemove.add(c);
                    YibaMod.logger.info(c.name + "放入抽牌堆最底部");
                }
            }
            ___group[0].removeAll(cardsToRemove);
            ___group[0].addAll(0, cardsToRemove);
        }
    }
}
