package cards.purple;
//以德报怨
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.ReturnGoodForEvilPower;

public class ReturnGoodForEvil extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ReturnGoodForEvil");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/ReturnGoodForEvil.png";

    private static final int COST = 1;

    public static final String ID = "ReturnGoodForEvil";

    public ReturnGoodForEvil() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF);
        //设置 magicNumber
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new ApplyPowerAction(p, p, new ReturnGoodForEvilPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ReturnGoodForEvil();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和费用
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}