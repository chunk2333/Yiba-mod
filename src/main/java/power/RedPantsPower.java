package power;

import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class RedPantsPower extends AbstractPower{

    public static final String POWER_ID = "RedPantsPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("RedPantsPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public RedPantsPower(AbstractCreature owner) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/RedPantsPower");
        this.region128 = atlas_self.findRegion("128/RedPantsPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void onSpecificTrigger() {
        Iterator var1 = this.owner.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.type == PowerType.DEBUFF) {
                flash();
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, p.ID));
            }
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        Iterator var1 = this.owner.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.type == PowerType.DEBUFF) {
                flash();
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, p.ID));
            }
        }
    }


    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

}
