package cards.element;
//柔水盾
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;
import power.HydroPower;

public class SoftWaterShield extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SoftWaterShield");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/SoftWaterShield.png";

    private static final int COST = 1;

    public static final String ID = "SoftWaterShield";

    public SoftWaterShield() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Witch_COLOR, CardRarity.UNCOMMON, CardTarget.SELF_AND_ENEMY);
        this.baseBlock = 9;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.HYDRO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new GainBlockAction(p, p, this.block));
        if (m != null && (m.intent == AbstractMonster.Intent.ATTACK ||
                m.intent == AbstractMonster.Intent.ATTACK_BUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEBUFF ||
                m.intent == AbstractMonster.Intent.ATTACK_DEFEND)){
            addToBot(new ApplyPowerAction(m, m, new HydroPower(m, YiBaHelper.getPlayerMystery()), 1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new SoftWaterShield();
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
