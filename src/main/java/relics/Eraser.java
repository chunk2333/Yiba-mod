package relics;
//一块橡皮擦
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;

public class Eraser extends CustomRelic {

    public static final String ID = "Eraser";

    private static final String IMG = "img/relics/Eraser.png";

    private static final String IMG_OTL = "img/relics/outline/Eraser.png";

    public Eraser() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        boolean isUsed = false;

        for(AbstractMonster mo :AbstractDungeon.getCurrRoom().monsters.monsters){
            if(!mo.isDead){
                ArrayList<AbstractPower> power = mo.powers;
                for (AbstractPower po : power) {
                    addToBot(new RemoveSpecificPowerAction(mo, mo, po.ID));
                    isUsed = true;
                }
            }
        }

        if(isUsed){
            flash();
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Eraser();
    }
}
