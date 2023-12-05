package cards.red;
//萌萌哒
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CuteAndAdorable extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("CuteAndAdorable");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/test.png";

    private static final int COST = 2;

    public static final String ID = "DemonicPact";

    private int useNum;

    public CuteAndAdorable() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.useNum += 1;
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseMagicNumber), this.baseMagicNumber));
        if (this.useNum == 2){
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseMagicNumber), this.baseMagicNumber));
            this.useNum = 0;
        }
    }

    @Override

    public AbstractCard makeCopy() {
        return new CuteAndAdorable();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
