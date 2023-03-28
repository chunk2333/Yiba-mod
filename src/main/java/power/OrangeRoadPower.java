package power;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class OrangeRoadPower extends AbstractPower {

    public static final String POWER_ID = "OrangeRoadPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("OrangeRoadPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    private static int loseNum;

    public OrangeRoadPower(AbstractCreature owner, int amt, int losenum1) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        loseNum = loseNum + losenum1;
        this.region48 = atlas_self.findRegion("48/MagicFeedbackPower");
        this.region128 = atlas_self.findRegion("128/MagicFeedbackPower");
        this.type = AbstractPower.PowerType.BUFF;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        int random;
        random = AbstractDungeon.cardRandomRng.random(1,4);
        AbstractPlayer p = (AbstractPlayer) this.owner;
        switch (random){
            case 1:
                //力量
                addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.amount), this.amount));
                break;
            case 2:
                //敏捷
                addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.amount), this.amount));
                break;
            case 3:
                //集中
                addToTop(new ApplyPowerAction(p, p, new FocusPower(p, this.amount), this.amount));
                break;
            case 4:
                //元素精通
                addToTop(new ApplyPowerAction(p, p, new MysteryPower(p, this.amount), this.amount));
                break;
            default:
                break;
        }
        //失去
        random = AbstractDungeon.cardRandomRng.random(1,4);
        switch (random){



            case 1:
                //力量
                addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -loseNum), -loseNum));
                break;
            case 2:
                //敏捷
                addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, -loseNum), -loseNum));
                break;
            case 3:
                //集中
                addToTop(new ApplyPowerAction(p, p, new FocusPower(p, -loseNum), -loseNum));
                break;
            case 4:
                //元素精通
                addToTop(new ApplyPowerAction(p, p, new MysteryPower(p, -loseNum), -loseNum));
                break;
            default:
                break;
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[2] + loseNum + DESCRIPTIONS[3];
    }
}
