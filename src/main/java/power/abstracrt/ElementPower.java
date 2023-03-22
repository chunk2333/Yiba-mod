package power.abstracrt;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class ElementPower extends AbstractPower {

    public ElementPower(AbstractCreature owner, int amt){

    }

    public ElementPower() {
        super();
    }

    public abstract void triggerElement(String reactionName);

    public void show() {
        super.flash();
    }

}
