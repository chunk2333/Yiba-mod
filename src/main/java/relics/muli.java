package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import YibaMod.YibaMod;

public class muli extends CustomRelic {
    public static final String ID = "muli";
    private static final String IMG = "img/relics/muli.png";
    private static final String IMG_OTL = "img/relics/outline/muli.png";
    boolean isActive;
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public muli() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        boolean isActive=false;
    }
    @Override
    public void atTurnStart(){
        //每回合开始时触发
        if(!isActive){
            this.counter = this.counter + 1;
            if(this.counter==4){
                flash();
                //AbstractPlayer p = AbstractDungeon.player;
                YibaMod.logger.info("muli触发：获得1层缓冲");
                //获得缓冲效果
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, (AbstractPower)new BufferPower(AbstractDungeon.player, 1), 1));
                //遗物特效
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
                this.counter = -1;
                isActive=true;
            }
        }

    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
    }
    @Override
    public void onVictory() {
        //在胜利时触发
        this.counter=0;
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    public void onEquip() {
        //拾取时触发
        this.counter = 0;
    }
    @Override
    public void onUnequip() {
        //丢弃时触发

    }
    @Override
    public AbstractRelic makeCopy() {
        return new muli();
    }
}
