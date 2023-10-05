package cards.element;
//全能全知
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;
import patchs.AbstractCardEnum;

public class OmnipotentAndOmniscient extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("OmnipotentAndOmniscient");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String ID = "OmnipotentAndOmniscient";

    private static final int COST = 1;

    public static final String IMG_PATH = "img/cards/witch/OmnipotentAndOmniscient.png";

    public OmnipotentAndOmniscient() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.SELF );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //升级全部卡牌
        addToBot(new ApotheosisAction());
        //失去费用
        if(this.upgraded){
            addToBot(new ApplyPowerAction(p, p, new EnergyDownPower(p, 1, true), 1));
        }else {
            addToBot(new ApplyPowerAction(p, p, new EnergyDownPower(p, 2, true), 2));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new OmnipotentAndOmniscient();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
