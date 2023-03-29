package cards.element;
//隐身雾色
import actions.StealthFogColorAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;

public class StealthFogColor extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("StealthFogColor");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/test.png";

    private static final int COST = 1;

    public static final String ID = "StealthFogColor";

    private static final int BLOCK_AMOUNT = 3;

    private static final int UPGRADE_PLUS_BLOCK = 1;

    private static final int BLOCK_DIFFERENCE = 6;

    private static final int UPGRADE_PLUS_BLOCK_DIFFERENCE = 4;

    public StealthFogColor() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.COMMON, CardTarget.SELF);
        this.block = this.baseBlock = 3;
        this.magicNumber = this.baseMagicNumber = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        applyPowers();
        addToBot(new StealthFogColorAction(p, this.block, this.magicNumber));
    }

    @Override
    public void applyPowers() {
        this.baseBlock += 6 + this.timesUpgraded * 4;
        this.baseMagicNumber = this.baseBlock;
        super.applyPowers();
        this.magicNumber = this.block;
        this.isMagicNumberModified = this.isBlockModified;
        this.baseBlock -= 6 + this.timesUpgraded * 4;
        super.applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new StealthFogColor();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(1);
            this.baseMagicNumber = this.baseBlock + 6 + this.timesUpgraded * 4;
            this.upgradedMagicNumber = this.upgradedBlock;
        }
    }
}
