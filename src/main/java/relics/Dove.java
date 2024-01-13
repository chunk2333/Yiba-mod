package relics;
//鸽子
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
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
        isActive = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        AbstractPlayer p = AbstractDungeon.player;
        if (!isActive) {
            //判断抽上来的卡是否是诅咒牌
            if (drawnCard.type == AbstractCard.CardType.CURSE) {
                flash();
                this.grayscale = true;
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                //触发消耗
                p.hand.moveToExhaustPile(drawnCard);
                isActive = true;
                //抽2卡牌
                addToBot(new DrawCardAction(AbstractDungeon.player, 2));
            }
        }
    }

    @Override
    public void onVictory() {
        isActive = false;
        this.grayscale = false;
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
