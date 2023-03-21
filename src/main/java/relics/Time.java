package relics;
//”时“
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Time extends CustomRelic {

    public static final String ID = "Time";

    private static final String IMG = "img/relics/Time.png";

    private static final String IMG_OTL = "img/relics/outline/Time.png";

    public Time() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        //在战斗开始时触发
        flash();
        //头顶出现遗物特效
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //遇见整个卡组
        addToBot(new ScryAction(AbstractDungeon.player.masterDeck.group.size()));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Time();
    }

}
