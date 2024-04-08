//package patchs;
//
//import YibaMod.YibaMod;
//import com.evacipated.cardcrawl.modthespire.lib.ByRef;
//import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
//import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
//import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.core.AbstractCreature;
//import com.megacrit.cardcrawl.shop.ShopScreen;
//
//public class ShopScreenPatchs {
//    @SpirePatch2(clz = ShopScreen.class, method = "update")
//    public static class ShopScreenFix01 {
//        @SpireInsertPatch(loc = 0, localvars = {"hoveredCard"})
//        public static SpireReturn<Void> InsertFix(AbstractCard ___hoveredCard){
//            return SpireReturn.Return();
//        }
//
//    }
//}
