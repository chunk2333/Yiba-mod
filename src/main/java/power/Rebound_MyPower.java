package power;
//斗转星移
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Rebound_MyPower extends AbstractPower {
    public static final String POWER_ID = "Rebound_MyPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Rebound_MyPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public Rebound_MyPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/Rebound_MyPower");
        this.region128 = atlas_self.findRegion("128/Rebound_MyPower");
        this.type = AbstractPower.PowerType.BUFF;
    }
    public void atStartOfTurn(){
        //回合开始时：降低1层数
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Rebound_MyPower"));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, "Rebound_MyPower", 1));
        }
    }
    public int onAttacked(DamageInfo info, int damageAmount) {
        UpdateStats.logger.info("斗转星移受到伤害："+damageAmount);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            flash();
            addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, damageAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
        return 0;
    }
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发
    }
}