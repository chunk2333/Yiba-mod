package potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class reborn extends AbstractPotion{
    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("reborn");
    public static final String POTION_ID = "reborn";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    private static final String[] DESCRIPTIONS = PotionStrings.getMockPotionString().DESCRIPTIONS;

    public reborn() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.GHOST, PotionColor.ENERGY);
        this.isThrown = true;
    }
    public void use(AbstractCreature target) {
        //回复最大生命值的生命
        AbstractPlayer p = AbstractDungeon.player;
        p.heal(p.maxHealth);
    }
    public void initializeData(){
        //药水描述
        this.potency = getPotency();
        this.description = potionString.DESCRIPTIONS[0];
        this.name = potionString.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    public int getPotency(int ascensionLevel) {
        return 0;
    }
    public AbstractPotion makeCopy() {
        return new reborn();
    }
}
