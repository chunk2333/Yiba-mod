package relics.Witch;
//异次元口袋
import Tools.YiBaHelper;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class AlternateDimensionalPocket extends CustomRelic {

    public static final String ID = "AlternateDimensionalPocket";

    private static final String IMG = "img/relics/AlternateDimensionalPocket.png";

    private static final String IMG_OTL = "img/relics/outline/AlternateDimensionalPocket.png";

    public AlternateDimensionalPocket() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();
        AbstractCard card = YiBaHelper.getRandomElementCard();
        card.cost = 0;
        card.current_x = -1000.0F * Settings.xScale;
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, true));
        this.grayscale = true;
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        this.grayscale = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AlternateDimensionalPocket();
    }
}
