package relics;
//太阳能电路板
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SolarPanels extends CustomRelic {

    public static final String ID = "SolarPanels";

    private static final String IMG = "img/relics/SolarPanels.png";

    private static final String IMG_OTL = "img/relics/outline/SolarPanels.png";

    public SolarPanels() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public void onPlayerEndTurn(){
        //每回合开始时触发
        AbstractDungeon.player.heal(2);
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SolarPanels();
    }

}
