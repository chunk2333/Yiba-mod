package cards.element;
//疾风术
import YibaMod.YibaMod;
import actions.AnemoAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;

public class GaleSpell extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("GaleSpell");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/GaleSpell.png";

    private static final int COST = 1;

    public static final String ID = "GaleSpell";

    public GaleSpell() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        this.baseBlock = 5;
        this.block = this.baseBlock;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.ANEMO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //扩散
        addToBot(new AnemoAction(m));
        //获得格挡
        addToBot(new GainBlockAction(p, p, this.block));

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new GaleSpell();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}
