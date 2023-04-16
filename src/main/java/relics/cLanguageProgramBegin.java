package relics;
//魔导书
import basemod.abstracts.CustomRelic;
import cards.element.GeoCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class cLanguageProgramBegin extends CustomRelic {

    public static final String ID = "cLanguageProgramBegin";

    private static final String IMG = "img/relics/cLanguageProgramBegin.png";

    private static final String IMG_OTL = "img/relics/outline/cLanguageProgramBegin.png";

    public cLanguageProgramBegin() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStartPreDraw() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInHandAction(new GeoCard(), 1, false));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new cLanguageProgramBegin();
    }

}
