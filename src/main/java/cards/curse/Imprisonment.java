package cards.curse;
//坐牢-诅咒牌
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Imprisonment extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Imprisonment");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Imprisonment.png";
    private static final int COST = -2;
    public static final String ID = "Imprisonment";
    public Imprisonment() {
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
        //不能被打出
        this.dontTriggerOnUseCard = true;
        //虚无
        this.isEthereal = true;
        //结束玩家回合
        addToBot(new PressEndTurnButtonAction());
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Imprisonment();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
    }
}