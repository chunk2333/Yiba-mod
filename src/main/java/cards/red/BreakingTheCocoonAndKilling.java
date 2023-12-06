package cards.red;
//破茧杀戮
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

public class BreakingTheCocoonAndKilling extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("BreakingTheCocoonAndKilling");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/BreakingTheCocoonAndKilling.png";

    private static final int COST = 2;

    public static final String ID = "FleshShock";

    public BreakingTheCocoonAndKilling() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
    }

    public int calculate(AbstractPlayer p, AbstractMonster m){
        int finaldamage = 0;
        for (AbstractCard card : p.hand.group){
            if (card.type == CardType.ATTACK){
                card.calculateCardDamage(m);
                finaldamage += card.damage;
            }
        }

        return finaldamage;
    }
    public int calculate(AbstractPlayer p){
        int finaldamage = 0;
        for (AbstractCard card : p.hand.group){
            if (card.type == CardType.ATTACK){
                finaldamage += card.damage;
            }
        }

        return finaldamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int finaldamage = 0;

        finaldamage = this.calculate(p, m);

        if (finaldamage == 0){
            return;
        }

        if (p.hasPower("ForbiddenCursePower")){
            finaldamage *= 1.5;
        }

        //残杀特效
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));
            for (int i = 0; i < 5; i++)
                addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
        } else {
            addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));
            for (int i = 0; i < 5; i++)
                addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
        }
        //造成伤害
        addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void triggerOnGlowCheck() {
        int damge = 0;
        damge = this.calculate(AbstractDungeon.player);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + damge;
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
        //复制卡牌时触发
        return new BreakingTheCocoonAndKilling();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
