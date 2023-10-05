package cards.element;
//舞火流风
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import actions.AnemoAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import patchs.AbstractCardEnum;
import power.PyroPower;

public class DancingFireAndFlowingWind extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("DancingFireAndFlowingWind");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/DancingFireAndFlowingWind.png";

    private static final int COST = 0;

    public static final String ID = "DancingFireAndFlowingWind";

    public DancingFireAndFlowingWind() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.COMMON, CardTarget.ENEMY);
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.ANEMO);
        this.tags.add(YibaMod.PYRO);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //给予 火元素
        addToBot(new ApplyPowerAction(m, m, new PyroPower(m, YiBaHelper.getPlayerMystery()), 1));
        //给予 扩散
        addToBot(new AnemoAction(m));

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new DancingFireAndFlowingWind();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
