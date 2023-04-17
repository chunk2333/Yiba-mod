package power;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LoseMyStryPower extends AbstractPower {
    public static final String POWER_ID = "LoseMyStryPower";

    private static final PowerStrings powerStrings;

    public static final String NAME;

    public static final String[] DESCRIPTIONS;

    public LoseMyStryPower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = "LoseMyStryPower";
        this.owner = owner;
        this.amount = newAmount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
        this.loadRegion("flex");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new MysteryPower(this.owner, -this.amount), -this.amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "LoseMyStryPower"));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("LoseMyStryPower");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
