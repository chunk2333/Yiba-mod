package cards.element;
//石破天惊
import Tools.YiBaHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;
import power.GeoPower;

public class Earthshaking extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Earthshaking");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/test.png";

    private static final int COST = 2;

    public static final String ID = "Earthshaking";

    public Earthshaking() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.baseMagicNumber = 3;
        this.baseDamage = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //addToBot(new ApplyPowerAction(p, p, new MysteryPower(p, this.magicNumber),this.magicNumber));
        //给予岩元素
        addToBot(new ApplyPowerAction(m, m, new GeoPower(m, YiBaHelper.getPlayerMystery()), 1));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(this.upgraded){
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Earthshaking();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            upgradeMagicNumber(1);
        }
    }
}
