package relics;
//瓶装异教徒面具
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;
import YibaMod.YibaMod;

public class BottledCultistMask extends CustomRelic {
    public static final String ID = "BottledCultistMask";

    private static final String IMG = "img/relics/BottledCultistMask.png";

    private static final String IMG_OTL = "img/relics/outline/BottledAir.png";

    public BottledCultistMask() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        flash();
        //头顶出现遗物特效
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //给予随机诅咒
        addToBot(new MakeTempCardInDrawPileAction(getRadomCurseCard(), 1, true, true));
    }

    public AbstractCard getRadomCurseCard(){
        //取随机诅咒牌
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.curseCardPool.group) {
            if(c.type== AbstractCard.CardType.CURSE){
                list.add(c);
            }
        }
        YibaMod.logger.info("全部诅咒牌数："+ (list.size() - 1));
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
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
        return new BottledCultistMask();
    }
}