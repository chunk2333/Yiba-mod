package Tools;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;

public abstract class GetRadomPower {
    private Random rng = AbstractDungeon.relicRng;
    public AbstractPower GetRadomPower(AbstractCreature owner, int newAmount){
        int radom = 0;
        radom = rng.random(1,99);
        if(radom==1){
            //精准
            return new AccuracyPower(owner,newAmount);
        }
        if(radom==2){
            //余象
            return new AfterImagePower(owner,newAmount);
        }
        if(radom==3){
            //增幅
            return new AmplifyPower(owner,newAmount);
        }
        if(radom==4){
            //激怒
            return new AngerPower(owner,newAmount);
        }
        if(radom==5){
            //生气
            return new AngryPower(owner,newAmount);
        }
        if(radom==6){
            //人工制品
            return new ArtifactPower(owner,newAmount);
        }
        if(radom==7){
            //人攻击烧毁
            return new AttackBurnPower(owner,newAmount);
        }
        if(radom==8){
            //壁垒
            return new BarricadePower(owner);
        }
        if(radom==9){
            //死亡律动
            return new BeatOfDeathPower(owner,newAmount);
        }



        return new StrengthPower(owner,newAmount);
    }


}
