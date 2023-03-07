package cards.curse;
//孝子
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
public class DutifulSon extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("DutifulSon");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/DutifulSon.png";
    private static final int COST = -2;
    public static final String ID = "DutifulSon";
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public DutifulSon() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        //添加基础攻击标签和将伤害设为6
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
        addToBot(new ChangeStanceAction("Wrath"));
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new DutifulSon();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
    }
}
