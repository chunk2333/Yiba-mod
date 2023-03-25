package power;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class TheSagePower extends AbstractPower {

    public static final String POWER_ID = "TheSagePower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TheSagePower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public TheSagePower(AbstractCreature owner) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID ;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/TheSagePower");
        this.region128 = atlas_self.findRegion("128/TheSagePower");
    }

    @Override
    public void atStartOfTurn(){
        flash();
        addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
        addToBot(new LoseHPAction(this.owner, this.owner, 99999));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "TheSagePower"));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }
}
