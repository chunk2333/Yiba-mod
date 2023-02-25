package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;

public class fafa extends CustomRelic {
    public static final String ID = "fafa";
    private static final String IMG = "img/relics_Seles/fafa.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/fafa.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public fafa() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发

    }
    @Override
    public void atTurnStart(){
        //每回合开始时触发
        this.counter++;
        if(this.counter==4){
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            UpdateStats.logger.info("fafa触发：失去1费");
            addToBot((AbstractGameAction)new LoseEnergyAction(1));
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
            this.counter=0;
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
    //拾取时触发
    public void onEquip() {
        //拾取时触发
        AbstractDungeon.player.energy.energyMaster++;
    }
    @Override
    public void onUnequip() {
        //丢弃时触发
        AbstractDungeon.player.energy.energyMaster--;
    }
    @Override
    public AbstractRelic makeCopy() {
        return new fafa();
    }
}
