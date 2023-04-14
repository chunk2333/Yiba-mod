package relics;
//血袋
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BloodBag extends CustomRelic {

    public static final String ID = "BloodBag";

    private static final String IMG = "img/relics/test.png";

    private static final String IMG_OTL = "img/relics/outline/test.png";

    public BloodBag() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public void onLoseHp(int damageAmount) {

        int random;
        random = AbstractDungeon.relicRng.random(1,100); //随机数
        AbstractPlayer p = AbstractDungeon.player;
        YibaMod.logger.info("血袋：掉血，随机数：" + random);
        if(random <= this.counter){
            p.heal(p.maxHealth);
            this.counter = 1;
        }else {
            this.counter += 1;
        }
    }

    @Override
    public void onEquip() {
        this.counter = 1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BloodBag();
    }
}
