package actions;

import Tools.YiBaHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FuturePerspectiveActions extends AbstractGameAction {
    private final boolean upgrade;
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    private AbstractPlayer player;
    private AbstractPlayer p;
    private CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public FuturePerspectiveActions (boolean upgrade){
        this.upgrade = upgrade;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.player = AbstractDungeon.player;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, 1);
        for (AbstractCard c : YiBaHelper.getAllCards(true))
            tmp.addToBottom(c.makeSameInstanceOf());

        tmp.sortByCost(false);
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (tmp.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.amount == 1) {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[1], false);
            }
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover();
                if (this.upgrade){
                    c.setCostForTurn(0);
                }
                addToBot(new MakeTempCardInHandAction(c));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }

}

