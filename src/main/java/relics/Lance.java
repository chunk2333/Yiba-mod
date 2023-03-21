package relics;
//极霸矛
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Lance extends CustomRelic {
    public static final String ID = "Lance";

    private static final String IMG = "img/relics/Lance.png";

    private static final String IMG_OTL = "img/relics/outline/Lance.png";

    public boolean isUse = false;

    public Lance() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.isUse = false;
        //持续闪烁
        this.pulse = true;
        //是否黑白
        this.grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        if(card.type== AbstractCard.CardType.ATTACK && !this.isUse){
            AbstractPlayer p = AbstractDungeon.player;
            addToTop(new RelicAboveCreatureAction(p, this));
            flash();
            //对全体敌人造成伤害
            addToBot(new DamageAllEnemiesAction(p, card.damage, card.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            this.isUse = true;
            this.pulse = false;
            this.grayscale = true;
        }
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        this.isUse = false;
        this.grayscale = false;
        this.pulse = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Lance();
    }

}
