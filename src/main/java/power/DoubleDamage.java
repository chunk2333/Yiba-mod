package power;
//DoubleDamage




import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.badlogic.gdx.files.FileHandle;
import pathes.AbstractPower_Self;

public class DoubleDamage extends AbstractPower {
    public static final String POWER_ID = "DoubleDamage";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("DoubleDamage");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas atlas;
    public static TextureAtlas atlas_self;
    public DoubleDamage(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.description = DESCRIPTIONS[0]+ amt + DESCRIPTIONS[1];
        this.ID = "DoubleDamage";
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/DoubleDamage");
        this.region128 = atlas_self.findRegion("128/DoubleDamage");
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void atStartOfTurn(){
        //回合开始时
    }
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //使用卡片时触发
        if(card.type == AbstractCard.CardType.ATTACK){
            flash();
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "DoubleDamage"));
            } else {
                addToBot(new ReducePowerAction(this.owner, this.owner, "DoubleDamage", 1));
            }
        }

        //addToBot(new DrawCardAction(AbstractDungeon.player, this.amount));

    }
    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        //给予伤害时
        if (type == DamageInfo.DamageType.NORMAL)
            return damage * 2;
        return damage;
    }
}