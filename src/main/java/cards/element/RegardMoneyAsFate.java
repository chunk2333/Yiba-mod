package cards.element;
//视金如命
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import pathes.AbstractCardEnum;

public class RegardMoneyAsFate extends CustomCard {
    //吃不到敏捷和脆弱
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("RegardMoneyAsFate");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String ID = "RegardMoneyAsFate";

    private static final int COST = 2;

    public static final String IMG_PATH = "img/cards/witch/RegardMoneyAsFate.png";

    public RegardMoneyAsFate() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.UNCOMMON, CardTarget.SELF );
        this.baseBlock = 5;
        this.isEthereal = true;
        this.baseMagicNumber = 40;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        int goldNum = p.gold;
        int random;
        if(goldNum == 0 ){
            goldNum = 1;
        }else {
            goldNum = (int) (goldNum * this.baseMagicNumber * 0.01);
        }

        if(this.block <= 0){
            random = AbstractDungeon.cardRandomRng.random(1, goldNum); //随机数
        }else {
            random = AbstractDungeon.cardRandomRng.random(this.block, goldNum); //随机数
        }

        addToBot(new GainBlockAction(p, p, random));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new RegardMoneyAsFate();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(20);
        }
    }
}
