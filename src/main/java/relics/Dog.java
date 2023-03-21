package relics;
//狗狗
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Dog extends CustomRelic {

    public static final String ID = "Dog";

    private static final String IMG = "img/relics/Dog.png";

    private static final String IMG_OTL = "img/relics/outline/Dog.png";

    public Dog() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void onPlayerEndTurn() {
        //玩家回合结束时触发
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        //遗物头顶特效
        addToTop(new RelicAboveCreatureAction(p, this));
        //回复1血
        p.heal(1);
        //获得1点力量
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Dog();
    }
}
