package relics;
//鸽子

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Dove extends CustomRelic {

    public static final String ID = "Dove";

    private static final String IMG = "img/relics/Dove.png";

    private static final String IMG_OTL = "img/relics/outline/Dove.png";

    boolean isActive;

    public Dove() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        isActive = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        //抽卡时触发
        AbstractPlayer p = AbstractDungeon.player;
        if (!isActive) {
            //判断抽上来的卡是否是诅咒牌
            if (drawnCard.type == AbstractCard.CardType.CURSE) {
                //触发消耗
                p.hand.moveToExhaustPile(drawnCard);
                //drawnCard.triggerOnExhaust();
                isActive = true;
                //添加抽2效果
                addToBot(new DrawCardAction(AbstractDungeon.player, 2));
            }
        }
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        isActive = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Dove();
    }

}
