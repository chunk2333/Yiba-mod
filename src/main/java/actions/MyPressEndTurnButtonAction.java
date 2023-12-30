package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MyPressEndTurnButtonAction extends AbstractGameAction {
    public MyPressEndTurnButtonAction(){
    }
    @Override
    public void update() {
        AbstractDungeon.actionManager.callEndTurnEarlySequence();
        this.isDone = true;
    }
}
