package cards.element;
//飓风呼啸
import YibaMod.YibaMod;
import actions.AnemoAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;

public class TheRoarOfAHurricane extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("TheRoarOfAHurricane");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/TheRoarOfAHurricane.png";

    private static final int COST = 1;

    public static final String ID = "TheRoarOfAHurricane";

    public TheRoarOfAHurricane() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        this.baseBlock = 5;
        this.tags.add(YibaMod.ANEMO);
        this.tags.add(YibaMod.ELEMENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //扩散
        addToBot(new AnemoAction(m));
        //给防御，存活怪物数次
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!mo.isDead){
                addToBot(new GainBlockAction(p, p, this.block));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new TheRoarOfAHurricane();
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
