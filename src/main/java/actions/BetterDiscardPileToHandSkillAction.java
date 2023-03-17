package actions;

import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class BetterDiscardPileToHandSkillAction extends AbstractGameAction {
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("BetterToHandAction")).TEXT;

    private AbstractPlayer player;

    private int numberOfCards;

    private boolean optional;

    private boolean isOver;

    public BetterDiscardPileToHandSkillAction(int numberOfCards, boolean optional) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public BetterDiscardPileToHandSkillAction(int numberOfCards) {
        this(numberOfCards, false);
    }
    public void update () {
        this.isOver = false;
        int skillCardNum = 0;
        for (AbstractCard c : this.player.discardPile.group){
            if(c.type== AbstractCard.CardType.SKILL){
                skillCardNum = skillCardNum + 1;
            }

        }
        if (this.duration == this.startDuration) {
            if (this.player.discardPile.isEmpty() || this.numberOfCards <= 0 ||skillCardNum <= 0) {
                this.isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            ArrayList<AbstractCard> cardsToReset = new ArrayList<>();
            for (AbstractCard c : this.player.discardPile.group)
                if (c.type == AbstractCard.CardType.SKILL) {
                    if (c.costForTurn > 0) {
                        c.costForTurn = c.costForTurn - 1;
                        c.isCostModifiedForTurn = true;
                    }
                    temp.addToTop(c);
                    cardsToReset.add(c);
                }
            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            YibaMod.logger.info("欲选择卡牌数："+this.numberOfCards+" 当前可以选择卡牌数："+cardsToReset.size());
            if(this.numberOfCards > cardsToReset.size()){
                this.numberOfCards = cardsToReset.size();
            }
            YibaMod.logger.info("打开卡牌选择界面");
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
                    this.player.discardPile.moveToDiscardPile(c);
                    this.player.createHandIsFullDialog();
                    continue;
                }
                this.player.discardPile.moveToHand(c, this.player.discardPile);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}