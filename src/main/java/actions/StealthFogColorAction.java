package actions;

import Tools.YiBaHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class StealthFogColorAction extends AbstractGameAction {

    int additionalAmt;

    public StealthFogColorAction(AbstractCreature target, int block, int additional) {
        this.target = target;
        this.amount = block;
        this.additionalAmt = additional;
    }

    public void update() {
        if(YiBaHelper.getLastTriggerElementName() == null){
            this.addToTop(new GainBlockAction(this.target, this.amount));
            this.isDone = true;
            return;
        }
        if (YiBaHelper.getLastTriggerElementName().equals("蒸发")) {
            this.addToTop(new GainBlockAction(this.target, this.amount + this.additionalAmt));
        } else {
            this.addToTop(new GainBlockAction(this.target, this.amount));
        }

        this.isDone = true;
    }
}
