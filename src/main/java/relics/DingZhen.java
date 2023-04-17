package relics;
//异眼丁真
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DingZhen extends CustomRelic implements CustomSavable<String> {

    public static final String ID = "DingZhen";

    private static final String IMG = "img/relics/DingZhen.png";

    private static final String IMG_OTL = "img/relics/outline/DingZhen.png";

    public DingZhen() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atPreBattle() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ConfusionPower(AbstractDungeon.player)));
    }


    @SpirePatch(clz = AbstractDungeon.class, method = "returnRandomRelic")
    public static class fix{
        @SpirePostfixPatch
        public static AbstractRelic Postfix(AbstractRelic.RelicTier tier){
            if(AbstractDungeon.player.hasRelic("DingZhen")){
                YibaMod.logger.info("丁真触发，原来遗物稀有度：" + tier.name());
                tier = returnAllRandomRelicTier();
                YibaMod.logger.info("丁真触发，现在遗物稀有度：" + tier.name());
            }
            return RelicLibrary.getRelic(AbstractDungeon.returnRandomRelicKey(tier)).makeCopy();

        }

        public static AbstractRelic.RelicTier returnAllRandomRelicTier() {
            int roll = AbstractDungeon.relicRng.random(1, 5);

            switch (roll) {
                case 2:
                    return AbstractRelic.RelicTier.UNCOMMON;
                case 3:
                    return AbstractRelic.RelicTier.RARE;
                case 4:
                    return AbstractRelic.RelicTier.SHOP;
                case 5:
                    return AbstractRelic.RelicTier.BOSS;
                case 1:

                default:
                    return AbstractRelic.RelicTier.COMMON;

            }
        }

    }

    @Override
    public String onSave()
    {
        return YiBaHelper.getTempRelic();
    }

    @Override
    public void onLoad(String relicId)
    {
        YiBaHelper.setTempRelic(relicId);
        YibaMod.logger.info("异眼丁真，当前遗物：" + relicId);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DingZhen();
    }
}
