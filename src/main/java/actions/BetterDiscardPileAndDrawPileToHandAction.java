package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class BetterDiscardPileAndDrawPileToHandAction extends AbstractGameAction {

    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("BetterToHandAction")).TEXT;

    private AbstractPlayer player;

    private int numberOfCards;

    private boolean optional;

    private ArrayList<AbstractCard> drawPile = new ArrayList<>();;

    private ArrayList<AbstractCard> discardPile = new ArrayList<>();;

    public BetterDiscardPileAndDrawPileToHandAction(int numberOfCards, boolean optional) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
        for (AbstractCard c : this.player.drawPile.group)
            this.drawPile.add(c);
        for (AbstractCard c : this.player.discardPile.group)
            this.discardPile.add(c);
    }

    public BetterDiscardPileAndDrawPileToHandAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            int allCardNum = this.player.drawPile.size() + this.player.discardPile.size();
            if (allCardNum == 0 || this.numberOfCards <= 0) {
                this.isDone = true;
                return;
            }
            if (allCardNum <= this.numberOfCards && !this.optional) {
                ArrayList<AbstractCard> cardsToMove = new ArrayList<>();
                for (AbstractCard c : this.player.drawPile.group)
                    cardsToMove.add(c);
                for (AbstractCard c : this.player.discardPile.group)
                    cardsToMove.add(c);
                for (AbstractCard c : cardsToMove) {
                    if (this.player.hand.size() == 10) {
                        this.player.drawPile.moveToDiscardPile(c);
                        this.player.createHandIsFullDialog();
                        continue;
                    }
                    for (AbstractCard c1 : this.drawPile)
                        if (c == c1){
                            this.player.drawPile.moveToHand(c, this.player.drawPile);
                        }
                    for (AbstractCard c1 : this.discardPile)
                        if (c == c1){
                            this.player.discardPile.moveToHand(c, this.player.discardPile);
                        }

                }
                this.isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.player.drawPile.group)
                temp.addToTop(c);
            for (AbstractCard c : this.player.discardPile.group)
                temp.addToTop(c);
            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            if (this.numberOfCards == 1) {
                if (this.optional) {
                    AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);
                } else {
                    AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
                }
            } else if (this.optional) {
                AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
            } else {
                AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
            }
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (this.player.hand.size() == 10) {
                    this.player.drawPile.moveToDiscardPile(c);
                    this.player.createHandIsFullDialog();
                    continue;
                }
                for (AbstractCard c1 : this.drawPile)
                    if (c == c1){
                        this.player.drawPile.moveToHand(c, this.player.drawPile);
                    }
                for (AbstractCard c1 : this.discardPile)
                    if (c == c1){
                        this.player.discardPile.moveToHand(c, this.player.discardPile);
                    }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
