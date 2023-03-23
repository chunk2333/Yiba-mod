package cards.element;
//贯穿水枪
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import pathes.AbstractCardEnum;
import power.HydroPower;

public class PenetrationWaterGun extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("PenetrationWaterGun");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/PenetrationWaterGun.png";

    private static final int COST = 2;

    public static final String ID = "PenetrationWaterGun";

    public PenetrationWaterGun() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 10;
        this.damage = this.baseDamage;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.HYDRO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //判断是否能丢到抽牌堆
        boolean canAnemo = m.hasPower("PyroPower") || m.hasPower("GeoPower");
        this.shuffleBackIntoDrawPile = canAnemo;
        //给予水元素
        addToBot(new ApplyPowerAction(m, m, new HydroPower(m, YiBaHelper.getPlayerMystery()), 1));
        //造成伤害

        if (m != null){
            addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));


        //如果可以丢到抽牌堆则改为丢到弃牌堆
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new PenetrationWaterGun();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBaseCost(1);
        }
    }

}
