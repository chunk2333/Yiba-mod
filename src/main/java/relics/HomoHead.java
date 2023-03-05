package relics;

import basemod.abstracts.CustomRelic;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import cards.*;
import pathes.SoundPlay;

public class HomoHead extends CustomRelic {
    public static final String ID = "HomoHead";
    private static final String IMG = "img/relics_Seles/HomoHead.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/HomoHead.png";
    SoundPlay s =new SoundPlay();


    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public HomoHead() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);

    }

    @Override
    public void atBattleStart() {
        //TempMusic tempMusic = new TempMusic("HomoVoice.ogg", true, true);
        //在战斗开始时触发
        AbstractCard card;
        int random;
        random = AbstractDungeon.relicRng.random(1,3); //随机数
        UpdateStats.logger.info("homo头随机数：" + random);
        switch (random){
            case 1 :
                card = new snowman();
                break;
            case 2 :
                card = new desire();
                break;
            case 3:
                card = new DutifulSon();
                break;
            default :
                card = new snowman();
        }
        flash();
        //遗物特效，在头顶显示
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        //CardCrawlGame.sound.
        addToBot(new TalkAction(true, "哼！哼！哼！啊啊啊啊啊啊啊啊啊啊啊啊！！！！", 1.0F, 2.0F));
        //s.playV("HomoVoice",10);
        //s.adjustVolume("HomoVoice",10,10);
        int random_play;
        random_play = AbstractDungeon.relicRng.random(1,100); //随机数
        if(random_play<=5){
            s.play("HomoVoice",true);
        }
        if(random_play>5 && random_play<=10){
            s.play("HomoVoice",true);
        }
        //往抽牌堆里塞一张“食雪汉”诅咒
        addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true));

    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发

    }
    @Override
    public void atTurnStart() {
        //回合开始时触发
        //flash();
        //AbstractPlayer p = AbstractDungeon.player;
        //loseHP
        //addToBot(new LoseHPAction(p, p, 1));
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
        return new HomoHead();
    }
}
