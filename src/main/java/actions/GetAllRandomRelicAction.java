package actions;

import Tools.YiBaHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.random.Random;

public class GetAllRandomRelicAction extends AbstractGameAction {

    private boolean isTrigger;

    public static Random relicRng;

    public GetAllRandomRelicAction() {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
        this.isTrigger = false;
    }

    public void update() {
        //给随机遗物
        if(!this.isTrigger){
            AbstractRelic abstractRelic = AbstractDungeon.returnRandomScreenlessRelic(
                    returnAllRandomRelicTier());
            YiBaHelper.setTempRelic2(abstractRelic.relicId);
            this.isTrigger = true;
            addToBot(new WaitAction(2.0F));
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY, abstractRelic);

            tickDuration();
        }

        tickDuration();

    }

    public AbstractRelic.RelicTier returnAllRandomRelicTier(){
        int roll = relicRng.random(1, 6);

        switch (roll){
            case 2:
                return AbstractRelic.RelicTier.UNCOMMON;
            case 3:
                return AbstractRelic.RelicTier.RARE;
            case 4:
                return AbstractRelic.RelicTier.SPECIAL;
            case 5:
                return AbstractRelic.RelicTier.SHOP;
            case 6:
                return AbstractRelic.RelicTier.BOSS;
            case 1:

            default:
                return AbstractRelic.RelicTier.COMMON;

        }

    }
}
