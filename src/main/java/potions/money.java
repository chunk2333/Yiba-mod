package potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.potions.AbstractPotion;


public class money extends AbstractPotion{
    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("money");
    public static final String POTION_ID = "money";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    private static final String[] DESCRIPTIONS = PotionStrings.getMockPotionString().DESCRIPTIONS;

    public money() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.CARD, PotionColor.SMOKE);
        this.isThrown = false;
    }

    public void use(AbstractCreature target) {
        //名利双收掉金币动画
        AbstractDungeon.effectList.add(new RainingGoldEffect(75 * 2, true));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        AbstractPlayer p = AbstractDungeon.player;
        //给予玩家75金币
        p.gainGold(getPotency());

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
        return 75;
    }
    public AbstractPotion makeCopy() {
        return new money();
    }
}
