package actions;
//重生buff代码，删除了每回合减一层的代码。
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class MyRegenAction extends AbstractGameAction {
    public MyRegenAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.target.currentHealth > 0) {
                this.target.tint.color = Color.CHARTREUSE.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
                this.target.heal(this.amount, true);
            }
        }
        tickDuration();
    }
}
