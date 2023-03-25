package actions;

import Tools.YiBaHelper;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import power.HydroPower;
import power.PyroPower;
import power.abstracrt.ArrayElementPower;
import power.abstracrt.ElementPower;
import relics.abstracrt.ArrayElementRelic;
import relics.abstracrt.ElementRelic;

public class AnemoAction extends AbstractGameAction {
    private AbstractPlayer p;

    AbstractMonster m;
    public AnemoAction(AbstractMonster m) {
        this.m = m;
        this.actionType = ActionType.POWER;
    }

    public void update() {
        for(AbstractPower power:this.m.powers){
            if(power.ID.equals("PyroPower")){
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    addToBot(new ApplyPowerAction(mo, mo, new PyroPower(mo, 0), 1));
                }
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(m.drawX, m.drawY, "扩散", Color.GREEN.cpy()));
                tickDuration();
                if(!ArrayElementRelic.getElementRelic().isEmpty()){
                    for (ElementRelic r : ArrayElementRelic.getElementRelic()){
                        r.triggerElement("扩散-火");
                    }
                }
                if(!ArrayElementPower.getElementPower().isEmpty()){
                    for (ElementPower powers : ArrayElementPower.getElementPower()){
                        powers.triggerElement("扩散-火");
                    }
                }
                YiBaHelper.setLastTriggerElement("扩散", "扩散-火");
                addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                return;
            }
            if(power.ID.equals("HydroPower")){
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    addToBot(new ApplyPowerAction(mo, mo, new HydroPower(mo, 0), 1));
                }
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(m.drawX, m.drawY, "扩散", Color.GREEN.cpy()));
                tickDuration();
                if(!ArrayElementRelic.getElementRelic().isEmpty()){
                    for (ElementRelic r : ArrayElementRelic.getElementRelic()){
                        r.triggerElement("扩散-水");
                    }
                }
                //通知元素反应能力
                if(!ArrayElementPower.getElementPower().isEmpty()){
                    for (ElementPower powers : ArrayElementPower.getElementPower()){
                        powers.triggerElement("扩散-水");
                    }
                }
                YiBaHelper.setLastTriggerElement("扩散", "扩散-水");
                addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                return;
            }
        }
        tickDuration();
    }
}