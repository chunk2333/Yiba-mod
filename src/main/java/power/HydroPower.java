package power;

//public class HydroPower {




import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
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
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import pathes.AbstractPower_Self;

public class HydroPower extends AbstractPower {
    public static final String POWER_ID = "HydroPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("HydroPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;
    private boolean isActive;
    private int mystery;
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

    public void atStartOfTurn(){
        //回合开始时
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发

    }
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.ID=="PyroPower"){
            //蒸发  移除水火元素，头顶显示 ”蒸发“
            isActive = true;
        }
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(!isActive){
            for(AbstractPower power:this.owner.powers){
                if(power.ID=="PyroPower"){
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "PyroPower"));
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                    AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "蒸发", Color.RED.cpy()));
                    UpdateStats.logger.info("触发1.5蒸发："+ damageAmount * 1.5 + this.mystery);
                    return (int) (damageAmount * 1.5 + this.mystery);
                }

            }
        }


        return damageAmount;
    }

}