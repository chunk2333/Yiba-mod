package potions;
//元素精通药水
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import power.MysteryPower;

public class ElementPotions extends AbstractPotion {

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("ElementPotions");

    public static final String POTION_ID = "ElementPotions";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    public ElementPotions() {
        super(NAME, POTION_ID, PotionRarity.COMMON, AbstractPotion.PotionSize.S, PotionColor.WHITE);
        this.isThrown = false;
        this.labOutlineColor = Color.WHITE.cpy();
    }

    public void use(AbstractCreature target) {
        AbstractPlayer abstractPlayer = AbstractDungeon.player;
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            addToBot(new ApplyPowerAction(abstractPlayer, AbstractDungeon.player, new MysteryPower(abstractPlayer, this.potency) {
            }, this.potency));

    }

    public void initializeData(){
        //药水描述
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        this.name = potionStrings.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

        this.tips.add(new PowerTip(

                TipHelper.capitalize("元素精通"), GameDictionary.keywords
                .get("元素精通")));
    }

    public int getPotency(int ascensionLevel) {
        return 3;
    }

    public AbstractPotion makeCopy() {
        return new ElementPotions();
    }
}
