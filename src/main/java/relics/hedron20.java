package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.GainEnergyIfDiscardAction;

public class hedron20 extends CustomRelic {
    public static final String ID = "hedron20";
    private static final String IMG = "img/relics_Seles/hedron20.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/hedron20.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public hedron20() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
    }
    @Override
    public void atTurnStart(){
        //每回合开始时触发
        AbstractPlayer p = AbstractDungeon.player;
        int maxhp = p.maxHealth;
        double per_maxhp;
        int num;
        //转换到整数
        per_maxhp =  Math.ceil(maxhp* 0.5);
        num = Double.valueOf(per_maxhp).intValue();
        flash();
        if(p.currentHealth >= num){
            addToBot((AbstractGameAction)new GainEnergyAction(1));
        }else{
            p.heal(2);
        }

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
        return new hedron20();
    }
}
