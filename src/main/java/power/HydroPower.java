package power;
//水元素
import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
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

    private boolean isActive;

    private final int mystery;

    boolean hasPyro = false;

    boolean hasGeo = false;

    boolean hasHydro = false;

    public HydroPower(AbstractCreature owner, int mystery) {
        super();
        this.mystery=mystery;
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

    public void triggerElementreaction(){
        //单蒸发
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.ID.equals("PyroPower")){
            isActive = true;
        }
        if(power.ID.equals("GeoPower")){
            //粘土
            hasGeo = true;
            isActive = true;
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "GeoPower"));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            AbstractMonster m = (AbstractMonster) this.owner;
            //击晕
            AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, this.owner));
            //m.setMove((byte) 999,AbstractMonster.Intent.STUN);
            AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "粘土", Color.BLUE.cpy()));
            //通知元素反应遗物
            if(!ArrayElementRelic.getElementRelic().isEmpty()){
                for (ElementRelic r : ArrayElementRelic.getElementRelic()){
                    r.triggerElement("粘土-水岩");
                }
            }
            //通知元素反应能力
            if(!ArrayElementPower.getElementPower().isEmpty()){
                for (ElementPower powers : ArrayElementPower.getElementPower()){
                    powers.triggerElement("粘土-水岩");
                }
            }
        }

        for(AbstractPower power_:this.owner.powers){
            if(power_.ID.equals("GeoPower")){
                this.hasGeo = true;
            }
            if(power_.ID.equals("PyroPower")){
                this.hasPyro = true;
            }
            if(power_.ID.equals("HydroPower")){
                this.hasHydro = true;
            }
        }
        if(this.hasPyro && this.hasHydro){
            //单蒸发
            triggerElementreaction();
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(!isActive){
            //YibaMod.logger.info("触发2.0蒸发的未增幅伤害："+ damageAmount );
            for(AbstractPower power:this.owner.powers){
                if(power.ID.equals("GeoPower")){
                    hasGeo = true;
                }
                if(power.ID.equals("HydroPower")) {
                    hasHydro = true;
                }
                if(power.ID.equals("PyroPower")){
                    if(hasHydro && hasGeo){
                        return 0;
                    }
                    if(this.hasHydro){
                        return 0;
                    }
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "PyroPower"));
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
                    YibaMod.logger.info("触发2.0蒸发："+ (damageAmount * 2 + this.mystery));
                    //给虚弱
                    addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new WeakPower(this.owner, 1, false), 1));
                    //抽1卡
                    addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                    //回复1能量
                    AbstractDungeon.player.gainEnergy(1);
                    //通知元素反应遗物
                    if(!ArrayElementRelic.getElementRelic().isEmpty()){
                        for (ElementRelic r : ArrayElementRelic.getElementRelic()){
                            r.triggerElement("2.0蒸发");
                        }
                    }
                    //通知元素反应能力
                    if(!ArrayElementPower.getElementPower().isEmpty()){
                        for (ElementPower powers : ArrayElementPower.getElementPower()){
                            powers.triggerElement("2.0蒸发");
                        }
                    }
                    return damageAmount * 2 + this.mystery;
                }
            }
        }
        return damageAmount;
    }
}