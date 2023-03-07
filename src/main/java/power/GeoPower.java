

//public class GeoPower {

package power;




import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.badlogic.gdx.files.FileHandle;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import pathes.AbstractPower_Self;

public class GeoPower extends AbstractPower {
    public static final String POWER_ID = "GeoPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("GeoPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;
    private boolean isActive;
    private int mystery;
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
        if(power.ID=="HydroPower"){
            //粘土
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "HydroPower"));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            AbstractMonster m = (AbstractMonster) this.owner;
            AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, this.owner));
            //m.setMove((byte) 999,AbstractMonster.Intent.STUN);
            AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "粘土", Color.BLUE.cpy()));
        }
    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        for(AbstractPower power:this.owner.powers){
            if(power.ID=="PyroPower"){
                //给予易伤
                addToBot(new ApplyPowerAction(this.owner, AbstractDungeon.player, new VulnerablePower(this.owner, 2, false), 2));
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "PyroPower"));
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.owner.drawX, this.owner.drawY, "熔岩", Color.GOLD.cpy()));
                UpdateStats.logger.info("触发熔岩：" + damageAmount * 3 + this.mystery);
                //抽1卡
                addToBot(new DrawCardAction(AbstractDungeon.player, 1));
                AbstractDungeon.player.gainEnergy(1);
                return damageAmount * 3 + this.mystery;
            }

        }
        return damageAmount;
    }

}