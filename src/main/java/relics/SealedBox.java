package relics;
//被封印的...
import basemod.abstracts.CustomRelic;
import cards.colorless.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class SealedBox extends CustomRelic {

    public static final String ID = "SealedBox";

    private static final String IMG = "img/relics/SealedBox.png";

    private static final String IMG_OTL = "img/relics/outline/SealedBox.png";

    public SealedBox() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void onEquip() {
        AbstractCard card = new SealedLeftArm();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        card = new SealedRightArm();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        card = new SealedLeftFoot();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        card = new SealedRightFoot();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        card = new SealedEkkusu();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SealedBox();
    }


}
