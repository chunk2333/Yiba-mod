package relics;
//海豹
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Seals extends CustomRelic {

    public static final String ID = "Seals";

    private static final String IMG = "img/relics/Seals.png";

    private static final String IMG_OTL = "img/relics/outline/Seals.png";

    public Seals() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        //战斗开始时，抽卡时触发
        addToBot(new BetterDrawPileToHandAction(1));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Seals();
    }

}
