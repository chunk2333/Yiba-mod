package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;

public class Countdown extends CustomRelic {
    public static final String ID = "Countdown";
    private static final String IMG = "img/relics/Countdown.png";
    private static final String IMG_OTL = "img/relics/outline/Countdown.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public Countdown() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        //角色对话泡
        addToBot(new TalkAction(true, "一切， #r~终将毁灭~~~", 1.0F, 2.0F));
    }
    @Override
    public void atTurnStart(){
        //每回合开始时触发
        this.counter = this.counter + 1;
        AbstractPlayer p = AbstractDungeon.player;
        if(this.counter>=12){
            flash();
            //调试出到控制台的内容
            UpdateStats.logger.info("终焉倒计时触发：对所有怪物造成int类型上限伤害");
            //遗物触发特效
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
            //人物说话气泡
            addToBot(new TalkAction(true, "一切， #r@已经结束~~~~~@", 1.5F, 2.0F));
            //华丽收场特效
            addToBot(new VFXAction(new GrandFinalEffect(), 1.0F));
            //社保特效
            addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.7F));
            //对所有敌人造成伤害
            addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(2147483647, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        }else {
            //播放钟表声音
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
            //边框金色闪烁效果
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
            //老头表buff触发特效
            AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
            if(this.counter<=9 && this.counter!=1){
                addToBot(new TalkAction(true, "#r"+String.valueOf(this.counter)+"......", 0.8F, 2.0F));
            }
            if(this.counter==10){
                addToBot(new TalkAction(true, "#r@末日临近~~~@", 1.5F, 2.0F));
            }
            if(this.counter==11){
                addToBot(new TalkAction(true, "#r@终焉将至!!!@", 1.5F, 2.0F));
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
        this.counter = 0;
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    public void onEquip() {
        this.counter = 0;
    }
    @Override
    public void onUnequip() {
        //丢弃时触发
    }
    @Override
    public AbstractRelic makeCopy() {
        return new Countdown();
    }
}
