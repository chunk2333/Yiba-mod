package actions;

import Tools.YiBaHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.GeoPower;
import power.HydroPower;
import power.PyroPower;

public class GiveRandomElement extends AbstractGameAction {

    AbstractMonster m;

    public GiveRandomElement(AbstractMonster mo){
        this.m = mo;
    }

    @Override
    public void update() {
        int random;
        random = AbstractDungeon.cardRandomRng.random(1, 3); //随机数
        switch (random){
            case 1:
                addToBot(new ApplyPowerAction(this.m, this.m, new HydroPower(this.m, YiBaHelper.getPlayerMystery()), 1));
                break;
            case 2:
                addToBot(new ApplyPowerAction(this.m, this.m, new GeoPower(this.m, YiBaHelper.getPlayerMystery()), 1));
                break;
            case 3:
                addToBot(new ApplyPowerAction(this.m, this.m, new PyroPower(this.m, YiBaHelper.getPlayerMystery()), 1));
                break;
            default:
                break;
        }
        tickDuration();
    }
}
