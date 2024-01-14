package patchs;
//乐加维林彩蛋
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(clz = Lagavulin.class, method = "takeTurn")
public class LagavulinEasterEggPatch {

    @SpireInsertPatch(loc = 156)
    public static void InsertFix01(){
        CardCrawlGame.sound.playA("AUGHHHH", 0F);
    }

    @SpireInsertPatch(loc = 160)
    public static void InsertFix02(){
        CardCrawlGame.sound.playA("AUGHHHH", 0F);
    }

}

@SpirePatch(clz = Lagavulin.class, method = "changeState")
class LagavulinEasterEggPatch02{

    @SpireInsertPatch(loc = 197)
    public static void InsertFix(Lagavulin __instance){
        if (AbstractDungeon.player.hasRelic("TungstenRod")){
            CardCrawlGame.sound.playA("Fall_Steel", 0F);
            AbstractRelic r = AbstractDungeon.player.getRelic("TungstenRod");
            r.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(__instance, r));
        }

    }
}