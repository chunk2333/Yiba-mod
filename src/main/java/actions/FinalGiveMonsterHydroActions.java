package actions;

import Tools.YiBaHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.GeoPower;
import power.HydroPower;
import power.PyroPower;

public class FinalGiveMonsterHydroActions extends AbstractGameAction {

    private final AbstractMonster m;

    private final String name;

    public FinalGiveMonsterHydroActions(AbstractMonster monster , String ElementName){
        this.m = monster;
        this.name = ElementName;
        addToBot(new RemoveSpecificPowerAction(this.m, this.m, "GeoPower"));
        addToBot(new RemoveSpecificPowerAction(this.m, this.m, "HydroPower"));
        addToBot(new RemoveSpecificPowerAction(this.m, this.m, "PyroPower"));
    }

    @Override
    public void update(){
        switch (name){
            case "HydroPower":
                addToBot(new ApplyPowerAction(this.m, this.m, new HydroPower(this.m, YiBaHelper.getPlayerMystery()), 1));
                break;
            case "GeoPower":
                addToBot(new ApplyPowerAction(this.m, this.m, new GeoPower(this.m, YiBaHelper.getPlayerMystery()), 1));
                break;
            case "PyroPower":
                addToBot(new ApplyPowerAction(this.m, this.m, new PyroPower(this.m, YiBaHelper.getPlayerMystery()), 1));
                break;
            default:
                break;

        }
        if(this.m.hasPower(name)){
            tickDuration();
        }

        tickDuration();
    }
}
