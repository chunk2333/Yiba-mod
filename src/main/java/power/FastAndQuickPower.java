package power;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FastAndQuickPower extends AbstractPower {

    public static final String POWER_ID = "FastAndQuickPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("FastAndQuickPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FastAndQuickPower(AbstractCreature owner, int amt) {
        super();
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        loadRegion("forcefield");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        flash();
        addToBot(new GainBlockAction(AbstractDungeon.player, this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

}
