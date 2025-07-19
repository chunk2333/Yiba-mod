package actions;

import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TheFaintLampCrowsAction extends AbstractGameAction {

    private final boolean upgrade;
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    private AbstractPlayer player;
    private AbstractPlayer p;
    private CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public TheFaintLampCrowsAction(boolean upgrade){
        this.upgrade = upgrade;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.player = AbstractDungeon.player;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, 1);
        for (AbstractCard c : YibaMod.usedCards)
            tmp.addToBottom(c.makeSameInstanceOf());
        //tmp.addToRandomSpot(c);
    }


    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (tmp.size() == 0) {
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
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover();
                if (this.upgrade){
                    c.cost = 0;
                    c.costForTurn = 0;
                }
                addToBot(new MakeTempCardInHandAction(c));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        tickDuration();
    }
}
