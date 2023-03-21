package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class BlockPerNonSkillAction extends AbstractGameAction {
    private final int blockPerCard;

    public BlockPerNonSkillAction(int blockAmount) {
        this.blockPerCard = blockAmount;
        setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = AbstractGameAction.ActionType.BLOCK;
    }

    public void update() {
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type != AbstractCard.CardType.SKILL)
                cardsToExhaust.add(c);
        }
        for (AbstractCard c : cardsToExhaust)
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.blockPerCard));
        for (AbstractCard c : cardsToExhaust)
            addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        this.isDone = true;
    }
}
