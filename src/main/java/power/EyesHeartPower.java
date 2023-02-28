package power;
//心之眼能力
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EyesHeartPower extends AbstractPower {
    public static final String POWER_ID = "EyesHeartPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("EyesHeartPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;
    public EyesHeartPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.description = DESCRIPTIONS[0]+ amt + DESCRIPTIONS[1];
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/EyesHeartPower");
        this.region128 = atlas_self.findRegion("128/EyesHeartPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void atStartOfTurn(){
        //回合开始时
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发
        if(card.type == AbstractCard.CardType.ATTACK){
            flash();
            addToBot(new GainBlockAction(this.owner, this.amount, Settings.FAST_MODE));

        }
    }
}