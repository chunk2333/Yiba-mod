package power;
//水元素

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import power.abstracrt.ArrayElementPower;
import power.abstracrt.ElementPower;
import relics.abstracrt.ArrayElementRelic;
import relics.abstracrt.ElementRelic;

public class HydroPower extends AbstractPower {

    public static final String POWER_ID = "HydroPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("HydroPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public static Boolean isHasHydroAndPyro = false;

    private boolean isActive;

    private final int mystery;

    boolean hasPyro = false;

    boolean hasGeo = false;

    boolean hasHydro = false;

    private boolean isMultiple = false;

    private boolean isMultipleActive = false;

    public HydroPower(AbstractCreature owner, int mystery) {
        super();
        this.mystery = mystery;
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        updateDescription();
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/HydroPower");
        this.region128 = atlas_self.findRegion("128/HydroPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void triggerElementreaction() {
        //单蒸发
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        isHasHydroAndPyro = false;
        AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
        YibaMod.logger.info("水元素：触发裸蒸发");
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
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        isHasHydroAndPyro = false;
        if (power.ID.equals("PyroPower")) {
            isActive = true;
            isHasHydroAndPyro = true;
        }
        if (power.ID.equals("GeoPower")) {
            //粘土
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "GeoPower"));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            addToBot(new GainBlockAction(AbstractDungeon.player, 15 + this.mystery, Settings.FAST_MODE));
            AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "粘土", Color.BLUE.cpy()));
            if (!ArrayElementRelic.getElementRelic().isEmpty()) {
                for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                    r.triggerElement("粘土-水岩");
                }
            }
            if (!ArrayElementPower.getElementPower().isEmpty()) {
                for (ElementPower powers : ArrayElementPower.getElementPower()) {
                    powers.triggerElement("粘土-水岩");
                }
            }
            YiBaHelper.setLastTriggerElement("粘土", "水岩");
        }
    }

    @Override
    public void atStartOfTurn() {
        this.isMultipleActive = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        this.isMultipleActive = false;
    }
}