package power;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EntanglePower;

public class NextTurnCanNotPlayAttackCardPower extends AbstractPower {
    public static final String POWER_ID = "Entangled";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("NextTurnCanNotPlayAttackCardPower");

    public NextTurnCanNotPlayAttackCardPower(AbstractCreature owner) {
        this.name = powerStrings.NAME;
        this.ID = "NextTurnCanNotPlayAttackCardPower";
        this.owner = owner;
        this.amount = 1;
        updateDescription();
        loadRegion("entangle");
        this.isTurnBased = true;
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_ENTANGLED", 0.05F);
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer){
            addToBot(new ApplyPowerAction(this.owner,this.owner,new EntanglePower(this.owner)));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }
}
