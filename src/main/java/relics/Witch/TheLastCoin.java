package relics.Witch;
//最后的一枚硬币
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import relics.abstracrt.ElementRelic;

import java.util.concurrent.ThreadLocalRandom;

public class TheLastCoin extends ElementRelic {

    public static final String ID = "TheLastCoin";

    private static final String IMG = "img/relics/TheLastCoin.png";

    private static final String IMG_OTL = "img/relics/outline/TheLastCoin.png";

    public TheLastCoin() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void triggerElement(String reactionName){
        show();

        AbstractDungeon.player.gainGold(3);

        int randomNumber = ThreadLocalRandom.current().nextInt(1, 100);

        if(randomNumber <= 5){
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.effectList.add(new RainingGoldEffect(5, true));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
