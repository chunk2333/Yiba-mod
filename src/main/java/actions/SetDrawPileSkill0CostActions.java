package actions;

import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SetDrawPileSkill0CostActions extends AbstractGameAction {

    public SetDrawPileSkill0CostActions() {
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard c : player.drawPile.group){
            if(c.costForTurn + 1 == c.cost){
                c.costForTurn = c.costForTurn + 1;
                c.isCostModifiedForTurn = false;
            }
        }
        UpdateStats.logger.info("重置抽牌堆所有技能牌的费用");
    }
    public SetDrawPileSkill0CostActions(boolean discard) {
        AbstractPlayer player = AbstractDungeon.player;
        for (AbstractCard c : player.discardPile.group){
            if(c.costForTurn + 1 == c.cost){
                c.costForTurn = c.costForTurn + 1;
                c.isCostModifiedForTurn = false;
            }
        }
        UpdateStats.logger.info("重置弃牌堆所有技能牌的费用");
    }
    public void update() {
        tickDuration();
    }
}