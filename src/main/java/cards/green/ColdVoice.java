package cards.green;
//寒音索绕
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ColdVoice extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ColdVoice");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ColdVoice.png";
    private static final int COST = 2;
    public static final String ID = "ColdVoice";
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public ColdVoice() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        //设置 magicNumber
        this.baseDamage = 33;
        this.baseMagicNumber = 3;
        this.damage = this.baseDamage;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //给予所有敌人易伤
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, this.baseDamage, false), this.baseDamage, true));
            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, this.baseDamage, false), this.baseDamage, true));
            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, this.baseDamage, false), this.baseDamage, true));
        }
        //抽牌
        addToBot(new DrawCardAction(p, this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ColdVoice();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和费用
            upgradeName();
            upgradeBaseCost(1);

        }
    }
}