package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;

public class APaTea extends CustomRelic {
    public static final String ID = "APaTea";
    private static final String IMG = "img/relics/APaTea.png";
    private static final String IMG_OTL = "img/relics/outline/APaTea.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public APaTea() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }
    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        //战斗开始时失去10点生命值
        //遗物特效，在头顶显示
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //给力量
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, 5), 5));
        //掉血
        addToBot(new LoseHPAction(p, p, 10));
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
    }
    @Override
    public void onVictory() {
        //在胜利时触发
        //战斗胜利（结束）时，回复10点生命值
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        p.heal(10);
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    public void onEquip() {
        //拾取时触发
    }
    @Override
    public AbstractRelic makeCopy() {
        return new APaTea();
    }
}