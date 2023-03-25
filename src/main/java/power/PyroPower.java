package power;
//火元素

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import power.abstracrt.ArrayElementPower;
import power.abstracrt.ElementPower;
import relics.abstracrt.ArrayElementRelic;
import relics.abstracrt.ElementRelic;

public class PyroPower extends AbstractPower {

    public static final String POWER_ID = "PyroPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("PyroPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    private boolean isActive;

    private final int mystery;

    boolean hasPyro = false;

    boolean hasGeo = false;

    boolean hasHydro = false;

    private boolean isMultiple = false;

    private boolean isMultipleActive = false;

    public PyroPower(AbstractCreature owner, int mystery) {
        super();
        this.mystery = mystery;
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        updateDescription();
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/PyroPower");
        this.region128 = atlas_self.findRegion("128/PyroPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public PyroPower(AbstractCreature owner, int mystery, boolean isMultiple) {
        super();
        this.mystery = mystery;
        this.canGoNegative = isMultiple;
        this.isMultiple = isMultiple;
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        updateDescription();
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/PyroPower");
        this.region128 = atlas_self.findRegion("128/PyroPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void triggerElementreaction() {
        YibaMod.logger.info("触发裸蒸发");
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        //通知元素反应遗物
        if (!ArrayElementRelic.getElementRelic().isEmpty()) {
            for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                r.triggerElement("裸蒸发");
            }
        }
        //通知元素反应能力
        if (!ArrayElementPower.getElementPower().isEmpty()) {
            for (ElementPower powers : ArrayElementPower.getElementPower()) {
                powers.triggerElement("裸蒸发");
            }
        }
        YiBaHelper.setLastTriggerElement("蒸发", "裸蒸发");
        AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals("HydroPower")) {
            this.isActive = true;
        }
        if (power.ID.equals("GeoPower")) {
            this.isActive = true;
            this.hasGeo = true;
        }
        for (AbstractPower power_ : this.owner.powers) {
            if (power_.ID.equals("GeoPower")) {
                this.hasGeo = true;
            }
            if (power_.ID.equals("PyroPower")) {
                this.hasPyro = true;
            }
            if (power_.ID.equals("HydroPower")) {
                this.hasHydro = true;

            }
        }

        if (this.hasPyro && this.hasHydro) {
            triggerElementreaction();

        }

    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        for (AbstractPower power : this.owner.powers) {
            if (power.ID.equals("PyroPower")) {
                this.hasPyro = true;
            }
            if (power.ID.equals("GeoPower")) {
                this.hasGeo = true;
            }
            if (power.ID.equals("HydroPower")) {
                this.hasHydro = true;
            }
        }
        if (this.hasHydro && this.hasPyro) {
            isActive = false;
        }
        if (!this.isActive) {

            if (this.hasPyro && this.hasGeo) {
                return damageAmount;
            }
            if (this.hasHydro && this.hasPyro) {
                triggerElementreaction();
                return 0;
            }


            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            if (this.isMultiple && !this.isMultipleActive) {
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
                YibaMod.logger.info("触发1.5蒸发：" + (damageAmount * 1.5 + this.mystery));
                //抽1卡
                //addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                //回1费
                AbstractDungeon.player.gainEnergy(1);
                this.isMultipleActive = true;
                //通知元素反应遗物
                if (!ArrayElementRelic.getElementRelic().isEmpty()) {
                    for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                        r.triggerElement("1.5蒸发");
                    }
                }
                //通知元素反应能力
                if (!ArrayElementPower.getElementPower().isEmpty()) {
                    for (ElementPower powers : ArrayElementPower.getElementPower()) {
                        powers.triggerElement("1.5蒸发");
                    }
                }
                YiBaHelper.setLastTriggerElement("蒸发", "1.5蒸发");
                return (int) (damageAmount * 1.5 + this.mystery);
            }
            if (!this.isMultiple) {
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
                YibaMod.logger.info("触发1.5蒸发：" + (damageAmount * 1.5 + this.mystery));
                //抽1卡
                //addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                //回1费
                AbstractDungeon.player.gainEnergy(1);
                //通知元素反应遗物
                if (!ArrayElementRelic.getElementRelic().isEmpty()) {
                    for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                        r.triggerElement("1.5蒸发");
                    }
                }
                //通知元素反应能力
                if (!ArrayElementPower.getElementPower().isEmpty()) {
                    for (ElementPower powers : ArrayElementPower.getElementPower()) {
                        powers.triggerElement("1.5蒸发");
                    }
                }
                YiBaHelper.setLastTriggerElement("蒸发", "1.5蒸发");
            }
            return (int) (damageAmount * 1.5 + this.mystery);


        }
        return damageAmount;
    }


}