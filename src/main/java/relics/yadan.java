package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class yadan extends CustomRelic {
    public static final String ID = "yadan";
    private static final String IMG = "img/relics_Seles/yadan.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/yadan.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public yadan() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发

    }
    @Override
    public void atTurnStart(){
        //每回合开始时触发
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
        //冻蛋代码抄下来的，不明觉厉
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null)
                for (AbstractCard c : reward.cards)
                    onPreviewObtainCard(c);
        }
    }

    public void onPreviewObtainCard(AbstractCard c) {
        //在卡片选择界面时
        onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        //获得卡时
        //判断卡片的颜色，若是无色，将其升级
        if (c.color == AbstractCard.CardColor.COLORLESS && c.canUpgrade() && !c.upgraded)
            //卡片升级
            c.upgrade();
    }
    @Override
    public boolean canSpawn() {
        //可以掉落的层数
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }
    public void onUnequip() {
        //丢弃时触发
    }
    @Override
    public AbstractRelic makeCopy() {
        return new yadan();
    }
}
