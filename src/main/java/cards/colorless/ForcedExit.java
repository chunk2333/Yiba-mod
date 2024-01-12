package cards.colorless;
//强制退场
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class ForcedExit extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ForcedExit");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/ForcedExit.png";

    private static final int COST = 0;

    public static final String ID = "ForcedExit";

    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString("Escape");

    public ForcedExit() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.exhaust = true;
        this.isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m.type == AbstractMonster.EnemyType.BOSS){
            return;
        }
        addToBot(new TalkAction(m, potionString.DESCRIPTIONS[1], 0.3F, 2.5F));
        addToBot(new VFXAction(new SmokeBombEffect(m.hb.cX, m.hb.cY)));
        addToBot(new EscapeAction(m));
        AbstractDungeon.player.masterDeck.removeCard(this.cardID);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster mo){
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(m.type == AbstractMonster.EnemyType.BOSS){
                this.cantUseMessage = (CardCrawlGame.languagePack.getUIString("IsBoss")).TEXT[0];
                return false;
            }
        }
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ForcedExit();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
