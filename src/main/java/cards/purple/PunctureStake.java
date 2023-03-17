package cards.purple;
//穿刺木桩
import basemod.abstracts.CustomCard;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PunctureStake extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("PunctureStake");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PunctureStake.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 20;
    public static final String ID = "PunctureStake";
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public PunctureStake() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.RARE, CardTarget.ENEMY);
        //添加基础攻击标签和将伤害设为6
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        int random;
        random = AbstractDungeon.cardRandomRng.random(1,100); //随机数
        double monsterHP;
        double monsterLoseHP;
        double monsterMaxHP;
        double PR;
        monsterMaxHP = m.maxHealth;
        monsterHP = m.currentHealth;
        monsterLoseHP = monsterMaxHP - monsterHP;
        YibaMod.logger.info("怪物剩余最大血量：" + monsterMaxHP  +"当前血量："+monsterHP);
        YibaMod.logger.info("怪物已损失血量：" + monsterLoseHP );

        PR = (monsterLoseHP / monsterMaxHP) * 100;
        PR = Math.toIntExact(Math.round(PR));
        PR= PR * this.magicNumber * 0.1;
        YibaMod.logger.info("怪物已损失血量百分比：" + PR);
        YibaMod.logger.info("怪物剩余血量：" + monsterHP );
        PR = PR + this.magicNumber;//加上基础斩杀概率

        YibaMod.logger.info("穿刺木桩斩杀概率：" + PR + "随机数："+ random);
        if(random<=PR){
            YibaMod.logger.info("触发斩杀");
            this.damage = 9999999;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.damage = this.baseDamage;
        if(this.upgraded){

            this.damage = this.damage+3;
        }
    }

    @Override

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new PunctureStake();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeDamage(5);
            upgradeMagicNumber(2);
        }
    }
}