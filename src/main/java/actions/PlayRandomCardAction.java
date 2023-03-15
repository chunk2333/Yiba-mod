package actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.green.GlassKnife;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;


public class PlayRandomCardAction extends AbstractGameAction {
    private boolean exhaustCards;

    public PlayRandomCardAction(AbstractCreature target, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                int random;
                random = AbstractDungeon.cardRandomRng.random(1,3);
                AbstractCard randomCard = new GlassKnife();

                if(random==1){

                    randomCard = CardLibrary.getAnyColorCard(AbstractCard.CardRarity.COMMON);
                }
                if(random==2){
                    randomCard = CardLibrary.getAnyColorCard(AbstractCard.CardRarity.UNCOMMON);
                }
                if(random==3){
                    randomCard = CardLibrary.getAnyColorCard(AbstractCard.CardRarity.RARE);
                }

                AbstractCard card = randomCard;
                if (AbstractDungeon.player.hasPower("MasterRealityPower")){
                    card.upgrade();
                }
                card.purgeOnUse = true;
                //card.exhaustOnUseOnce = true;
                card.current_y = -200.0F * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.4F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                addToTop(new NewQueueCardAction(card, this.target, false, true));
                addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
            this.isDone = true;
        }
    }
}