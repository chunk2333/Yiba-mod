package cards;
//碎骨
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.StanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class PeopleAndGodsAreOutraged extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("PeopleAndGodsAreOutraged");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/test.png";
    private static final int COST = 1;
    public static final String ID = "PeopleAndGodsAreOutraged";
    public PeopleAndGodsAreOutraged() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        //添加基础攻击标签和将伤害设为6
        this.baseDamage = 10;

        this.damage = this.baseDamage;

        this.baseMagicNumber = 2;

        this.magicNumber = this.baseMagicNumber;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //进入愤怒状态
        addToBot(new ChangeStanceAction("Wrath"));
        //AbstractStance.getStanceFromName()
        if(p.stance.ID.equals("Calm") || p.stance.ID.equals("Divinity") ||p.stance.ID.equals("Neutral")){
            this.damage=this.damage*2;
        }
        //给予伤害
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        //获得2点临时力量
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        if(this.upgraded){
            this.damage=14;
        }else {
            this.damage=10;
        }


    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new PeopleAndGodsAreOutraged();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeDamage(4);
            upgradeMagicNumber(1);
        }
    }
}
