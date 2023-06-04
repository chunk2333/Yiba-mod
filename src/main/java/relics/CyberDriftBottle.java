package relics;
//赛博漂流瓶
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CyberDriftBottle extends CustomRelic {

    public static final String ID = "CyberDriftBottle";

    private static final String IMG = "img/relics/CyberDriftBottle.png";

    private static final String IMG_OTL = "img/relics/outline/CyberDriftBottle.png";

    public CyberDriftBottle() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.MAGICAL);
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
        this.counter = AbstractDungeon.player.energy.energy;
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
