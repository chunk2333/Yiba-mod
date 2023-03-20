package relics;
//红裤衩
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import power.RedPantsPower;

public class RedPants extends CustomRelic {
    public static final String ID = "RedPants";

    private static final String IMG = "img/relics/RedPants.png";

    private static final String IMG_OTL = "img/relics/outline/RedPants.png";

    public RedPants() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        //在战斗开始时触发
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效

        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 99), 99));

        addToBot(new ApplyPowerAction(p, p, new RedPantsPower(p)));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RedPants();
    }
}
