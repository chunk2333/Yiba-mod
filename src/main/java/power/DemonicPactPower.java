package power;
//恶魔契约power
import actions.MyRegenAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
public class DemonicPactPower extends AbstractPower {

    public static final String POWER_ID = "DemonicPactPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("DemonicPactPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DemonicPactPower(AbstractCreature owner, int heal) {
        this.name = NAME;
        this.ID = "DemonicPactPower";
        this.owner = owner;
        this.amount = heal;
        updateDescription();
        loadRegion("regen");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flashWithoutSound();
        addToTop(new MyRegenAction(this.owner, this.amount));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }
}
