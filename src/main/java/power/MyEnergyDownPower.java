package power;
//运行缓慢
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MyEnergyDownPower extends AbstractPower {
    public static final String POWER_ID = "EnergyDownPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MyEnergyDownPower");

    public MyEnergyDownPower(AbstractCreature owner, int amount, boolean isFasting) {
        this.name = powerStrings.NAME;
        this.ID = "EnergyDownPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        if (isFasting) {
            loadRegion("fasting");
        } else {
            loadRegion("energized_blue");
        }
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    public MyEnergyDownPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        for (int i = 0; i < this.amount; i++)
            sb.append("[E] ");
        if (powerStrings.DESCRIPTIONS[1].isEmpty()) {
            sb.append(LocalizedStrings.PERIOD);
        } else {
            sb.append(powerStrings.DESCRIPTIONS[1]);
        }
        this.description = sb.toString();
    }

    public void atStartOfTurn() {
        addToBot(new LoseEnergyAction(this.amount));
        flash();
    }
}
