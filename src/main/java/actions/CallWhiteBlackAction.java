package actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.ArrayList;

public class CallWhiteBlackAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CallWhiteBlackAction");

    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();

    public CallWhiteBlackAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, true);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();
                AbstractCard temp_card = c.makeCopy();
                this.p.hand.moveToExhaustPile(c);
                int magicNum = temp_card.baseMagicNumber;
                int damageNum = temp_card.baseDamage;
                temp_card.baseDamage = magicNum;
                temp_card.baseMagicNumber = damageNum;
                temp_card.initializeDescription();
                temp_card.superFlash();
                temp_card.applyPowers();
                addToTop(new MakeTempCardInHandAction(temp_card));
                //returnCards();
                this.isDone = true;
            }
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                //新对象
                AbstractCard temp_card = c.makeCopy();
                this.p.hand.moveToExhaustPile(c);
                int magicNum = temp_card.baseMagicNumber;
                int damageNum = temp_card.baseDamage;
                temp_card.baseDamage = magicNum;
                temp_card.baseMagicNumber = damageNum;
                temp_card.initializeDescription();
                temp_card.superFlash();
                temp_card.applyPowers();
                addToTop(new MakeTempCardInHandAction(temp_card));
                //this.p.hand.addToTop(c);
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotUpgrade)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}
