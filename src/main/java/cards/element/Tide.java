package cards.element;
// 潮水啊，我已归来
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import patchs.AbstractCardEnum;
import power.HydroPower;
import power.TidePower;

public class Tide extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Tide");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/test.png";

    private static final int COST = 3;

    public static final String ID = "Tide";

    public Tide() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 40;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.HYDRO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int finalDamage;
        //给予水元素


        //特效
        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
            if (monster != null) {
                addToBot(new ApplyPowerAction(monster, monster, new HydroPower(monster, YiBaHelper.getPlayerMystery()), 1));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY)));
            }
        }

        //造成伤害
        finalDamage = (int) (this.damage * p.maxHealth * 0.01);
        addToBot(new DamageAllEnemiesAction(p, finalDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        //给予后续造成伤害的buff
        addToBot(new ApplyPowerAction(p, p, new TidePower(p, this.magicNumber), this.magicNumber));


    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Tide();
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
