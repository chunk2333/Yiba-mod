package relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class Repeater extends CustomRelic {
    public static final String ID = "Repeater";
    private static final String IMG = "img/relics/Repeater.png";
    private static final String IMG_OTL = "img/relics/outline/Repeater.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    boolean isGive;
    public Repeater() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }
    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        isGive = false;
    }
    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        //玩家已经获得过格挡时触发
        if (isGive) {
            isGive = false;
        } else {
            flash();
            //给玩家1点格挡
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 1));
            isGive = true;
        }
        return MathUtils.floor(blockAmount);
    }
    @Override
    public void atTurnStart(){
        isGive = false;
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
    public void onEquip() {
        //拾取时触发
    }
    @Override
    public AbstractRelic makeCopy() {
        return new Repeater();
    }
}