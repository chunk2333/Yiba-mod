package potions;
//Escape



import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class Escape extends AbstractPotion{
    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("Escape");
    public static final String POTION_ID = "Escape";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    private static final String[] DESCRIPTIONS = PotionStrings.getMockPotionString().DESCRIPTIONS;

    public Escape() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.POWER);
        this.isThrown = true;
        this.targetRequired = true;
    }
    public void use(AbstractCreature target) {
        //
        if(target.hasPower("Life Link")){
            //移除小黑的生命链接
            addToBot(new RemoveSpecificPowerAction(target, target, "Life Link"));
        }
        AbstractDungeon.actionManager.addToBottom(new TalkAction(target, potionString.DESCRIPTIONS[1], 0.3F, 2.5F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(target.hb.cX, target.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new EscapeAction((AbstractMonster) target));
    }
    public void initializeData(){
        //药水描述
        this.potency = getPotency();
        //this.description = potionString.DESCRIPTIONS[0] + this.potency + potionString.DESCRIPTIONS[1];
        this.description = potionString.DESCRIPTIONS[0];
        this.name = potionString.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    public boolean canUse() {
        if (super.canUse()) {
            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (m.hasPower("BackAttack"))
                    return false;
                if (m.type == AbstractMonster.EnemyType.BOSS)
                    return false;
            }
            return true;
        }
        return false;
    }
    public int getPotency(int ascensionLevel) {
        return 0;
    }
    public AbstractPotion makeCopy() {
        return new Escape();
    }
}
