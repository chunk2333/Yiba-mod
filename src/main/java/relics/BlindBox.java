package relics;
//盲盒
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import actions.LoseRelicAction;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import actions.GetRandomRelicAction;

public class BlindBox extends CustomRelic implements CustomSavable<String> {

    public static final String ID = "BlindBox";

    private static final String IMG = "img/relics/test.png";

    private static final String IMG_OTL = "img/relics/outline/test.png";

    private AbstractRelic abstractRelic;

    private static String nowRelicId;

    public BlindBox() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        if(YiBaHelper.getBlindBoxRelic() != null){
            YibaMod.logger.info(YiBaHelper.getBlindBoxRelic());
            addToTop(new LoseRelicAction(YiBaHelper.getBlindBoxRelic()));
        }
        flash();
        addToBot(new GetRandomRelicAction());
        //YibaMod.logger.info(YiBaHelper.getBlindBoxRelic());

    }

    @Override
    public void onEquip() {
        RussianDolls.wasLoad = true;
    }

    @Override
    public void onVictory() {

    }

    @Override
    public String onSave()
    {
        return YiBaHelper.getBlindBoxRelic();
    }

    @Override
    public void onLoad(String relicId)
    {
        YiBaHelper.setBlindBoxRelic(relicId);
        YibaMod.logger.info("盲盒载入，当前遗物：" + relicId);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlindBox();
    }

}
