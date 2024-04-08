//package patchs;
//
//import YibaMod.YibaMod;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.relics.AbstractRelic;
//
//import java.util.ArrayList;
//
//public class loseGoldPatch {
//    @SpirePatch2(clz = AbstractCreature.class, method = "loseGold")
//    public static class loseGoldFix01 {
//        @SpireInsertPatch(rloc = 0, localvars = {"goldAmount", "gold"})
//        public static SpireReturn<Void> InsertFix(int ___goldAmount, @ByRef int[] ___gold){
//            ___gold[0] -= ___goldAmount;
//            YibaMod.logger.info("Creature失去金币：" + ___goldAmount);
//            return SpireReturn.Return();
//        }
//
//    }
//    @SpirePatch2(clz = AbstractPlayer.class, method = "loseGold")
//    public static class loseGoldFix02 {
//        @SpireInsertPatch(loc = 839, localvars = {"goldAmount", "gold", "relics"})
//        public static SpireReturn<Void> InsertFix(int ___goldAmount, @ByRef int[] ___gold, ArrayList<AbstractRelic> ___relics){
//            ___gold[0] -= ___goldAmount;
//            for (AbstractRelic r : ___relics){
//                r.onLoseGold();
//            }
//            YibaMod.logger.info("player失去金币：" + ___goldAmount);
//            return SpireReturn.Return();
//        }
//
//    }
//}
