package relics;
//吸血鬼节仗
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class VampireFestivalStick extends CustomRelic {

    public static final String ID = "VampireFestivalStick";

    private static final String IMG = "img/relics/VampireFestivalStick.png";

    private static final String IMG_OTL = "img/relics/outline/VampireFestivalStick.png";

    public VampireFestivalStick() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        flash();
        AbstractDungeon.player.heal((int) (damageAmount * 0.1));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new VampireFestivalStick();
    }
}
