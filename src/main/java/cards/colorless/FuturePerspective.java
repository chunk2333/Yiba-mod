package cards.colorless;
//未来视角
import YibaMod.YibaMod;
import actions.FuturePerspectiveActions;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FuturePerspective extends CustomCard {
    public static final String ID = YibaMod.makeModID("FuturePerspective");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/FuturePerspective.png";

    private static final int COST = 2;

    public FuturePerspective() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FuturePerspectiveActions(this.upgraded));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if (!m.isDead && !m.isDying){
                        m.takeTurn();
                        m.applyPowers();
                    }
                }
                tickDuration();
            }
        });
    }

    @Override
    public AbstractCard makeCopy() {
        return new FuturePerspective();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
