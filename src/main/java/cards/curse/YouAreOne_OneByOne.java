
//public class YouAreOne_OneByOne {
package cards.curse;
//你是一个，一个一个-诅咒牌
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;

public class YouAreOne_OneByOne extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("YouAreOne_OneByOne");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/YouAreOne_OneByOne.png";
    private static final int COST = -2;
    public static final String ID = "YouAreOne_OneByOne";
    public YouAreOne_OneByOne() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        //不能被打出
        this.dontTriggerOnUseCard = true;
        //虚无
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
    }
    @Override
    public void triggerWhenDrawn() {
        //抽到时触发
        AbstractPlayer p = AbstractDungeon.player;
        //不能被打出
        this.dontTriggerOnUseCard = true;
        //虚无
        this.isEthereal = true;
        //给予无法抽卡的buff
        addToBot(new ApplyPowerAction(p, p, new NoDrawPower(p)));
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new YouAreOne_OneByOne();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
    }
}