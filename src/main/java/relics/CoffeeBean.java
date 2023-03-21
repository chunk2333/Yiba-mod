package relics;
//咖啡豆
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import power.CoffeeBeanPower;

public class CoffeeBean extends CustomRelic {

    public static final String ID = "CoffeeBean";

    private static final String IMG = "img/relics/CoffeeBean.png";

    private static final String IMG_OTL = "img/relics/outline/CoffeeBean.png";

    public CoffeeBean() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        //在战斗开始时触发
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
        //给予亢奋能力
        addToBot(new ApplyPowerAction(p, p, new CoffeeBeanPower(p, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CoffeeBean();
    }

}
