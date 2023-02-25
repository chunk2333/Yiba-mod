package potions;


import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import com.megacrit.cardcrawl.potions.AbstractPotion;


public class time extends AbstractPotion{
    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("time");
    public static final String POTION_ID = "time";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    private static final String[] DESCRIPTIONS = PotionStrings.getMockPotionString().DESCRIPTIONS;

    public time() {
        super(NAME, POTION_ID, AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.GHOST, AbstractPotion.PotionColor.SMOKE);
        this.isThrown = true;
    }

    public void use(AbstractCreature target) {
        //腾跃动画
        addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
        //跳过回合
        addToBot(new SkipEnemiesTurnAction());
        //结束回合
        addToBot(new PressEndTurnButtonAction());

    }
    public void initializeData(){
        this.potency = getPotency();
        //this.description = potionString.DESCRIPTIONS[0] + this.potency + potionString.DESCRIPTIONS[1];
        this.description = potionString.DESCRIPTIONS[0];
        this.name = potionString.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    public int getPotency(int ascensionLevel) {
        return 0;
    }
    public AbstractPotion makeCopy() {
        return new time();
    }
}
