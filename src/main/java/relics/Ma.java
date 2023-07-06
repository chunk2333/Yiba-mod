package relics;
//麻
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Ma extends CustomRelic {
    public static final String ID = "Ma";

    private static final String IMG = "img/relics/test.png";

    private static final String IMG_OTL = "img/relics/outline/test.png";

    boolean isActive;

    public Ma() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        isActive = true;
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        if (isActive) {
            flash();
            isActive = false;
            this.grayscale = true;
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.counter < 1 && isActive) {
            this.counter++;
        }
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        if (this.counter >= 1) {
            card.cantUseMessage = this.DESCRIPTIONS[3] + '\001' + this.DESCRIPTIONS[1];
            return false;
        }
        return true;
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        this.grayscale = false;
        this.counter = -1;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PandaMan();
    }

}
