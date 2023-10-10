package patchs;
//黑手遗物的功能实现
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

@SpirePatch2(clz= CardRewardScreen.class, method = "takeReward")
public class CanGetMutiCardReward {
    @SpireInsertPatch(loc = 353)
    public static SpireReturn<Void> Replace(){
        if(AbstractDungeon.player.hasRelic("BlackHand")){
            AbstractDungeon.combatRewardScreen.positionRewards();
            if (AbstractDungeon.combatRewardScreen.rewards.isEmpty()) {
                AbstractDungeon.combatRewardScreen.hasTakenAll = true;
                AbstractDungeon.overlayMenu.proceedButton.show();
            }
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
@SpirePatch(clz= CardRewardScreen.class, method = "acquireCard")
class acquire {
    @SpireInsertPatch(loc = 317, localvars={"hoveredCard"})
    public static void getDraft(CardRewardScreen __instance, AbstractCard hoveredCard){
        if(AbstractDungeon.player.hasRelic("BlackHand")){
            int i = 0;
            for(AbstractCard c : __instance.rItem.cards){
                if(c.cardID.equals(hoveredCard.cardID)){
                    __instance.rItem.cards.remove(i);
                    break;
                }
                i+=1;
            }
            if (__instance.rItem.cards.isEmpty()){
                AbstractDungeon.combatRewardScreen.rewards.remove(__instance.rItem);
            }
        }

    }
}
