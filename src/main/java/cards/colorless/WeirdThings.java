package cards.colorless;
//怪神乱力

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class WeirdThings extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("WeirdThings");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WeirdThings.png";
    private static final int COST = 1;
    public static final String ID = "WeirdThings";

    public WeirdThings() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

        int StrengthNum = 0;
        //取玩家身上的力量
        for(AbstractPower power : p.powers){
            if(power.ID.equals("Strength")){
                StrengthNum = power.amount;
                break;
            }
        }
        if(StrengthNum==0){
            return;
        }
        //获得临时力量
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, StrengthNum), StrengthNum));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, StrengthNum), StrengthNum));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new WeirdThings();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}