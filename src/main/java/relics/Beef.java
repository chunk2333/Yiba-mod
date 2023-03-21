package relics;
//战斧牛排
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Beef extends CustomRelic {

    public static final String ID = "beef";

    private static final String IMG = "img/relics/beef.png";

    private static final String IMG_OTL = "img/relics/outline/beef.png";

    public Beef() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
        //打出一张牌回复1生命
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new RelicAboveCreatureAction(p, this));
        p.heal(1);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Beef();
    }

}
