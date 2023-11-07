package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class WaitActionYibaAction extends AbstractGameAction {
    public WaitActionYibaAction(float setDur) {
        this.setValues(null, null, 0);
        this.duration = setDur;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}
