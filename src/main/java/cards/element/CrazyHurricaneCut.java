package cards.element;
//狂飓切裂
import YibaMod.YibaMod;
import actions.AnemoAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import patchs.AbstractCardEnum;

public class CrazyHurricaneCut extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("CrazyHurricaneCut");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/CrazyHurricaneCut.png";

    private static final int COST = 3;

    public static final String ID = "CrazyHurricaneCut";

    public CrazyHurricaneCut() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 18;
        this.isMultiDamage = true;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.ANEMO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //造成伤害
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        boolean canAnemo = false;
        //判断是否能扩散成功 和 给予扩散
        for(AbstractMonster mo :AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!mo.isDead){
                canAnemo = mo.hasPower("PyroPower") || mo.hasPower("HydroPower");
                addToBot(new AnemoAction(mo));
                if(canAnemo){
                    break;
                }
            }
        }

        //若扩散成功则额外造成一次伤害
        if(canAnemo){
            addToBot(new SFXAction("ATTACK_HEAVY"));
            addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
            addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }



    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new CrazyHurricaneCut();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}
