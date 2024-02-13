package cards.colorless;
//绯红之王
import VFX.KingCrimsonEffect;
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.KingCrimsonPower;

public class KingCrimson extends CustomCard {
    public static final String ID = YibaMod.makeModID("KingCrimson");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/KingCrimson.png";

    private static final int COST = 1;


    public KingCrimson() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("KingCrimson", 0F);
        addToBot(new VFXAction(new KingCrimsonEffect(new Color(255.0F, 77.0F, 255.0F, 0.5F), false)));
        addToBot(new SkipEnemiesTurnAction());
        p.gainEnergy(p.energy.energyMaster);
        if (this.upgraded){
            addToBot(new DrawCardAction(this.magicNumber));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if (!m.isDying && !m.isDead){
                        addToBot(new ApplyPowerAction(m, m, new KingCrimsonPower(m, 1)));
                    }
                }
                tickDuration();
            }
        });

    }

    @Override
    public AbstractCard makeCopy() {
        return new KingCrimson();
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
