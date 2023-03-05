
//挑衅
//public class ProvokePower {

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

public class ProvokePower extends AbstractPower {
    public static final String POWER_ID = "ProvokePower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("ProvokePower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;

    public ProvokePower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.description = DESCRIPTIONS[0] + amt + DESCRIPTIONS[1];
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/ProvokePower");
        this.region128 = atlas_self.findRegion("128/ProvokePower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void atStartOfTurn() {
        //回合开始时
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        //回合结束时
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "ProvokePower"));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, "ProvokePower", 1));
        }
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发
    }
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        //给予伤害时
        //flash();
        return 0;
    }
}