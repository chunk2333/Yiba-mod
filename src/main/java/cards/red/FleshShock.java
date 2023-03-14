package cards.red;
//肉身震撼
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
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

public class FleshShock extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("FleshShock");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FleshShock.png";
    private static final int COST = 3;
    public static final String ID = "FleshShock";
    public FleshShock() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.baseMagicNumber = 50;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作

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
        int finaldamage;
        finaldamage = (int) (this.magicNumber * p.maxHealth * 0.01);
        addToBot(new DamageAction(m, new DamageInfo(p, finaldamage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new FleshShock();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(25);
        }
    }
}