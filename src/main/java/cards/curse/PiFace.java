package cards.curse;
//批脸
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PiFace extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("PiFace");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/PiFace.png";

    private static final int COST = -2;

    public static final String ID = "PiFace";

    public PiFace() {
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
        int count = AbstractDungeon.player.hand.size();
        int i;
        for (i = 0; i < count; i++) {
            if (Settings.FAST_MODE) {
                addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            } else {
                addToTop(new ExhaustAction(1, true, true));
            }
        }
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new PiFace();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
    }
}
