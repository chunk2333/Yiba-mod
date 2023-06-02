package cards.purple;
//天地同寿
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

    public static final String IMG_PATH = "img/cards/LongevityInHeavenAndEarth.png";

    private static final int COST = 4;

    public static final String ID = "LongevityInHeavenAndEarth";

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public LongevityInHeavenAndEarth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 30;
        this.damage = this.baseDamage;
        this.exhaust = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
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
            //upgradeDamage(10);
            //设置保留
            this.selfRetain = true;
            //读取升级后的描述
            this.rawDescription = UPGRADE_DESCRIPTION;
            //重载描述
            initializeDescription();
        }
    }
}