package relics.Witch;
//黑猫
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import power.LoseMyStryPower;
import power.MysteryPower;

public class BlackCat extends CustomRelic {

    public static final String ID = "BlackCat";

    private static final String IMG = "img/relics/BlackCat.png";

    private static final String IMG_OTL = "img/relics/outline/BlackCat.png";

    public BlackCat() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(YibaMod.ELEMENT)){
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new MysteryPower(p, 1), 1));
            addToBot(new ApplyPowerAction(p, p, new LoseMyStryPower(p, 1), 1));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlackCat();
    }
}
