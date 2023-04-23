package relics;
//空月祝福
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BlessingOfTheWelkinMoon extends CustomRelic {

    public static final String ID = "BlessingOfTheWelkinMoon";

    private static final String IMG = "img/relics/BlessingOfTheWelkinMoon.png";

    private static final String IMG_OTL = "img/relics/outline/BlessingOfTheWelkinMoon.png";

    public BlessingOfTheWelkinMoon() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void onVictory() {
        flash();
        AbstractDungeon.player.gainGold(30);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlessingOfTheWelkinMoon();
    }
}
