package power;
//原始胎海之水
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.OmegaFlashEffect;

public class TidePower extends AbstractPower {

    public static final String POWER_ID = "TidePower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("TidePower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public TidePower(AbstractCreature owner, int amt) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/TidePower");
        this.region128 = atlas_self.findRegion("128/TidePower");
        this.type = AbstractPower.PowerType.BUFF;
    }


    public void atStartOfTurn(){
        //回合开始时：降低1层数
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "TidePower"));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, "TidePower", 1));
        }
        AbstractPlayer p = AbstractDungeon.player;
        int finalDamage;
        finalDamage = (int) (p.maxHealth * 10 * 0.01);
        flash();
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m != null && !m.isDeadOrEscaped()) {
                addToTop(new ApplyPowerAction(m, m, new HydroPower(m, YiBaHelper.getPlayerMystery()), 1));
                if (Settings.FAST_MODE) {
                    addToBot(new VFXAction(new OmegaFlashEffect(m.hb.cX, m.hb.cY)));
                    continue;
                }
                addToBot(new VFXAction(new OmegaFlashEffect(m.hb.cX, m.hb.cY), 0.2F));

            }
        }
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
        addToBot(new DamageAllEnemiesAction(null,

                DamageInfo.createDamageMatrix(finalDamage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

}
