package relics;
//祭礼残章
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SacrificialFragments extends CustomRelic {

    public static final String ID = "SacrificialFragments";

    private static final String IMG = "img/relics/SacrificialFragments.png";

    private static final String IMG_OTL = "img/relics/outline/SacrificialFragments.png";

    private boolean isActive;

    public SacrificialFragments() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        this.isActive = false;
        beginLongPulse(); //长时间闪烁
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        if(!this.isActive && card.type == AbstractCard.CardType.POWER && card.cost > 0){
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            p.gainEnergy(card.cost);
            this.isActive = true;
            this.grayscale = true;
            stopPulse();//结束闪烁
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        this.isActive = false;
        this.grayscale = false;

    }

    @Override
    public void onEquip() {
        //拾取时触发
        this.isActive = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SacrificialFragments();
    }

}
