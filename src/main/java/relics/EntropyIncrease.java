package relics;
//熵增
import basemod.abstracts.CustomRelic;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.EnergyPanel", method = "addEnergy")
public class EntropyIncrease extends CustomRelic{
    public static final String ID = "EntropyIncrease";

    private static final String IMG = "img/relics/EntropyIncrease.png";

    private static final String IMG_OTL = "img/relics/outline/EntropyIncrease.png";

    private static boolean isGive = false;

    public EntropyIncrease() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.HEAVY);
    }

    @SpireInsertPatch(loc = 80, localvars = {"e"})
    public static void Insert(int e){
        AbstractPlayer p = AbstractDungeon.player;
        if(isGive){
            isGive = false;
        }else {
            if(p.hasRelic("EntropyIncrease")){
                isGive = true;
                EntropyIncrease en = new EntropyIncrease();
                AbstractDungeon.player.getRelic("EntropyIncrease").flash();
                en.addToTop(new RelicAboveCreatureAction(p, en));
                p.gainEnergy(1);
                UpdateStats.logger.info("熵增触发：addEnergy");
            }
        }
    }
    public void myFlash(){
        flash();
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        isGive = false;
        //给予虚弱
        //addToBot(new ApplyPowerAction(p, p, new WeakPower(p,2,false)));
        //给予易伤
        //addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p,2,false)));
    }

    @Override
    public void atTurnStart(){
        //每回合开始时触发
        isGive = false;
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
        //拾取时触发
    }
    @Override
    public void onUnequip() {
        //丢弃时触发
    }
    @Override
    public AbstractRelic makeCopy() {
        return new EntropyIncrease();
    }
}