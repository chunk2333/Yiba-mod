package cards.green;
//诡谲
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import power.TreacherousPower;

public class Treacherous extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Treacherous");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Treacherous.png";
    private static final int COST = 2;
    public static final String ID = "Treacherous";
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public Treacherous() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        //设置 magicNumber
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //给予力量
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseMagicNumber), this.baseMagicNumber));
        //给予敏捷
        addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.baseMagicNumber), this.baseMagicNumber));
        //本场战斗所有技能牌降低1费
        addToBot(new ApplyPowerAction(p, p, new TreacherousPower(p, 1), 1));
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Treacherous();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和费用
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}