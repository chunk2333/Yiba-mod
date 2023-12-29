package power;
//附魔金苹果
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.Iterator;

@SpirePatch2(clz = ApplyPowerAction.class, method = "update")
public class EnchantedGoldenApplePower extends AbstractPower {
    public static final String POWER_ID = "EnchantedGoldenApplePower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("EnchantedGoldenApplePower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public EnchantedGoldenApplePower(AbstractCreature owner, int amount) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/RedPantsPower");
        this.region128 = atlas_self.findRegion("128/RedPantsPower");
        this.type = AbstractPower.PowerType.BUFF;
    }

    @SpireInsertPatch(loc = 297, localvars = {"powerToApply"})
    public static void PostFix(AbstractPower powerToApply){
//        if (powerToApply.type == AbstractPower.PowerType.DEBUFF && powerToApply.owner != AbstractDungeon.player){
//            EnchantedGoldenApplePower.Trigger();
//        }
        if (powerToApply.type == AbstractPower.PowerType.DEBUFF){
            EnchantedGoldenApplePower.Trigger();
        }
    }


    public static void Trigger() {
        Iterator var1 = AbstractDungeon.player.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.type == PowerType.DEBUFF && AbstractDungeon.player.hasPower("EnchantedGoldenApplePower")) {
                AbstractDungeon.player.getPower("EnchantedGoldenApplePower").flash();
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, p.ID));
            }
        }
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        Iterator var1 = AbstractDungeon.player.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower p = (AbstractPower)var1.next();
            if (p.type == PowerType.DEBUFF) {
                flash();
                addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, p.ID));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        //回合结束时
        if(isPlayer){
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "EnchantedGoldenApplePower"));
            } else {
                addToBot(new ReducePowerAction(this.owner, this.owner, "EnchantedGoldenApplePower", 1));
            }
        }
    }


    @Override
    public void updateDescription() {
        if (this.amount == 1){
            this.description = powerStrings.DESCRIPTIONS[0];
        } else {
            this.description = this.amount + powerStrings.DESCRIPTIONS[1];
        }

    }
}
