package power;
//VR给的能力
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AtTurnStartGiveEnegryPower extends AbstractPower {
    public static final String POWER_ID = "AtTurnStartGiveEnegryPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("AtTurnStartGiveEnegryPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AtTurnStartGiveEnegryPower(AbstractCreature owner, int amt) {
        super();
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        if (this.amount >= 999)
            this.amount = 999;
        updateDescription();
        loadRegion("energized_blue");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999)
            this.amount = 999;
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onEnergyRecharge() {
        flash();
        AbstractDungeon.player.gainEnergy(this.amount);
    }
}