package relics;
//猫猫
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Cat extends CustomRelic {

    public static final String ID = "Cat";

    private static final String IMG = "img/relics/Cat.png";

    private static final String IMG_OTL = "img/relics/outline/Cat.png";

    public Cat() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.SOLID);
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
        //移除所有debuff
        addToBot(new RemoveDebuffsAction(p));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Cat();
    }
}
