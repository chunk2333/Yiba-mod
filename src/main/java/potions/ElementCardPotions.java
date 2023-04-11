package potions;
//元素牌药水
import actions.ElementCardPotionsAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class ElementCardPotions extends AbstractPotion {

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString("ElementCardPotions");

    public static final String POTION_ID = "ElementCardPotions";

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    public ElementCardPotions() {
        super(NAME, POTION_ID, PotionRarity.COMMON, AbstractPotion.PotionSize.CARD, PotionColor.ENERGY);
        this.isThrown = false;
        this.labOutlineColor = Color.WHITE.cpy();
    }

    public void use(AbstractCreature target) {
        addToBot(new ElementCardPotionsAction(this.potency));
    }

    public void initializeData(){
        //药水描述
        this.potency = getPotency();
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = potionStrings.DESCRIPTIONS[0];
        } else {
            this.description = potionStrings.DESCRIPTIONS[1];
        }
        this.name = potionStrings.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

        this.tips.add(new PowerTip(

                TipHelper.capitalize("元素牌"), GameDictionary.keywords
                .get("元素牌")));
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new ElementCardPotions();
    }
}
