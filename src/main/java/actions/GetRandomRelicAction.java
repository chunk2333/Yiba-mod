package actions;

import Tools.YiBaHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GetRandomRelicAction extends AbstractGameAction {
    private boolean isTrigger;

    public GetRandomRelicAction() {
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
        this.isTrigger = false;
    }
    public void update() {
        //给随机遗物
        if(!this.isTrigger){
            AbstractRelic abstractRelic = AbstractDungeon.returnRandomScreenlessRelic(
                    AbstractDungeon.returnRandomRelicTier());
            YiBaHelper.setBlindBoxRelic(abstractRelic.relicId);
            this.isTrigger = true;
            addToBot(new WaitAction(2.0F));
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY, abstractRelic);

            tickDuration();
        }

        tickDuration();

    }
}
