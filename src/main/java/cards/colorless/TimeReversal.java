package cards.colorless;
//时光倒流
import actions.WaitActionYibaAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeReversal extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("TimeReversal");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/TimeReversal.png";
    private static final int COST = 3;
    public static final String ID = "TimeReversal";
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public TimeReversal() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        //消耗
        this.exhaust = true;
        //虚无
        this.isEthereal = true;
        //添加 HEALING 标签 使其无法在战斗中出现
        this.tags.add(AbstractCard.CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("TimeReversal", 0F);
        addToBot(new WaitActionYibaAction(2.2F));
        //移除所有debuff
        addToBot(new RemoveDebuffsAction(p));
        //回满血
        p.heal(p.maxHealth);
        //回满能量
        p.gainEnergy(p.energy.energyMaster);
        //移除牌库
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TimeReversal();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
            this.isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}