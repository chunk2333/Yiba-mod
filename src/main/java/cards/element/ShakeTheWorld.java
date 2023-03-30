package cards.element;
//掀天揭地
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import actions.ShakeTheWorldAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;
import power.GeoPower;

public class ShakeTheWorld extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ShakeTheWorld");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/ShakeTheWorld.png";

    private static final int COST = -1;

    public static final String ID = "ShakeTheWorld";

    public ShakeTheWorld() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.GEO);
        this.exhaust = true;
        this.baseDamage = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new ApplyPowerAction(m, m, new GeoPower(m, YiBaHelper.getPlayerMystery()), 1));
        addToBot(new ShakeTheWorldAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ShakeTheWorld();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}
