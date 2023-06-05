package relics;
//赛博漂流瓶
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class CyberDriftBottle extends CustomRelic {

    public static final String ID = "CyberDriftBottle";

    private static final String IMG = "img/relics/CyberDriftBottle.png";

    private static final String IMG_OTL = "img/relics/outline/CyberDriftBottle.png";

    public CyberDriftBottle() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        //int now_energy = EnergyPanel.getCurrentEnergy();
        //YibaMod.logger.info("赛博漂流瓶, 当前剩余能量:" + now_energy);

    }

    @Override
    public void atBattleStart() {
        if (this.counter != 0){
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.gainEnergy(this.counter);
            this.counter = 0;
        }
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = EnergyPanel.getCurrentEnergy();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CyberDriftBottle();
    }
}
