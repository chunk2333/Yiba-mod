
package relics;
//隐蔽陷阱
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CovertTrap extends CustomRelic {
    public static final String ID = "CovertTrap";

    private static final String IMG = "img/relics_Seles/CovertTrap.png";

    private static final String IMG_OTL = "img/relics_Seles/outline/CovertTrap.png";
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public CovertTrap() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.SOLID);
    }
    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
        //给予所有怪物15点伤害
        addToBot(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(15, true),
                DamageInfo.DamageType.THORNS,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        //给予所有怪物4层易伤
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, 4, false), 4, true));
        }
    }
    @Override
    public void atTurnStart(){
        //每回合开始时触发
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发
    }
    @Override
    public void onVictory() {
        //在胜利时触发
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    //拾取时触发
    public void onEquip() {
    }
    @Override
    public AbstractRelic makeCopy() {
        return new CovertTrap();
    }
}