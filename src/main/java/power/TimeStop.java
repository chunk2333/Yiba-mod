package power;
//时间暂停
import actions.MyPressEndTurnButtonAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TimeStop extends AbstractPower {
    public static final String POWER_ID = "TimeStop";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TimeStop");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;
    public TimeStop(AbstractCreature owner) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        updateDescription();
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/TimeStop");
        this.region128 = atlas_self.findRegion("128/TimeStop");
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    @Override
    public void atStartOfTurn(){
        //回合开始时
        //移除buff
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        //结束回合
        addToBot(new MyPressEndTurnButtonAction());
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }
}