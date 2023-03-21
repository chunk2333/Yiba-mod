package relics;
//复读机
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class Repeater extends CustomRelic {

    public static final String ID = "Repeater";

    private static final String IMG = "img/relics/Repeater.png";

    private static final String IMG_OTL = "img/relics/outline/Repeater.png";

    boolean isGive;

    public Repeater() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        isGive = false;
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        //玩家已经获得过格挡时触发
        if (isGive) {
            isGive = false;
        } else {
            flash();
            //给玩家1点格挡
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 1));
            isGive = true;
        }
        return MathUtils.floor(blockAmount);
    }

    @Override
    public void atTurnStart(){
        isGive = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Repeater();
    }

}
