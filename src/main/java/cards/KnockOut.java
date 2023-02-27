//KnockOut
//最后一击

package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.DamagePerAttackPlayedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KnockOut extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("KnockOut");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/test.png";
    private static final int COST = 0;
    public static final String ID = "KnockOut";
    private int num = 0;

    public KnockOut() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 6;
        this.damage = this.baseDamage;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(c.type==CardType.SKILL){
            triggerOnDiscard();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //addToBot(new DamagePerAttackPlayedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        for (int i = 0; i < num; i++) {
            addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void atTurnStart() {
        this.num = 0;
    }

    public void triggerOnDiscard() {
        this.num = this.num + 1;
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + num;
        if (num == 1) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new KnockOut();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和减1费
            upgradeName();
            upgradeDamage(2);
        }
    }
}
