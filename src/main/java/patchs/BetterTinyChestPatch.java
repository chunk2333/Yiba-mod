package patchs;
//加强小宝箱
import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.TinyChest;

@SpirePatch(clz= TinyChest.class, method = "getUpdatedDescription")
public class BetterTinyChestPatch {
    @SpirePrefixPatch
    public static SpireReturn<String> BetterTinyChestFixDescription(AbstractRelic __instance){
        String[] TEXT = (CardCrawlGame.languagePack.getUIString("BetterTinyChestPatch")).TEXT;
        return SpireReturn.Return(__instance.DESCRIPTIONS[0] + '4' + __instance.DESCRIPTIONS[1] + TEXT[0]);
    }

}

@SpirePatch(clz = TinyChest.class, method = "onEquip")
class TinyChestOnEquip {
    @SpirePrefixPatch
    public static void FixTinyChestOnEquip() {
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(100);
        YibaMod.logger.info("拾起了小宝箱遗物");
    }
}
