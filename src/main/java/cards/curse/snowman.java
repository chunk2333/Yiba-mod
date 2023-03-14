package cards.curse;
//食雪汉
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
public class snowman extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("snowman");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/snowman.png";
    private static final int COST = -2;
    public static final String ID = "snowman";
    public snowman() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.baseMagicNumber = 3;
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
        this.dontTriggerOnUseCard = true;
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new PoisonPower(p,p,this.magicNumber), this.magicNumber));
        UpdateStats.logger.info("食雪汉开始中毒");
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new snowman();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
    }
}