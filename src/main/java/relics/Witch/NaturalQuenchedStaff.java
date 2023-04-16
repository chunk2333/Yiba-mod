package relics.Witch;
//自然淬炼之杖
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import power.MysteryPower;

public class NaturalQuenchedStaff extends CustomRelic {

    public static final String ID = "NaturalQuenchedStaff";

    private static final String IMG = "img/relics/NaturalQuenchedStaff.png";

    private static final String IMG_OTL = "img/relics/outline/NaturalQuenchedStaff.png";

    public NaturalQuenchedStaff() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {

        int elementCardNum = 0;
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group){
            if(c.hasTag(YibaMod.ELEMENT)){
                elementCardNum += 1;
            }
        }
        if(elementCardNum > 0){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MysteryPower(AbstractDungeon.player, elementCardNum),elementCardNum));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NaturalQuenchedStaff();
    }
}
