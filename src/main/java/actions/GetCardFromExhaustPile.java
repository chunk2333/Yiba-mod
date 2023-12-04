package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GetCardFromExhaustPile extends AbstractGameAction {

    private AbstractCard c = null;

    private AbstractPlayer p = AbstractDungeon.player;

    public GetCardFromExhaustPile(AbstractCard card){
        this.c = card;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update(){
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractCard card = null;
            for(AbstractCard card1 : this.p.exhaustPile.group){
                if (card1.cardID.equals(this.c.cardID)){
                    card = card1;
                    break;
                }
            }

            if (card == null){
                this.isDone = true;
                return;
            }

            card.unfadeOut();
            this.p.hand.addToHand(card);
            if (AbstractDungeon.player.hasPower("Corruption") && card.type == AbstractCard.CardType.SKILL)
                card.setCostForTurn(-9);

            this.p.exhaustPile.removeCard(card);
            if (this.c.upgraded && card.canUpgrade())
                card.upgrade();
            card.unhover();
            card.fadingOut = false;
            this.isDone = true;
        }
        tickDuration();
    }
}
