//HouseholdRegister
package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.unique.GainEnergyIfDiscardAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;

public class HouseholdRegister extends CustomRelic {
    public static final String ID = "HouseholdRegister";
    private static final String IMG = "img/relics/HouseholdRegister.png";
    private static final String IMG_OTL = "img/relics/outline/HouseholdRegister.png";
    public boolean isAcative;
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public HouseholdRegister() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        //头顶出现遗物特效
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 5), 5));
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
        isAcative = false;

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
        return new HouseholdRegister();
    }
}
