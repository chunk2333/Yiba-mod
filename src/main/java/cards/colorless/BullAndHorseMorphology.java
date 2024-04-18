package cards.colorless;
//牛马形态
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import power.MaPower;
import power.NiuPower;

public class BullAndHorseMorphology extends CustomCard {
    public static final String ID = YibaMod.makeModID("BullAndHorseMorphology");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/BullAndHorseMorphology.png";

    private static final int COST = 0;

    public BullAndHorseMorphology() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        this.baseMagicNumber = 25;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new NiuPower(p, 1), 1));
        addToBot(new ApplyPowerAction(p, p, new MaPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p,99,false)));
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p,99,false)));
        addToBot(new ApplyPowerAction(p, p, new FrailPower(p,99,false)));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new BullAndHorseMorphology();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
        }
    }
}
