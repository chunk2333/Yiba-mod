//LongevityInHeavenAndEarth
//天地同寿
package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LongevityInHeavenAndEarth extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("LongevityInHeavenAndEarth");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/LongevityInHeavenAndEarth.png";
    private static final int COST = 4;
    public static final String ID = "LongevityInHeavenAndEarth";
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public LongevityInHeavenAndEarth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 30;
        this.damage = this.baseDamage;
        this.exhaust = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        //造成伤害
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        //进入神格
        addToBot(new ChangeStanceAction("Divinity"));
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new LongevityInHeavenAndEarth();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和伤害
            upgradeName();
            upgradeDamage(10);
            //设置保留
            this.selfRetain = true;
            //读取升级后的描述
            this.rawDescription = UPGRADE_DESCRIPTION;
            //重载描述
            initializeDescription();
        }
    }
}
