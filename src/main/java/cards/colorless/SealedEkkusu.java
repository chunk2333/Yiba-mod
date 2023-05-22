package cards.colorless;
//被封印的艾克佐迪亚
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class SealedEkkusu extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SealedEkkusu");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/SealedEkkusu.png";

    private static final int COST = -2;

    public static final String ID = "SealedEkkusu";

    public SealedEkkusu() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        boolean a = false;
        boolean b = false;
        boolean e = false;
        boolean d = false;
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card.cardID=="SealedLeftFoot"){
                a = true;
            }
            if(card.cardID=="SealedRightFoot"){
                b = true;
            }
            if(card.cardID=="SealedRightArm"){
                d = true;
            }
            if(card.cardID=="SealedLeftArm"){
                e = true;
            }
        }
        if(a&&b&&d&&e){
            //秒杀
            //AbstractPlayer p = AbstractDungeon.player;
            //addToBot(new VFXAction(new LightningEffect(p.hb.cX, p.hb.cY)));
            //addToBot(new LoseHPAction(p, p, 99999));
            killAllMonsters(false);
        }
    }

    @Override
    public void triggerWhenDrawn() {
        triggerOnOtherCardPlayed(new SealedLeftArm());
    }

    public void killAllMonsters(boolean isKillAllMode) {
        if (AbstractDungeon.getMonsters() == null)
            return;
        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
            if (monster != null) {
                AbstractDungeon.actionManager.addToTop(new InstantKillAction(monster));
                if (!isKillAllMode) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY)));
                }
            }
        }
    }
    @Override
    public void triggerWhenCopied() {
        triggerOnOtherCardPlayed(new SealedLeftArm());
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        triggerOnOtherCardPlayed(new SealedLeftArm());
        return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SealedEkkusu();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和提高3点伤害
            upgradeName();
        }
    }
}
