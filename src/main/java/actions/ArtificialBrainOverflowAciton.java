package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class ArtificialBrainOverflowAciton extends AbstractGameAction {

    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("ArtificialBrainOverflowAciton")).TEXT;

    private AbstractPlayer player;

    private int numberOfCards;

    public ArtificialBrainOverflowAciton(int numberOfCards) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.player.discardPile.isEmpty() || this.numberOfCards <= 0) {
                this.isDone = true;
                return;
            }
            int maxSelectable = Math.min(this.numberOfCards, this.player.discardPile.size());
            AbstractDungeon.gridSelectScreen.open(this.player.discardPile, maxSelectable, true, TEXT[1] + this.numberOfCards + TEXT[2]);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.exhaust = true;
                AbstractDungeon.player.discardPile.moveToExhaustPile(c);
                AbstractDungeon.player.discardPile.group.remove(c);
            }
            int selectedCount = AbstractDungeon.gridSelectScreen.selectedCards.size();
            if (selectedCount > 0) {
                addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, AbstractDungeon.gridSelectScreen.selectedCards.size()), AbstractDungeon.gridSelectScreen.selectedCards.size()));
                addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, AbstractDungeon.gridSelectScreen.selectedCards.size()), AbstractDungeon.gridSelectScreen.selectedCards.size()));

            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
