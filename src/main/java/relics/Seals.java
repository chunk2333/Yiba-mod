package relics;
//海豹

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Seals extends CustomRelic {
    public static final String ID = "Seals";
    private static final String IMG = "img/relics_Seles/Seals.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/Seals.png";

    public Seals() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        //战斗开始时，抽卡时触发
        addToBot(new BetterDrawPileToHandAction(1));
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
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
        return new Seals();
    }
}