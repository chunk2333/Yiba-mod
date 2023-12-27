package cards.colorless;
//生命火焰
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlameOfLife extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("FlameOfLife");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/FlameOfLife.png";

    private static final int COST = 1;

    public static final String ID = "FlameOfLife";

    public FlameOfLife() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 25;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, (int) (p.maxHealth * this.magicNumber * 0.01)));
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!mo.isDead){
                addToBot(new StunMonsterAction(mo, p));
            }
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + (int) (AbstractDungeon.player.maxHealth * this.magicNumber * 0.01);
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }


    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() {
        return new FlameOfLife();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-10);
        }
    }
}
