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
        if(radom==10){
            //狂暴
            return new BerserkPower(owner,newAmount);
        }
        if(radom==11){
            //偏差（机器人的每回合下降1集中）
            return new BiasPower(owner,newAmount);
        }
        if(radom==12){
            //残影
            return new BlurPower(owner,newAmount);
        }
        if(radom==13){
            //残暴
            return new BrutalityPower(owner,newAmount);
        }
        if(radom==14){
            //缓冲
            return new BufferPower(owner,newAmount);
        }
        if(radom==15){
            //爆发
            return new BurstPower(owner,newAmount);
        }
        if(radom==16){
            //勒脖
            return new ChokePower(owner,newAmount);
        }
        if(radom==17){
            //收集
            return new CollectPower(owner,newAmount);
        }
        if(radom==18){
            //自然
            return new CombustPower(owner,newAmount,newAmount);
        }



        return new StrengthPower(owner,newAmount);
    }


}
