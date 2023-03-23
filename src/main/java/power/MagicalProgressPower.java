package power;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import power.abstracrt.ElementPower;

public class MagicalProgressPower extends ElementPower {

    public static final String POWER_ID = "MagicalProgressPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MagicalProgressPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas;

    public static TextureAtlas atlas_self;

    public MagicalProgressPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/MagicalProgressPower");
        this.region128 = atlas_self.findRegion("128/MagicalProgressPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void triggerElement(String reactionName){
        show();
        addToBot(new GainBlockAction(this.owner, this.amount, Settings.FAST_MODE));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

}

