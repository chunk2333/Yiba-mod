package power;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MyCurlUpPower extends AbstractPower {
    public static final String POWER_ID = "Curl Up";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Curl Up");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean triggered = false;

    public MyCurlUpPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "MyCurlUpPower";
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        loadRegion("closeUp");
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (!this.triggered && damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            this.triggered = true;
            addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "MyCurlUpPower"));
        }
        return damageAmount;
    }
}
