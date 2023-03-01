
//Dove

package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class Dove extends CustomRelic {
    public static final String ID = "Dove";
    private static final String IMG = "img/relics_Seles/Dove.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/Dove.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    boolean isAcive;
    public Dove() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发

        isAcive = false;
    }
    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        //抽卡时触发
        AbstractPlayer p = AbstractDungeon.player;
        if(!isAcive){
            //判断抽上来的卡是否是诅咒牌
            if(drawnCard.type==AbstractCard.CardType.CURSE){
                //触发消耗
                p.hand.moveToExhaustPile(drawnCard);
                //drawnCard.triggerOnExhaust();
                isAcive = true;
                //添加抽2效果
                addToBot(new DrawCardAction(AbstractDungeon.player, 2));
            }

    }}

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        isAcive = false;
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
        return new Dove();
    }
}