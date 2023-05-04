package relics;
//户口本
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HouseholdRegister extends CustomRelic {

    public static final String ID = "HouseholdRegister";

    private static final String IMG = "img/relics/HouseholdRegister.png";

    private static final String IMG_OTL = "img/relics/outline/HouseholdRegister.png";

    public boolean isActive;

    public HouseholdRegister() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        //头顶出现遗物特效
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 3), 5));
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        isActive = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HouseholdRegister();
    }

}
