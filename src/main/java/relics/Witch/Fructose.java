package relics.Witch;
//果儿糖
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import power.MysteryPower;

public class Fructose extends CustomRelic {

    public static final String ID = "Fructose";

    private static final String IMG = "img/relics/Fructose.png";

    private static final String IMG_OTL = "img/relics/outline/Fructose.png";

    public Fructose() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractPlayer p = AbstractDungeon.player;
        if(card.hasTag(YibaMod.ELEMENT)){
            this.counter += 1;
            if(this.counter ==3 ){
                flash();
                addToBot(new RelicAboveCreatureAction(p, this));
                addToBot(new ApplyPowerAction(p, p, new MysteryPower(p, 1), 1));
                this.counter = 0;
            }
        }
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Fructose();
    }

}
