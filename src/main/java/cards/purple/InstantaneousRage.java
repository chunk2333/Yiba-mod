package cards.purple;
//一瞬狂暴
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

public class InstantaneousRage extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("InstantaneousRage");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/InstantaneousRage.png";

    private static final int COST = 1;

    public static final String ID = "InstantaneousRage";

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public InstantaneousRage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 5;
        this.damage = this.baseDamage;
        //保留
        this.selfRetain = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        //造成伤害
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        //如果是愤怒则进入平静否则进入愤怒
        if(p.stance.ID.equals("Wrath")){
            addToBot(new ChangeStanceAction("Calm"));
        }else {
            addToBot(new ChangeStanceAction("Wrath"));
        }
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new InstantaneousRage();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和伤害
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}