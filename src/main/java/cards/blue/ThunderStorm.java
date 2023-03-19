package cards.blue;
//雷雨交加
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

public class ThunderStorm extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("ThunderStorm");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/ThunderStorm.png";

    private static final int COST = 1;

    public static final String ID = "ThunderStorm";

    public ThunderStorm() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        int frostCount = 0;
        int lightningCount = 0;
        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof com.megacrit.cardcrawl.orbs.Frost)
                frostCount++;
        }
        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof com.megacrit.cardcrawl.orbs.Lightning)
                lightningCount++;
        }
        this.baseDamage = frostCount * this.magicNumber + lightningCount * this.magicNumber;
        calculateCardDamage(null);
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new BlizzardEffect(frostCount + lightningCount,
                    AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            addToBot(new VFXAction(new BlizzardEffect(frostCount + lightningCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
    }

    @Override
    public void applyPowers() {
        int frostCount = 0;
        int lightningCount = 0;
        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof com.megacrit.cardcrawl.orbs.Frost)
                frostCount++;
        }
        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof com.megacrit.cardcrawl.orbs.Lightning)
                lightningCount++;
        }

        if (frostCount > 0 || lightningCount >0) {
            this.baseDamage = frostCount * this.magicNumber + lightningCount * this.magicNumber;
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new ThunderStorm();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}