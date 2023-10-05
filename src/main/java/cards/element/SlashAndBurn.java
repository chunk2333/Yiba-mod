package cards.element;
//火耕水耨
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import patchs.AbstractCardEnum;
import power.HydroPower;
import power.PyroPower;

public class SlashAndBurn extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("SlashAndBurn");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/SlashAndBurn.png";

    private static final int COST = 4;

    public static final String ID = "SlashAndBurn";

    public SlashAndBurn() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.ENEMY);
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.PYRO);
        this.tags.add(YibaMod.HYDRO);
        this.baseDamage = 25;
        this.baseMagicNumber = 30;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        if(YiBaHelper.hasPyroElement(m) && !YiBaHelper.hasHydroElement(m)){
            addToBot(new ApplyPowerAction(m, m, new HydroPower(m, YiBaHelper.getPlayerMystery()),1));
        }
        if(YiBaHelper.hasHydroElement(m) && !YiBaHelper.hasPyroElement(m)){
            addToBot(new ApplyPowerAction(m, m, new PyroPower(m, YiBaHelper.getPlayerMystery()),1));
        }

        if(!YiBaHelper.hasPyroElement(m) && !YiBaHelper.hasHydroElement(m)){
            int random;
            random = AbstractDungeon.cardRandomRng.random(1, 2); //随机数
            if(random == 1){
                addToBot(new ApplyPowerAction(m, m, new HydroPower(m, YiBaHelper.getPlayerMystery()),1));
            }else {
                addToBot(new ApplyPowerAction(m, m, new PyroPower(m, YiBaHelper.getPlayerMystery()),1));
            }
        }

        if (m != null)
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new DamageAllEnemiesAction(p, (int) (this.damage * this.magicNumber * 0.01), this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));


    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new SlashAndBurn();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
            upgradeMagicNumber(10);
        }
    }
}
