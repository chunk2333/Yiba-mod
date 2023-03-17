package relics;
//鸭蛋
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

public class Yadan extends CustomRelic {
    public static final String ID = "yadan";

    private static final String IMG = "img/relics/yadan.png";

    private static final String IMG_OTL = "img/relics/outline/yadan.png";

    public Yadan() {
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
    public void onEquip() {
        //拾取时触发
        //冻蛋代码抄下来的，不明觉厉
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null)
                for (AbstractCard c : reward.cards)
                    onPreviewObtainCard(c);
        }
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        //在卡片选择界面时
        onObtainCard(c);
    }

    @Override
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

    @Override
    public void onUnequip() {
        //丢弃时触发
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Yadan();
    }
}
