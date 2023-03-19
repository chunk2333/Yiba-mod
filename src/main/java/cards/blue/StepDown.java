package cards.blue;
//降压
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StepDown extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("StepDown");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/StepDown.png";

    private static final int COST = 1;

    public static final String ID = "StepDown";

    public StepDown() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //获得集中
        addToTop(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
        //随机失去1力量或敏捷
        int random;
        random = AbstractDungeon.cardRandomRng.random(1, 2); //随机数
        if (random == 1) {
            //力量 StrengthPower
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -1), -1));
        }
        if (random == 2) {
            //敏捷 DexterityPower
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, -1), -1));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new StepDown();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}

