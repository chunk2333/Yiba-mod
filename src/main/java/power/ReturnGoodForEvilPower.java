package power;
//空灵
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;


public class ReturnGoodForEvilPower extends AbstractPower {
    public static final String POWER_ID = "ReturnGoodForEvilPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ReturnGoodForEvilPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public ReturnGoodForEvilPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/ReturnGoodForEvilPower");
        this.region128 = atlas_self.findRegion("128/ReturnGoodForEvilPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void atStartOfTurn(){
        //回合开始时
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡牌时触发

    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        //转换姿态时触发
        flash();
        addToBot(new DrawCardAction(this.owner, this.amount));
    }
}
