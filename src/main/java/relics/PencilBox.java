package relics;
//文具盒
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PencilBox extends CustomRelic {

    public static final String ID = "PencilBox";

    private static final String IMG = "img/relics/PencilBox.png";

    private static final String IMG_OTL = "img/relics/outline/PencilBox.png";

    public PencilBox() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        this.counter += 1;

        if(this.counter == 9){
            //持续闪烁
            beginLongPulse();
        }

        if (this.counter == 10) {
            flash();
            AbstractMonster m = null;
            if (action.target != null)
                m = (AbstractMonster) action.target;
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;
            if (m != null)
                tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            this.counter = 0;
            stopPulse();
        }

    }

    @Override
    public void onVictory() {
        //在胜利时触发
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PencilBox();
    }


}
