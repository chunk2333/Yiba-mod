package cards.element;
//爆裂魔法
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import actions.ExplosionDaiLogAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import pathes.AbstractCardEnum;
import power.GeoPower;
import power.NextTurnCanNotPlayAttackCardPower;

public class Explosion extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Explosion");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/Explosion.png";

    private static final int COST = 3;

    public static final String ID = "Explosion";

    public Explosion() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 20;
        this.damage = this.baseDamage;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.GEO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //addToBot(new SFXAction(YiBaHelper.MakeSoundPath("Explosion")));
        //addToBot(new ExplosionDaiLogAction(1,0.0F));
        //for(int i = 1; i < 9;i++){
         //   addToBot(new ExplosionDaiLogAction(i,5.0F));
        //}

        //给予岩元素
        addToBot(new ApplyPowerAction(m, m, new GeoPower(m, YiBaHelper.getPlayerMystery()), 1));
        //造成伤害，同重锤代码
        if (m != null)
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        addToBot(new WaitAction(0.8F));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

        //给予下回合开始时触发丢网效果
        addToTop(new ApplyPowerAction(p, p, new NextTurnCanNotPlayAttackCardPower(p), 1));



    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Explosion();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(8);
        }
    }
}
