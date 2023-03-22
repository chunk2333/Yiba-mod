package cards.element;
//水精通
import Tools.YiBaHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;
import power.HydroPower;
import power.MysteryPower;

public class WaterProficiency extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("WaterProficiency");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/WaterProficiency.png";

    private static final int COST = 2;

    public static final String ID = "WaterProficiency";

    public WaterProficiency() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.SELF_AND_ENEMY);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        //给予元素精通
        addToBot(new ApplyPowerAction(p, p, new MysteryPower(p, this.magicNumber),this.magicNumber));
        //给予 水元素
        addToBot(new ApplyPowerAction(m, m, new HydroPower(m, YiBaHelper.getPlayerMystery()), 1));


    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new WaterProficiency();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
