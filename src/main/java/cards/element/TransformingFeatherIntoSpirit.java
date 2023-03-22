package cards.element;
//化羽为灵
import actions.AnemoAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;
import power.MyFlightPower;

public class TransformingFeatherIntoSpirit extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("TransformingFeatherIntoSpirit");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/TransformingFeatherIntoSpirit.png";

    private static final int COST = 1;

    public static final String ID = "TransformingFeatherIntoSpirit";

    public TransformingFeatherIntoSpirit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.SELF_AND_ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //给飞行
        addToTop(new ApplyPowerAction(p, p, new MyFlightPower(p, this.magicNumber), this.magicNumber));
        //扩散
        addToBot(new AnemoAction(m));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new TransformingFeatherIntoSpirit();
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


