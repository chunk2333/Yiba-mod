package potions;
//瓶装遗物药水
import actions.GetRandomRelicAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BottledRelicsPotion extends AbstractPotion {

    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("BottledRelicsPotion");

    public static final String POTION_ID = "BottledRelicsPotion";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    public BottledRelicsPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.HEART, PotionColor.SMOKE);

        this.isThrown = false;
    }

    public void use(AbstractCreature target) {

        int num = getPotency();

        if(num == 1){
            addToBot(new GetRandomRelicAction());
        }

        if(num == 2){
            addToBot(new GetRandomRelicAction());
            addToBot(new GetRandomRelicAction());
        }

    }

    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            return false;
        return (AbstractDungeon.getCurrRoom()).event == null ||
                !((AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain);
    }

    public void initializeData(){
        this.potency = getPotency();
        this.description = potionString.DESCRIPTIONS[0] + this.potency + potionString.DESCRIPTIONS[1];
        this.name = potionString.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new BottledRelicsPotion();
    }
}
