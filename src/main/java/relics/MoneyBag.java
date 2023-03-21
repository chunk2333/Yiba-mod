package relics;
//带有血渍的钱袋
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class MoneyBag extends CustomRelic {

    public static final String ID = "MoneyBag";

    private static final String IMG = "img/relics/MoneyBag.png";

    private static final String IMG_OTL = "img/relics/outline/MoneyBag.png";

    public MoneyBag() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        //拾取时触发
        AbstractPlayer p = AbstractDungeon.player;
        p.gainGold(150);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MoneyBag();
    }

    @Override
    public boolean canSpawn() {
        return ((Settings.isEndless || AbstractDungeon.floorNum <= 48) &&
                !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom));
    }

}
