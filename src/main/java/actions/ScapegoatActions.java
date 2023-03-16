package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;
import java.util.Iterator;

public class ScapegoatActions extends AbstractGameAction {
    private final AbstractPlayer p;

    private final boolean upgrade;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private final ArrayList<AbstractCard> exhumes = new ArrayList<>();

    public ScapegoatActions(boolean upgrade) {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }
            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.exhaustPile.size() == 1) {
                if (this.p.exhaustPile.group.get(0).cardID.equals("Exhume") || this.p.exhaustPile.group.get(0).cardID.equals("Scapegoat")) {
                    this.isDone = true;
                    return;
                }
                AbstractCard abstractCard = this.p.exhaustPile.getTopCard();
                abstractCard.unfadeOut();
                this.p.hand.addToHand(abstractCard);
                if (AbstractDungeon.player.hasPower("Corruption") && abstractCard.type == AbstractCard.CardType.SKILL)
                    abstractCard.setCostForTurn(-9);
                this.p.exhaustPile.removeCard(abstractCard);
                if (this.upgrade && abstractCard.canUpgrade())
                    abstractCard.upgrade();
                abstractCard.unhover();
                abstractCard.fadingOut = false;
                this.isDone = true;
                return;
            }
            for (AbstractCard abstractCard : this.p.exhaustPile.group) {
                abstractCard.stopGlowing();
                abstractCard.unhover();
                abstractCard.unfadeOut();
            }
            for (Iterator<AbstractCard> c = this.p.exhaustPile.group.iterator(); c.hasNext(); ) {
                AbstractCard derp = c.next();
                if (derp.cardID.equals("Exhume") || derp.cardID.equals("Scapegoat")) {
                    c.remove();
                    this.exhumes.add(derp);
                }
            }
            if (this.p.exhaustPile.isEmpty()) {
                this.p.exhaustPile.group.addAll(this.exhumes);
                this.exhumes.clear();
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.p.hand.addToHand(c);
                if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL)
                    c.setCostForTurn(-9);
                this.p.exhaustPile.removeCard(c);
                if (this.upgrade && c.canUpgrade())
                    c.upgrade();
                c.unhover();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            this.p.exhaustPile.group.addAll(this.exhumes);
            this.exhumes.clear();
            for (AbstractCard c : this.p.exhaustPile.group) {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            }
        }
        tickDuration();
    }
}
