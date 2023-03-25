package relics;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.KeywordStrings;
import relics.abstracrt.ElementRelic;

public class TestTriggerElement extends ElementRelic {

    public static final String ID = "TestTriggerElement";

    private static final String IMG = "img/relics/test.png";

    private static final String IMG_OTL = "img/relics/outline/test.png";


    public TestTriggerElement() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public void triggerElement(String reactionName){
        show();
        YibaMod.logger.info("[triggerElement] 有元素反应触发：" + reactionName);
        if(YiBaHelper.getLastTriggerElementName() != null){
            YibaMod.logger.info("[triggerElement] 上次触发的元素反应：：" + YiBaHelper.getLastTriggerElementName());
            YibaMod.logger.info("[triggerElement] 上次触发的完整元素反应：：" + YiBaHelper.getLastTriggerElementFinalName());
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
