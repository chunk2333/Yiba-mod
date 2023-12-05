package cards.colorless;
//完美谢幕
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.PerfectClosingPower;

public class PerfectClosing extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("PerfectClosing");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/PerfectClosing.png";

    private static final int COST = 1;

    public static final String ID = "PerfectClosing";

    public PerfectClosing() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("PerfectClosingPower")){
            if (p.getPower("PerfectClosingPower").amount != 0 ){
                addToBot(new ApplyPowerAction(p, p, new PerfectClosingPower(p, this.magicNumber - p.getPower("PerfectClosingPower").amount), this.magicNumber - p.getPower("PerfectClosingPower").amount));
            }
        } else {
            addToBot(new ApplyPowerAction(p, p, new PerfectClosingPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new PerfectClosing();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

}
