package relics;
//木炭，只支持简体中文
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Charcoal extends CustomRelic {

    public static final String ID = "Charcoal";

    private static final String IMG = "img/relics/Charcoal.png";

    private static final String IMG_OTL = "img/relics/outline/Charcoal.png";


    public Charcoal() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Charcoal();
    }
}
