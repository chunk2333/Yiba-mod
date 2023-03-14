

//public class AnemoCard {

package cards.element;

//public class GeoCard {
//岩元素牌
import actions.AnemoActions;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import pathes.AbstractCardEnum;
import power.GeoPower;

public class AnemoCard extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("AnemoCard");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/test.png";
    private static final int COST = 0;
    public static final String ID = "AnemoCard";
    public AnemoCard() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Seles_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.damage = this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        int mystery = 0;
        for(AbstractPower power:p.powers){
            if(power.ID=="MysteryPower"){
                mystery = power.amount;
            }
        }
        //给予扩散动作
        addToBot(new AnemoActions(m));
        //造成伤害
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new AnemoCard();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}