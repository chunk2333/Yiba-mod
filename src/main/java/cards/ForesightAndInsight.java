package cards;
//远见明察
import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import java.util.ArrayList;

public class ForesightAndInsight extends CustomCard {

    public static final String IMG_PATH = "img/cards_Seles/ForesightAndInsight.png";

    public static final String ID = "ForesightAndInsight";

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ForesightAndInsight");
    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 1;

    private int buffNum = 0;

    public ForesightAndInsight() {

        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY);

        this.baseDamage = 5;

        this.damage = this.baseDamage;

        this.baseMagicNumber = 1;

        this.magicNumber = this.baseMagicNumber;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        ArrayList<AbstractPower> power = m.powers;
        for (AbstractPower po : power) {
            UpdateStats.logger.info("目标buff：" + po.ID);
            this.buffNum = this.buffNum + 1;
            //移除对应buff
            addToBot(new RemoveSpecificPowerAction(m, m, po.ID));
        }
        //造成伤害
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p,
                        this.damage + this.damage * this.buffNum,
                        this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        //回血
        p.heal(1 + this.magicNumber * this.buffNum);
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ForesightAndInsight();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字
            upgradeName();
            //提高伤害
            upgradeDamage(2);
            //提高回复血量
            upgradeMagicNumber(1);
            this.selfRetain = true;

            this.rawDescription=cardStrings.UPGRADE_DESCRIPTION;

            initializeDescription();
        }
    }
}