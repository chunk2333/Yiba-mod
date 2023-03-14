package cards.purple;
//明镜止水
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import power.*;

public class ThePeaceOfMind extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ThePeaceOfMind");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ThePeaceOfMind.png";
    private static final int COST = 1;
    public static final String ID = "ThePeaceOfMind";
    public ThePeaceOfMind() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        //设置 magicNumber
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new ApplyPowerAction(p, p, new ThePeaceOfMindPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ThePeaceOfMind();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}