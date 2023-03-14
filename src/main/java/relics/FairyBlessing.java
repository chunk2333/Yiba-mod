package relics;
//FairyBlessing
//精灵祝福
import basemod.abstracts.CustomRelic;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class FairyBlessing extends CustomRelic {
    public static final String ID = "FairyBlessing";
    private static final String IMG = "img/relics/FairyBlessing.png";
    private static final String IMG_OTL = "img/relics/outline/FairyBlessing.png";
    private boolean isActive;

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public FairyBlessing() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        this.isActive = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
    }

    @Override
    public void onPlayerEndTurn() {
        //玩家回合结束时触发

    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;

        if(damageAmount>p.currentHealth && !isActive ){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            UpdateStats.logger.info("精灵祝福触发，受到的伤害：" + damageAmount);
            this.isActive=true;
            this.grayscale = true;
            return 0;
        }
        return damageAmount;
    }
    @Override
    public void onVictory() {
        //在胜利时触发
        this.isActive = false;
        this.grayscale = false;
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
        return new FairyBlessing();
    }
}