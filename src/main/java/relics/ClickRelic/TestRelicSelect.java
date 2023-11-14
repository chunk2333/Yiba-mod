package relics.ClickRelic;

import YibaMod.YibaMod;
import actions.DianAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.*;
import relics.abstracrt.ClickableRelic;

import java.util.ArrayList;
import java.util.List;

public class TestRelicSelect extends ClickableRelic {
    public static final String ID = "TestRelicSelect";

    private static final String IMG = "img/relics/test.png";

    private static final String IMG_OTL = "img/relics/outline/test.png";

    public TestRelicSelect() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onRightClick(){
        List<AbstractRelic> testRelics = new ArrayList<>();
        testRelics.add(new RedSkull());
        testRelics.add(new SneckoSkull());
        testRelics.add(new DataDisk());
        testRelics.add(new TeardropLocket());
        List<AbstractRelic> selectedRelics = new ArrayList<>();
        List<List<AbstractRelic>> relicGroup = new ArrayList<>();
        relicGroup.add(testRelics);
        addRelic(relicGroup, selectedRelics, 0);
        YibaMod.logger.info("你点击了：TestRelicSelect遗物");
    }

    private void addRelic(List<List<AbstractRelic>> relics, List<AbstractRelic> selectedRelics, int index) {
        if (index == relics.size()) {
            selectedRelics.forEach(AbstractRelic::instantObtain);
            YibaMod.relicSelectScreen.isDone = true;
            return;
        }
        YibaMod.relicSelectScreen.open(relics.get(index), relic -> {
            selectedRelics.add(relic);
            YibaMod.relicSelectScreen.isDone = true;
        },() -> addRelic(relics, selectedRelics, index + 1));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TestRelicSelect();
    }

}
