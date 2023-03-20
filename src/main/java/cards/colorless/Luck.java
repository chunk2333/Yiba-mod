package cards.colorless;
//运气
import YibaMod.YibaMod;
import actions.MyAttackDamageRandomEnemyAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Luck extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Luck");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Luck.png";

    private static final int COST = 2;

    public static final String ID = "Luck";

    public Luck() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 12;
        this.damage = this.baseDamage;
        this.baseMagicNumber = 8;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        int randomDamage;
        int randomMagicNumber;
        randomDamage = AbstractDungeon.cardRandomRng.random(0, this.damage);
        YibaMod.logger.info("[运气]：最大点伤害：" + this.damage);
        randomMagicNumber = AbstractDungeon.cardRandomRng.random(0, this.magicNumber);
        YibaMod.logger.info("[运气]：造成" + randomDamage + "点" + randomMagicNumber + "次");
        for (int i = 0; i < randomMagicNumber; i++){
            addToBot(new MyAttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING, randomDamage));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Luck();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(2);
        }
    }
}
