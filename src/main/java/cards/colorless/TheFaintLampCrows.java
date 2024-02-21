package cards.colorless;
//幽灯啼
import YibaMod.YibaMod;
import actions.TheFaintLampCrowsAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TheFaintLampCrows extends CustomCard{
    public static final String ID = YibaMod.makeModID("TheFaintLampCrows");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/test.png";

    private static final int COST = 1;

    public TheFaintLampCrows() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.tags.add(YibaMod.VANISH);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TheFaintLampCrowsAction(this.upgraded));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TheFaintLampCrows();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
