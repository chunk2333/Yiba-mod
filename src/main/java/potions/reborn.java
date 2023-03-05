package potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class reborn extends AbstractPotion{
    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("reborn");
    public static final String POTION_ID = "reborn";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    private static final String[] DESCRIPTIONS = PotionStrings.getMockPotionString().DESCRIPTIONS;

    public reborn() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.GHOST, PotionColor.ENERGY);
        this.isThrown = false;
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
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return false;
        if ((AbstractDungeon.getCurrRoom()).event != null &&
                (AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain)
            return false;
        return true;
    }
    public int getPotency(int ascensionLevel) {
        return 0;
    }
    public AbstractPotion makeCopy() {
        return new reborn();
    }
}
