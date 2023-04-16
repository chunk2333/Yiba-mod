package potions;
//混乱药水
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import power.SpiritualDominationPower;

public class Yiba_ChaoticPotion extends AbstractPotion {

    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("Yiba_ChaoticPotion");

    public static final String POTION_ID = "Yiba_ChaoticPotion";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    public Yiba_ChaoticPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.ENERGY);
        this.isThrown = true;
        this.targetRequired = true;
    }

    @Override
    public void use(AbstractCreature target) {

        addToBot(new ApplyPowerAction(target, target, new SpiritualDominationPower(target, getPotency()),getPotency()));

    }

    @Override
    public void initializeData(){
        //药水描述
        this.potency = getPotency();
        this.description = potionString.DESCRIPTIONS[0] + this.potency + potionString.DESCRIPTIONS[1];
        //this.description = potionString.DESCRIPTIONS[0];
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
        return new Yiba_ChaoticPotion();
    }
}
