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

public class MagicFeedbackPower extends AbstractPower {

    public static final String POWER_ID = "MagicFeedbackPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MagicFeedbackPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public MagicFeedbackPower(AbstractCreature owner) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/MagicFeedbackPower");
        this.region128 = atlas_self.findRegion("128/MagicFeedbackPower");
    }

    @Override
    public void atStartOfTurn() {
        flash();
        int random;
        random = AbstractDungeon.cardRandomRng.random(1,3);
        flash();
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!mo.isDead){
                switch (random){
                    case 1:
                        addToBot(new ApplyPowerAction(mo, mo, new PyroPower(mo, YiBaHelper.getPlayerMystery()), 1));
                        break;
                    case 2:
                        addToBot(new ApplyPowerAction(mo, mo, new HydroPower(mo, YiBaHelper.getPlayerMystery()), 1));
                        break;
                    case 3:
                        addToBot(new ApplyPowerAction(mo, mo, new GeoPower(mo, YiBaHelper.getPlayerMystery()), 1));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
