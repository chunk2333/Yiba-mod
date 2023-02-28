package cards;
//抽象

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Abstract extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Abstract");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/Abstract.png";
    private static final int COST = 0;
    public static final String ID = "Abstract";

    public Abstract() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        int random;
        random = AbstractDungeon.relicRng.random(1, 3); //随机数
        if (random == 1) {
            //力量 StrengthPower
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, -1), -1));
        }
        if (random == 2) {
            //敏捷 DexterityPower
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, -1), -1));
        }
        if (random == 3) {
            //集中 FocusPower
            addToTop(new ApplyPowerAction(p, p, new FocusPower(p, -1), -1));
        }
        random = AbstractDungeon.relicRng.random(1, 3); //随机数
        if (random == 1) {
            //力量 StrengthPower
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
        if (random == 2) {
            //敏捷 DexterityPower
            addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        }
        if (random == 3) {
            //集中 FocusPower
            addToTop(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Abstract();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}