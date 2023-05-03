package actions;

import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class DeletePotionAction extends AbstractGameAction {

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Back to Basics");

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    public DeletePotionAction(int num){
        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards())
                .size() > 0) {
            YibaMod.logger.info("DeletePotion：选择卡牌");
            AbstractDungeon.gridSelectScreen.open(
                    CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                            .getPurgeableCards()), num, OPTIONS[2], false, false, false, true);
        }
    }

    @Override
    public void update(){
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for(int i = 0;i<AbstractDungeon.gridSelectScreen.selectedCards.size();i++){
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(i), (Settings.WIDTH / 2 + i), (Settings.HEIGHT / 2 + i)));
                CardCrawlGame.sound.play("CARD_EXHAUST");
                AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.gridSelectScreen.selectedCards.get(i));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            YibaMod.logger.info("DeletePotion：删除卡牌");
            this.isDone = true;
        }
    }

}
