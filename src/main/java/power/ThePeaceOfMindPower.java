package power;
//明镜止水能力
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class ThePeaceOfMindPower extends AbstractPower {
    public static final String POWER_ID = "ThePeaceOfMindPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ThePeaceOfMindPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public ThePeaceOfMindPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/ThePeaceOfMindPower");
        this.region128 = atlas_self.findRegion("128/ThePeaceOfMindPower");
        this.type = AbstractPower.PowerType.BUFF;
    }
    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
        } else {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
        }
    }
    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        //判断是否为平静姿态
        if (!oldStance.ID.equals(newStance.ID) && newStance.ID.equals("Calm")) {
            flash();
            addToBot(new DrawCardAction(this.owner, this.amount));
        }
    }
}