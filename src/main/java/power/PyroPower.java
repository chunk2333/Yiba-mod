package power;
//火元素
import YibaMod.YibaMod;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.badlogic.gdx.files.FileHandle;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import pathes.AbstractPower_Self;

public class PyroPower extends AbstractPower {
    public static final String POWER_ID = "PyroPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("PyroPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;
    private boolean isActive;
    private int mystery;
    boolean hasPyro = false;
    boolean hasGeo = false;
    boolean hasHydro = false;
    public PyroPower(AbstractCreature owner, int mystery) {
        super();
        this.mystery=mystery;
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

    public void atStartOfTurn(){
        //回合开始时
    }
    public void triggerElementreaction(){
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发

    }
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.ID=="HydroPower"){
            //蒸发  移除水火元素，头顶显示 ”蒸发“
            this.isActive = true;
        }
        if(power.ID=="GeoPower"){
            this.isActive = true;
            this.hasGeo = true;
        }
        for(AbstractPower power_:this.owner.powers){
            if(power_.ID == "GeoPower"){
                this.hasGeo = true;
            }
            if(power_.ID == "PyroPower"){
                this.hasPyro = true;
            }
            if(power_.ID == "HydroPower"){
                this.hasHydro = true;

            }
        }
        if(this.hasPyro && this.hasHydro){
            YibaMod.logger.info("触发裸蒸发");
            triggerElementreaction();
        }

    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(this.hasHydro && this.hasPyro){
            isActive = false;
        }
        if(!this.isActive){
            YibaMod.logger.info("触发1.5蒸发的未增幅伤害："+ damageAmount );
            for(AbstractPower power:this.owner.powers){
                if(power.ID.equals("PyroPower")){
                    this.hasPyro = true;
                }
                if(power.ID.equals("GeoPower")){
                    this.hasGeo = true;
                }
                if(power.ID.equals("HydroPower")){
                    this.hasHydro = true;
                    if(this.hasPyro && this.hasGeo){
                        return 0;
                    }

                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
                    YibaMod.logger.info("触发1.5蒸发："+ (damageAmount * 1.5 + this.mystery));
                    //抽1卡
                    addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                    AbstractDungeon.player.gainEnergy(1);
                    return (int) (damageAmount * 1.5 + this.mystery);
                }

            }
        }
        return damageAmount;
    }
}