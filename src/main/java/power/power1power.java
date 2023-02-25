package power;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class power1power extends AbstractPower {
    public static final String POWER_ID = "power1power";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("power1power");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public power1power(AbstractCreature owner, int amt) {
        this.name = NAME;
        this.description = DESCRIPTIONS[0]+ amt + DESCRIPTIONS[1];
        this.ID = "power1power";
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        loadRegion("ai");
        this.type = AbstractPower.PowerType.BUFF;
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
    public void atStartOfTurn(){
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot((AbstractGameAction)new HealAction((AbstractCreature)p, (AbstractCreature)p, this.amount));
    }

}
