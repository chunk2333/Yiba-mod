package relics;
//黑手
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BlackHand extends CustomRelic {
    public static final String ID = "BlackHand";

    private static final String IMG = "img/relics/BlackHand.png";

    private static final String IMG_OTL = "img/relics/outline/BlackHand.png";

    public BlackHand() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        CardCrawlGame.sound.playA("BlackHand", 0F);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new BlackHand();
    }
}
