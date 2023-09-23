package power;
//岩元素
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import power.abstracrt.ArrayElementPower;
import power.abstracrt.ElementPower;
import relics.abstracrt.ArrayElementRelic;
import relics.abstracrt.ElementRelic;

public class GeoPower extends AbstractPower {

    public static final String POWER_ID = "GeoPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("GeoPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    private final int mystery;

    private boolean isMultiple = false;

    private boolean isMultipleActive = false;

    public GeoPower(AbstractCreature owner, int mystery) {
        super();
        this.mystery=mystery;
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        updateDescription();
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/GeoPower");
        this.region128 = atlas_self.findRegion("128/GeoPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.ID.equals("HydroPower")){
            //粘土
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            AbstractMonster m = (AbstractMonster) this.owner;
            //击晕
            //AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, this.owner));

            //给予格挡
            addToBot(new GainBlockAction(AbstractDungeon.player, 15 + this.mystery, Settings.FAST_MODE));

            //怪物头顶显示xxx字
            AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "粘土", Color.BLUE.cpy()));
            //通知元素反应遗物
            if(!ArrayElementRelic.getElementRelic().isEmpty()){
                for (ElementRelic r : ArrayElementRelic.getElementRelic()){
                    r.triggerElement("粘土-岩水");
                }
            }
            //通知元素反应能力
            if(!ArrayElementPower.getElementPower().isEmpty()){
                for (ElementPower powers : ArrayElementPower.getElementPower()){
                    powers.triggerElement("粘土-岩水");
                }
            }
            YiBaHelper.setLastTriggerElement("粘土", "粘土-岩水");
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        for(AbstractPower power:this.owner.powers){
            if(power.ID.equals("PyroPower")){
                this.isMultiple = power.canGoNegative;
                YibaMod.logger.info("触发熔岩，是否是多段伤害：" + this.isMultiple + "是否已经触发了一次多段：" + this.isMultipleActive);

                //移除火元素
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "PyroPower"));
                //移除岩元素，即自身
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                //怪物头顶显示元素反应类型
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "熔岩", Color.GOLD.cpy()));
                YibaMod.logger.info("触发熔岩：" + damageAmount * 2 + "额外伤害："+ this.mystery);
                if(this.isMultiple && !this.isMultipleActive){
                    //给予易伤
                    //addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new VulnerablePower(this.owner, 1, false), 1));
                    //抽1牌
                    //addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                    //获得1费
                    AbstractDungeon.player.gainEnergy(1);
                    this.isMultipleActive = true;
                    //通知元素反应遗物
                    if(!ArrayElementRelic.getElementRelic().isEmpty()){
                        for (ElementRelic r : ArrayElementRelic.getElementRelic()){
                            r.triggerElement("熔岩-岩火");
                        }
                    }
                    //通知元素反应能力
                    if(!ArrayElementPower.getElementPower().isEmpty()){
                        for (ElementPower powers : ArrayElementPower.getElementPower()){
                            powers.triggerElement("熔岩-岩火");
                        }
                    }
                    YiBaHelper.setLastTriggerElement("熔岩", "熔岩-岩火");
                    int InvincibleNum = 0;
                    boolean hasInvincible = false;
                    //处理坚不可摧
                    for(AbstractPower power_Invincible : this.owner.powers){
                        if(power_Invincible.ID.equals("Invincible")){
                            InvincibleNum = power_Invincible.amount;
                            hasInvincible = true;
                        }
                    }
                    //处理无实体
                    for(AbstractPower power_Invincible : this.owner.powers){
                        if(power_Invincible.ID.equals("IntangiblePlayer")){
                            return 1;
                        }
                    }
                    //最终伤害
                    int finalDamage = damageAmount * 2 + this.mystery;
                    if(finalDamage >= InvincibleNum && hasInvincible){
                        addToTop(new ApplyPowerAction(this.owner, this.owner, new InvinciblePower(this.owner, -InvincibleNum), -InvincibleNum));
                        return damageAmount + InvincibleNum;
                    }else {
                        return finalDamage;
                    }
                }
                if(!this.isMultiple) {
                    //给予易伤
                    //addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new VulnerablePower(this.owner, 1, false), 2));
                    //抽1牌
                    //addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                    //获得1费
                    AbstractDungeon.player.gainEnergy(1);
                    //通知元素反应遗物
                    if(!ArrayElementRelic.getElementRelic().isEmpty()){
                        for (ElementRelic r : ArrayElementRelic.getElementRelic()){
                            r.triggerElement("熔岩-岩火");
                        }
                    }
                    //通知元素反应能力
                    if(!ArrayElementPower.getElementPower().isEmpty()){
                        for (ElementPower powers : ArrayElementPower.getElementPower()){
                            powers.triggerElement("熔岩-岩火");
                        }
                    }
                    YiBaHelper.setLastTriggerElement("熔岩", "熔岩-岩火");
                }
                int InvincibleNum = 0;
                boolean hasInvincible = false;
                //处理坚不可摧
                for(AbstractPower power_Invincible : this.owner.powers){
                    if(power_Invincible.ID.equals("Invincible")){
                        InvincibleNum = power_Invincible.amount;
                        hasInvincible = true;
                    }
                }
                //处理无实体
                for(AbstractPower power_Invincible : this.owner.powers){
                    if(power_Invincible.ID.equals("IntangiblePlayer")){
                        return 1;
                    }
                }
                int finalDamage = damageAmount * 2 + this.mystery;
                if(finalDamage >= InvincibleNum && hasInvincible){
                    addToTop(new ApplyPowerAction(this.owner, this.owner, new InvinciblePower(this.owner, -InvincibleNum), -InvincibleNum));
                    return damageAmount + InvincibleNum;
                }else {
                    return finalDamage;
                }
            }

        }
        return damageAmount;
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