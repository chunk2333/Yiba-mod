package cards.curse;
//三大欲望
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class desire extends CustomCard{

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("desire");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/desire.png";
    private static final int COST = -2;
    public static final String ID = "desire";
    //public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public desire() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        //添加基础攻击标签和将伤害设为6
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.dontTriggerOnUseCard=true;
        this.isEthereal = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
    }
    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        //回合结束
        this.dontTriggerOnUseCard = true;
        this.isEthereal=true;
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p,4,false)));
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -1), -1));
        addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, -1), -1));
        //YibaMod.logger.info("");
        //AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
        //addToBot(new ApplyPowerAction(p, p, new PoisonPower((AbstractCreature)p, this.magicNumber));
    }
    @Override

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return (AbstractCard)new desire();
    }
    @Override

    public boolean isStrike() {
        //是否是最基础攻击牌，
        return false;
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {

        }
    }
}
