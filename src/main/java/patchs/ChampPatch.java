package patchs;
//第勇召唤狗男女
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Centurion;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.monsters.city.Healer;

@SpirePatch2(clz = Champ.class, method = "getMove")
public class ChampPatch {

    @SpireInsertPatch(loc = 295, localvars = {"numTurns"})
    public static SpireReturn<Void> InsertGetMoveFix(Champ __instance, int ___numTurns){

        if (GameActionManager.turn == 3){
            __instance.setMove((byte)114514, AbstractMonster.Intent.UNKNOWN);
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}

@SpirePatch2(clz = Champ.class, method = "takeTurn")
class ChampPatchTake{
    @SpireInsertPatch(loc = 149)
    public static SpireReturn<Void> Fix(Champ __instance){
        if (__instance.nextMove == (byte)114514){
            AbstractDungeon.actionManager.addToBottom(new AnimateShakeAction(__instance, 1.0F, 0.1F));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Healer(200.0F, 20.0F), false));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Centurion(-455.0F, 0.0F), false));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(__instance));
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
