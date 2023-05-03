package potions;
//精简药水
import actions.DeletePotionAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.localization.EventStrings;

public class DeletePotion extends AbstractPotion {

    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("DeletePotion");

    public static final String POTION_ID = "DeletePotion";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Back to Basics");

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    public DeletePotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.CARD, PotionColor.WEAK);

        this.isThrown = false;
    }

    @Override
    public void use(AbstractCreature target) {
        addToBot(new DeletePotionAction(getPotency()));
    }

    @Override
    public void initializeData(){
        this.potency = getPotency();
        this.description = potionString.DESCRIPTIONS[0] + this.potency + potionString.DESCRIPTIONS[1];
        this.name = potionString.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new DeletePotion();
    }

}