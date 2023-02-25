package actions;
//Treacherous（诡谲）的降低本场战斗所有技能牌1费
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TreacherousAction extends AbstractGameAction {

    private AbstractPlayer p;

    public TreacherousAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            //弃牌堆
            for (AbstractCard c : this.p.drawPile.group) {
                if (c.type == AbstractCard.CardType.SKILL) {
                    if (c.cost > 0) {
                        c.costForTurn = c.cost - 1;
                        c.isCostModifiedForTurn = true;
                        c.cost = c.cost - 1;
                        c.isCostModified = true;
                    }
                }
            }
            //手牌
            for (AbstractCard c : this.p.hand.group) {
                if (c.type == AbstractCard.CardType.SKILL) {
                    if (c.cost >= 0) {
                        c.costForTurn = c.cost - 1;
                        c.isCostModifiedForTurn = true;
                        c.cost = c.cost - 1;
                        c.isCostModified = true;
                    }
                }
            }
            //抽牌堆
            for (AbstractCard c : this.p.discardPile.group) {
                if (c.type == AbstractCard.CardType.SKILL) {
                    if (c.cost >= 0) {
                        c.costForTurn = c.cost - 1;
                        c.isCostModifiedForTurn = true;
                        c.cost = c.cost - 1;
                        c.isCostModified = true;
                    }
                }
            }
        }
        //结束指令
        tickDuration();
    }
}
