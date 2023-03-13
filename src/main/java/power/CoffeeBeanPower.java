package power;
//咖啡豆遗物的能力。
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CoffeeBeanPower extends AbstractPower {
    public static final String POWER_ID = "CoffeeBeanPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("CoffeeBeanPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;
    public int finalDamage;
    public CoffeeBeanPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.description = DESCRIPTIONS[0]+ amt + DESCRIPTIONS[1];
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/CoffeeBeanPower");
        this.region128 = atlas_self.findRegion("128/CoffeeBeanPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void atStartOfTurn(){
        //回合开始时
    }
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        //回合结束时
        if(isPlayer){
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "CoffeeBeanPower"));
            } else {
                addToBot(new ReducePowerAction(this.owner, this.owner, "CoffeeBeanPower", 1));
            }
        }
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发
        if(card.type== AbstractCard.CardType.ATTACK){
            flash();
        }

    }
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        //给予伤害时
        finalDamage = (int) Math.ceil(damage *1.3);
        if (type == DamageInfo.DamageType.NORMAL){
            return finalDamage;
        }

        return damage;
    }
}