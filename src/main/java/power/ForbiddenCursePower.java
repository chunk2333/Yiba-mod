package power;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ForbiddenCursePower extends AbstractPower {
    public static final String POWER_ID = "ForbiddenCursePower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ForbiddenCursePower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    private boolean Upgrade;

    private int amountNoUpgrade = 0;

    public ForbiddenCursePower(AbstractCreature owner, int amount, boolean upgrade) {
        this.name = NAME;
        this.ID = "ForbiddenCursePower";
        this.owner = owner;
        this.amount = amount;
        this.Upgrade = upgrade;
        if (!this.Upgrade){
            this.amountNoUpgrade = this.amount;
        }
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.region48 = atlas_self.findRegion("48/CoffeeBeanPower");
        this.region128 = atlas_self.findRegion("128/CoffeeBeanPower");
        updateDescription();
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK){
            flash();
            if (this.amount != 0){
                addToBot(new ReducePowerAction(this.owner, this.owner, "ForbiddenCursePower", 1));
            } else {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "ForbiddenCursePower"));
            }
        }
    }

    @Override
    public void atEndOfRound() {
        if (!this.Upgrade){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "ForbiddenCursePower"));
        } else {
            if ((this.amount - this.amountNoUpgrade) >= 0){
                addToBot(new ReducePowerAction(this.owner, this.owner, "ForbiddenCursePower", this.amountNoUpgrade));
            } else {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "ForbiddenCursePower"));
            }
        }

    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL){
            return damage * 2.0F;
        }
        return damage;
    }
}
