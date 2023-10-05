package cards.element;
//怒海狂涛
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import actions.FinalGiveMonsterHydroActions;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import power.HydroPower;

import java.util.ArrayList;

public class AngrySea extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("AngrySea");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/AngrySea.png";

    private static final int COST = 2;

    public static final String ID = "AngrySea";

    public AngrySea() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.baseDamage = 5;
        this.isMultiDamage = true;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.HYDRO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        ArrayList<AbstractMonster> monster = new ArrayList<>();
        int HydroCardNum = YiBaHelper.getTagsCardNum(YibaMod.HYDRO);
        int finalDamage = this.baseDamage * HydroCardNum;
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if(YiBaHelper.canTriggerElement(mo,"HydroPower")){
                monster.add(mo);
            }
            //给予水元素
            addToBot(new ApplyPowerAction(mo, mo, new HydroPower(mo, YiBaHelper.getPlayerMystery()), 1));
        }
        //造成伤害
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, finalDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        //如果触发了元素反应就再给予 水元素
        for(AbstractMonster mo : monster){
            addToBot(new FinalGiveMonsterHydroActions(mo,"HydroPower"));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        //抽到时触发
        int HydroCardNum = YiBaHelper.getTagsCardNum(YibaMod.HYDRO);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + HydroCardNum + cardStrings.EXTENDED_DESCRIPTION[1];
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
        return new AngrySea();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }

}
