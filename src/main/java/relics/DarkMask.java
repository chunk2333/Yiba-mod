package relics;
//Dark面罩
import actions.ExhaustDrawPileCardAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class DarkMask extends CustomRelic {

    public static final String ID = "DarkMask";

    private static final String IMG = "img/relics/DarkMask.png";

    private static final String IMG_OTL = "img/relics/outline/DarkMask.png";


    public DarkMask() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        //在战斗开始时触发
        addToBot(new ExhaustDrawPileCardAction(2));
        flash();
        this.grayscale = true;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.masterDeck.group.size() > 2;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DarkMask();
    }
}
