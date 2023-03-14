//SumUpExperience
//总结经验
package cards.green;
import actions.UpgradeRadmoSkillCard;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SumUpExperience extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SumUpExperience");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SumUpExperience.png";
    private static final int COST = 1;
    public static final String ID = "SumUpExperience";
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public SumUpExperience() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
        this.damage = this.baseDamage;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new UpgradeRadmoSkillCard(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new SumUpExperience();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和费用
            upgradeName();
            upgradeDamage(5);
        }
    }
}
