package patchs;
//加强 抢占先机
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.green.Outmaneuver;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnergizedPower;

@SpirePatch(clz = Outmaneuver.class, method = "use")
public class OutmaneuverPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> PerFix(Outmaneuver __instance){
        AbstractPlayer p = AbstractDungeon.player;
        p.gainEnergy(2);
        if (!__instance.upgraded){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, 1), 1));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, 2), 2));
        }

        return SpireReturn.Return();
    }
}
