package relics;
//牡蛎
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import YibaMod.YibaMod;

public class Muli extends CustomRelic {
    public static final String ID = "muli";

    private static final String IMG = "img/relics/muli.png";

    private static final String IMG_OTL = "img/relics/outline/muli.png";

    boolean isActive;

    public Muli() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        this.isActive = false;
    }

    @Override
    public void atTurnStart(){
        //每回合开始时触发
        if(!isActive){
            this.counter = this.counter + 1;
            if(this.counter == 4){
                flash();
                YibaMod.logger.info("Muli触发：获得1层缓冲");
                //获得缓冲效果
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, 1), 1));
                //遗物特效
                addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this)); //头顶出现遗物特效
                this.counter = -1;
                isActive=true;
            }
        }
    }

    @Override
    public void onVictory() {
        //在胜利时触发
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        //拾取时触发
        this.counter = 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Muli();
    }

}
