package relics;
//”时“
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Time extends CustomRelic {
    public static final String ID = "Time";
    private static final String IMG = "img/relics_Seles/test.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/test.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public Time() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        //在战斗开始时触发
        flash();
        //头顶出现遗物特效
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //遇见整个卡组
        addToBot(new ScryAction(AbstractDungeon.player.masterDeck.group.size()));
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

    }
    @Override
    public AbstractRelic makeCopy() {
        return new Time();
    }
}