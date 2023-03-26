package actions;

import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import power.abstracrt.ArrayElementPower;
import power.abstracrt.ElementPower;
import relics.abstracrt.ArrayElementRelic;
import relics.abstracrt.ElementRelic;

public class TriggerElementAction extends AbstractGameAction {

    private final String elementName;

    private final String fullName;

    private final AbstractMonster owner;

    private final DamageInfo damage;

    private final AbstractGameAction.AttackEffect eff;

    private final int mystery;

    private int finalDamage = 0;


    public TriggerElementAction(AbstractMonster mo, DamageInfo damage, AbstractGameAction.AttackEffect eff, String name, String fullName) {
        this.elementName = name;
        this.fullName = fullName;
        this.owner = mo;
        this.damage = damage;
        this.eff = eff;
        this.mystery = YiBaHelper.getPlayerMystery();
    }

    @Override
    public void update() {

        if (this.fullName != null && this.elementName != null) {
            switch (this.elementName) {
                case "2.0蒸发":
                    addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new WeakPower(this.owner, 1, false), 1));
                    AbstractDungeon.player.gainEnergy(1);
                    if (!ArrayElementRelic.getElementRelic().isEmpty()) {
                        for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                            r.triggerElement("2.0蒸发");
                        }
                    }
                    if (!ArrayElementPower.getElementPower().isEmpty()) {
                        for (ElementPower powers : ArrayElementPower.getElementPower()) {
                            powers.triggerElement("2.0蒸发");
                        }
                    }
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
                    this.finalDamage = this.damage.base * 2 + this.mystery;
                    YibaMod.logger.info("触发2.0蒸发：" + (this.finalDamage));
                    YiBaHelper.setLastTriggerElement("蒸发", "2.0蒸发");
                    addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.finalDamage, this.damage.type), this.eff));
                    break;
                case "1.5蒸发":
                    addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new WeakPower(this.owner, 1, false), 1));
                    AbstractDungeon.player.gainEnergy(1);
                    if (!ArrayElementRelic.getElementRelic().isEmpty()) {
                        for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                            r.triggerElement("1.5蒸发");
                        }
                    }
                    if (!ArrayElementPower.getElementPower().isEmpty()) {
                        for (ElementPower powers : ArrayElementPower.getElementPower()) {
                            powers.triggerElement("1.5蒸发");
                        }
                    }
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
                    this.finalDamage = (int) (this.damage.base * 1.5 + this.mystery);
                    YibaMod.logger.info("触发1.5蒸发蒸发：" + (this.finalDamage));
                    YiBaHelper.setLastTriggerElement("蒸发", "1.5蒸发");
                    addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.finalDamage, this.damage.type), this.eff));
                    break;
                case "粘土":
                    addToBot(new GainBlockAction(AbstractDungeon.player, 15 + this.mystery, Settings.FAST_MODE));
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "粘土", Color.BLUE.cpy()));
                    if (!ArrayElementRelic.getElementRelic().isEmpty()) {
                        for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                            r.triggerElement("粘土-" + this.fullName);
                        }
                    }
                    if (!ArrayElementPower.getElementPower().isEmpty()) {
                        for (ElementPower powers : ArrayElementPower.getElementPower()) {
                            powers.triggerElement("粘土-" + this.fullName);
                        }
                    }
                    YiBaHelper.setLastTriggerElement("粘土", this.fullName);
                    break;
                case "熔岩":
                    addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new VulnerablePower(this.owner, 1, false), 1));
                    AbstractDungeon.player.gainEnergy(1);
                    if (!ArrayElementRelic.getElementRelic().isEmpty()) {
                        for (ElementRelic r : ArrayElementRelic.getElementRelic()) {
                            r.triggerElement("熔岩-" + this.fullName);
                        }
                    }
                    if (!ArrayElementPower.getElementPower().isEmpty()) {
                        for (ElementPower powers : ArrayElementPower.getElementPower()) {
                            powers.triggerElement("熔岩-" + this.fullName);
                        }
                    }
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "熔岩", Color.GOLD.cpy()));
                    this.finalDamage = this.damage.base * 3 + this.mystery;
                    YibaMod.logger.info("触发熔岩：" + (this.finalDamage));
                    addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.finalDamage, this.damage.type), this.eff));
                    break;
            }
        }


        tickDuration();
    }
}
