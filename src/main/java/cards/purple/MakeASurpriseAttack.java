package cards.purple;
//虚诱掩杀
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import power.DoubleDamage;

public class MakeASurpriseAttack extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("MakeASurpriseAttack");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/MakeASurpriseAttack.png";
    private static final int COST = 1;
    public static final String ID = "MakeASurpriseAttack";
    public MakeASurpriseAttack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.damage = this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new ApplyPowerAction(p, p, new DoubleDamage(p, 1), 1));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new MakeASurpriseAttack();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeDamage(3);
        }
    }
}