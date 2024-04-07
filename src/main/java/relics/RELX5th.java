package relics;
//锐刻五代
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class RELX5th extends CustomRelic {

    public static final String ID = "RELX5th";

    private static final String IMG = "img/relics/RELX5th.png";

    private static final String IMG_OTL = "img/relics/outline/RELX5th.png";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Back to Basics");

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    public RELX5th() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void update() {
        super.update();
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.EVENT){
            return;
        }
        YibaMod.actionList.add(new AbstractGameAction() {
            @Override
            public void update() {
                if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards

                            .get(0), (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
                    AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
                tickDuration();
            }
        });

    }

    @Override
    public void onVictory() {
        flash();
        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards())
                .size() > 0) {
            AbstractDungeon.gridSelectScreen.open(
                    CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck
                            .getPurgeableCards()), 1, OPTIONS[2], false, false, false, true);
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RELX5th();
    }

}
