package cards.element;
//烈焰冲锋
import Tools.YiBaHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import pathes.AbstractCardEnum;
import power.PyroPower;

public class FlamingCharge extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("FlamingCharge");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/FlamingCharge.png";

    private static final int COST = 2;

    public static final String ID = "FlamingCharge";

    public FlamingCharge() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        //给予力量
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        //给予 火元素
        addToBot(new ApplyPowerAction(m, m, new PyroPower(m, YiBaHelper.getPlayerMystery()), 1));


    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new FlamingCharge();
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
