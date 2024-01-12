package relics;
//机关卷轴
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class MechanismScroll extends CustomRelic {
    public static final String ID = "MechanismScroll";

    private static final String IMG = "img/relics/MechanismScroll.png";

    private static final String IMG_OTL = "img/relics/outline/MechanismScroll.png";

    public MechanismScroll() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
