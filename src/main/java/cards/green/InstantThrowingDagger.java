package cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InstantThrowingDagger extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("InstantThrowingDagger");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/InstantThrowingDagger.jpg";
    private static final int COST = -2;
    public static final String ID = "InstantThrowingDagger";
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public InstantThrowingDagger() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseDamage = 10;
        this.damage = this.baseDamage;
        this.dontTriggerOnUseCard = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //this.dontTriggerOnUseCard = true;
    }
    @Override
    public void triggerOnManualDiscard() {
        addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = "不能被打出";
        return false;
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new InstantThrowingDagger();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高5点伤害
            upgradeName();
            upgradeDamage(5);
        }
    }
}
