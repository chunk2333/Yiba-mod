//cranium
package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Cranium extends CustomRelic {
    public static final String ID = "Cranium";
    private static final String IMG = "img/relics/Cranium.png";
    private static final String IMG_OTL = "img/relics/outline/Cranium.png";

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public Cranium() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
    }
    @Override

    public void onManualDiscard() {
        //丢弃卡片时触发
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DrawCardAction(AbstractDungeon.player, 1));

    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发

    }
    @Override
    public void onVictory() {
        //在胜利时触发:清楚激活
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
        return new Cranium();
    }
}
