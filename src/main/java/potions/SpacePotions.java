
//空间药水
//public class SpacePotions {

package potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.potions.AbstractPotion;


public class SpacePotions extends AbstractPotion{
    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("SpacePotions");
    public static final String POTION_ID = "SpacePotions";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    private static final String[] DESCRIPTIONS = PotionStrings.getMockPotionString().DESCRIPTIONS;

    public SpacePotions() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.FAIRY, PotionColor.ENERGY);
        this.isThrown = false;
    }

    public void use(AbstractCreature target) {
        //给予药水栏位
        AbstractDungeon.player.potionSlots = AbstractDungeon.player.potionSlots + getPotency();
        for(int i = 0; i < getPotency(); i++){
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - (getPotency()-i)));
        }
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
    public void initializeData(){
        this.potency = getPotency();
        this.description = potionString.DESCRIPTIONS[0] + this.potency + potionString.DESCRIPTIONS[1];
        //this.description = potionString.DESCRIPTIONS[0];
        this.name = potionString.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    public int getPotency(int ascensionLevel) {
        return 1;
    }
    public AbstractPotion makeCopy() {
        return new SpacePotions();
    }
}