//PhantomHand
package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class PhantomHand extends CustomRelic {
    public static final String ID = "PhantomHand";
    private static final String IMG = "img/relics_Seles/PhantomHand.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/PhantomHand.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    boolean isActive;
    private boolean disabledUntilEndOfTurn = false;
    private boolean canDraw = false;
    public PhantomHand() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }
    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        isActive=true;
        beginLongPulse(); //长时间闪烁

    }
    @Override
    public void atPreBattle() {
        this.canDraw = false;
    }
    @Override
    public void atTurnStart() {
        this.canDraw = true;
        this.disabledUntilEndOfTurn = false;
    }
    @Override
    public void onRefreshHand() {
        if(isActive){
            //判断手牌是否彻底为空
            if (AbstractDungeon.actionManager.actions.isEmpty() && AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded && this.canDraw &&
                    !AbstractDungeon.player.hasPower("No Draw") && !AbstractDungeon.isScreenUp)
                if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !this.disabledUntilEndOfTurn && (
                        AbstractDungeon.player.discardPile.size() > 0 || AbstractDungeon.player.drawPile.size() > 0)) {
                    //遗物闪一下
                    flash();
                    //遗物头顶特效
                    addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    //抽卡
                    addToBot(new DrawCardAction(AbstractDungeon.player, 5));
                    isActive=false;
                    stopPulse();//结束闪烁
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
        this.grayscale = false;
        stopPulse();//结束闪烁
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
        return new PhantomHand();
    }
}