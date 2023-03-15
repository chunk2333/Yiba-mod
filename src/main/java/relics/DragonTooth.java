package relics;
//龙牙
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DragonTooth extends CustomRelic {
    public static final String ID = "DragonTooth";

    private static final String IMG = "img/relics/DragonTooth.png";

    private static final String IMG_OTL = "img/relics/outline/DragonTooth.png";

    public DragonTooth() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //力量
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        //敏捷
        addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        //集中
        addToTop(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1));
        //金属化
        addToTop(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DragonTooth();
    }
}
