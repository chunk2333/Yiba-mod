package cards.curse;
//骤停
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class SuddenStop extends CustomCard {
    public static final String ID = YibaMod.makeModID("SuddenStop");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/test.png";

    private static final int COST = -2;

    int drawTimes = 0;

    public SuddenStop() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.dontTriggerOnUseCard = true;
        this.tags.add(YibaMod.VANISH);
        this.tags.add(YibaMod.LAST);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerOnGlowCheck() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + this.drawTimes;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void triggerWhenDrawn() {
        this.drawTimes += 1;
        if (this.drawTimes >= 4){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            for (int i = 0; i < 20; i++){
                                AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
                            }
                            tickDuration();
                        }
                    });
                    addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 114514));
                    tickDuration();
                }
            });
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuddenStop();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}
