package relics;
//焦灼的魔女帽
import basemod.abstracts.CustomRelic;
import cards.curse.LavawalkersTorment;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class WitchsScorchingHat extends CustomRelic {
    public static final String ID = "WitchsScorchingHat";

    private static final String IMG = "img/relics/WitchsScorchingHat.png";

    private static final String IMG_OTL = "img/relics/outline/WitchsScorchingHat.png";

    public WitchsScorchingHat() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        //拾取时触发
        AbstractDungeon.player.energy.energyMaster++;
        //添加一张渡火者的煎熬
        LavawalkersTorment la = new LavawalkersTorment();
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.addToBottom(la.makeCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, this.DESCRIPTIONS[1]);
    }

    @Override
    public void onUnequip() {
        //丢弃时触发
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WitchsScorchingHat();
    }
}
