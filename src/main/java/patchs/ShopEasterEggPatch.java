package patchs;
//为商人添加一个彩蛋：当你买不起时会额外出现“买不起别摸”的提示
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.shop.ShopScreen;
import java.util.ArrayList;

@SpirePatch(clz = ShopScreen.class, method = "getCantBuyMsg")
public class ShopEasterEggPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ShopEasterEggPatch");

    @SpireInsertPatch(loc = 343, localvars = {"list"})
    public static void FixShopEasterEgg(ArrayList<String> list) {
        list.add(uiStrings.TEXT[0]);
    }
}
