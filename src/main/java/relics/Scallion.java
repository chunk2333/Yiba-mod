package relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;

public class Scallion extends CustomRelic {
    public static final String ID = "Scallion";
    private static final String IMG = "img/relics/Scallion.png";
    private static final String IMG_OTL = "img/relics/outline/Scallion.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public Scallion() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        //遗物特效，在头顶显示
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //给力量
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 2), 2));

    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发

    }

    @Override
    public void onVictory() {
        //在胜利时触发

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    //拾取时触发
    public void onEquip() {

    }
    @Override
    public AbstractRelic makeCopy() {
        return new Scallion();
    }
}
