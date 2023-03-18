package relics;

import YibaMod.YibaMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;
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
    }
}
