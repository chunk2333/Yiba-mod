package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class SinfulKarmaAction extends AbstractGameAction {

    private final ArrayList<AbstractCard> DrawPile = new ArrayList<>();
    private final ArrayList<AbstractCard> DiscardPile = new ArrayList<>();
    private final ArrayList<AbstractCard> HandPille = new ArrayList<>();

    public SinfulKarmaAction(){
        for (AbstractCard c : AbstractDungeon.player.drawPile.group){
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS){
                this.DrawPile.add(c);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group){
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS){
                this.DiscardPile.add(c);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.hand.group){
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS){
                this.HandPille.add(c);
            }
        }
    }

    public void update(){

        for (AbstractCard c : this.DrawPile){
            c.exhaust = true;
            AbstractDungeon.player.drawPile.moveToExhaustPile(c);
            AbstractDungeon.player.drawPile.group.remove(c);
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }

        for (AbstractCard c : this.DiscardPile){
            c.exhaust = true;
            AbstractDungeon.player.discardPile.moveToExhaustPile(c);
            AbstractDungeon.player.discardPile.group.remove(c);
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }

        for (AbstractCard c : this.HandPille){
            c.exhaust = true;
            AbstractDungeon.player.hand.moveToExhaustPile(c);
            AbstractDungeon.player.hand.group.remove(c);
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }


        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.player.hand.refreshHandLayout();

        tickDuration();
    }
}
