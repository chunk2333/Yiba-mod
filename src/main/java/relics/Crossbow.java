package relics;
//诸葛连弩
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Crossbow extends CustomRelic {
    public static final String ID = "Crossbow";

    private static final String IMG = "img/relics/Crossbow.png";

    private static final String IMG_OTL = "img/relics/outline/Crossbow.png";

    boolean isActive;

    public Crossbow() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        isActive = true;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!this.isActive){
            this.isActive = true;
            return;
        }

        if (card.hasTag(AbstractCard.CardTags.STRIKE)) {
            isActive = false;
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
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
        }

    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        isActive = true;
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
