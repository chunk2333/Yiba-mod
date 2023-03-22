package power;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SpiritualDominationPower extends AbstractPower {

    public static final String POWER_ID = "SpiritualDominationPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("SpiritualDominationPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    private boolean isActive;

    public SpiritualDominationPower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/SpiritualDominationPower");
        this.region128 = atlas_self.findRegion("128/SpiritualDominationPower");
        this.type = PowerType.DEBUFF;
    }

    @Override

    public void atEndOfTurn(boolean isPlayer) {
        //回合开始时：降低1层数
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "SpiritualDominationPower"));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, "SpiritualDominationPower", 1));
            isActive = false;
        }
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        AbstractMonster targetMonster = (AbstractMonster) this.owner;
        if (targetMonster != null && targetMonster.getIntentBaseDmg() >= 0 && !isActive) {

            //YibaMod.logger.info("受到伤害.isEndingTurn:" + AbstractDungeon.player.isEndingTurn);

            isActive = true;
            flash();
            addToTop(new DamageAction(this.owner, new DamageInfo(this.owner, (int) damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
        //YibaMod.logger.info("isEndingTurn:" + AbstractDungeon.player.isEndingTurn);
        return 0;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}
