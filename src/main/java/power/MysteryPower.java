package power;
//元素精通
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MysteryPower extends AbstractPower {
    public static final String POWER_ID = "MysteryPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("MysteryPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;
    public MysteryPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        updateDescription();
        this.ID = POWER_ID ;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/DoubleDamage");
        this.region128 = atlas_self.findRegion("128/DoubleDamage");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void atStartOfTurn(){
        //回合开始时
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {

    }
}