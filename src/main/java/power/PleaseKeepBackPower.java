package power;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PleaseKeepBackPower extends AbstractPower {

    public static final String POWER_ID = "PleaseKeepBackPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("PleaseKeepBackPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas;

    public static TextureAtlas atlas_self;

    public PleaseKeepBackPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/MagicLoopPower");
        this.region128 = atlas_self.findRegion("128/MagicLoopPower");
        this.type = AbstractPower.PowerType.DEBUFF;
    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

        if(power.ID.equals("HydroPower") || power.ID.equals("PyroPower") || power.ID.equals("GeoPower")){
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, this.amount, Settings.FAST_MODE));
        }
    }


    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

}
