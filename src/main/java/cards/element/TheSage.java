package cards.element;
//贤者
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;
import power.MysteryPower;
import power.TheSagePower;

public class TheSage extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("TheSage");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/TheSage.png";

    private static final int COST = 1;

    public static final String ID = "TheSage";

    public TheSage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 15;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new ApplyPowerAction(p, p, new MysteryPower(p, this.magicNumber),this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new TheSagePower(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new TheSage();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(5);
        }
    }
}
