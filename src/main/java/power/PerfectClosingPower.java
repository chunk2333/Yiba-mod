package power;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PerfectClosingPower extends AbstractPower {

    public static final String POWER_ID = "PerfectClosingPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("PerfectClosingPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PerfectClosingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "PerfectClosingPower";
        this.owner = owner;
        this.amount = amount;
        loadRegion("doubleDamage");
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK){
            flash();
            addToBot(new ReducePowerAction(this.owner, this.owner, "PerfectClosingPower", 1));
        }
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "PerfectClosingPower"));
    }


    @Override
    public void updateDescription() {
        if (this.amount == 4){
            this.description = DESCRIPTIONS[0] + "120%" + DESCRIPTIONS[1];
            return;
        }
        if (this.amount == 3){
            this.description = DESCRIPTIONS[0] + "150%" + DESCRIPTIONS[1];
            return;
        }
        if (this.amount == 2){
            this.description = DESCRIPTIONS[0] + "200%" + DESCRIPTIONS[1];
            return;
        }
        if (this.amount == 1){
            this.description = DESCRIPTIONS[0] + "300%" + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + "100%" + DESCRIPTIONS[1];
        }

    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL){
            if (this.amount == 4){
                return damage * 1.2F;
            }
            if (this.amount == 3){
                return damage * 1.5F;
            }
            if (this.amount == 2){
                return damage * 2.0F;
            }
            if (this.amount == 1){
                return damage * 3.0F;
            }
        }
        return damage;
    }
}
