package relics;
//孝
import basemod.abstracts.CustomRelic;
import cards.curse.DutifulSon;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Xiao extends CustomRelic {

    public static final String ID = "Xiao";

    private static final String IMG = "img/relics/test.png";

    private static final String IMG_OTL = "img/relics/outline/test.png";

    public Xiao() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
        this.tips.add(new PowerTip(DutifulSon.NAME, DutifulSon.cardStrings.DESCRIPTION));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Xiao();
    }

    @Override
    public void onEquip() {
        //添加两张孝子
        DutifulSon du = new DutifulSon();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(du, (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(du, (Settings.WIDTH / 2), (Settings.HEIGHT / 2)));
    }
}
