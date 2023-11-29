package actions;

import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import power.MyCurlUpPower;
import power.MyFlightPower;

import java.util.ArrayList;

public class StealBuffAction extends AbstractGameAction {

    private final AbstractMonster m;

    private final AbstractPlayer p = AbstractDungeon.player;

    public ArrayList<AbstractPower> powersBuff = new ArrayList();

    private int num = 1;

    public StealBuffAction(AbstractMonster mo){
        this.m = mo;
    }

    @Override
    public void update(){
        int random;
        int buffSize = 0;
        AbstractPower FinalBuff = null;
        for (AbstractPower power : this.m.powers){
            if (power.type == AbstractPower.PowerType.BUFF){
                buffSize += 1;
                powersBuff.add(power);
            }
        }

        if(buffSize == 0){
            tickDuration();
            return;
        }
        if (buffSize > 1){
            random = AbstractDungeon.cardRandomRng.random(0, buffSize - 1);
            FinalBuff = powersBuff.get(random);
        } else {
            FinalBuff = powersBuff.get(0);
        }

        if (FinalBuff.ID.equals("ReactivePower")){
            //处理扭曲团块的buff
            addToBot(new RemoveSpecificPowerAction(m, m, FinalBuff.ID));
            tickDuration();
            return;
        }

        FinalBuff.owner = p;

        switch (FinalBuff.ID){
            case "Flight":
                addToBot(new ApplyPowerAction(p, p, new MyFlightPower(p, FinalBuff.amount)));
                FinalBuff.owner = m;
                break;
            case  "Curl Up":
                addToBot(new ApplyPowerAction(p, p, new MyCurlUpPower(p, FinalBuff.amount)));
                break;
            default:
                addToBot(new ApplyPowerAction(p, p, FinalBuff));
                break;
        }
        addToBot(new RemoveSpecificPowerAction(m, m, FinalBuff.ID));
        tickDuration();
    }
}
