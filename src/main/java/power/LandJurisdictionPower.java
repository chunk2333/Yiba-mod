package power;

import Tools.YiBaHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LandJurisdictionPower extends AbstractPower {

    public static final String POWER_ID = "LandJurisdictionPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("LandJurisdictionPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public LandJurisdictionPower(AbstractCreature owner) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID ;
        this.owner = owner;
        //this.amount = 1;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/LandJurisdictionPower");
        this.region128 = atlas_self.findRegion("128/LandJurisdictionPower");
    }

    @Override
    public void atStartOfTurn(){
        //回合开始时
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, mo, new GeoPower(mo, YiBaHelper.getPlayerMystery()), 1));
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }
}
