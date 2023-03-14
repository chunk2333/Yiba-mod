package relics;
//酿造台
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BrewingStand extends CustomRelic {
    public static final String ID = "BrewingStand";

    private static final String IMG = "img/relics/BrewingStand.png";

    private static final String IMG_OTL = "img/relics/outline/BrewingStand.png";

    public BrewingStand() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.CLINK);
    }
    @Override
    public void atBattleStart() {
        this.counter += 1;
    }

    @Override
    public void onVictory() {
        if(this.counter == 3){
            flash();
            this.counter = 0;
            //添加一瓶药水到奖励列表
            AbstractDungeon.getCurrRoom().addPotionToRewards(AbstractDungeon.returnRandomPotion());
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BrewingStand();
    }
}