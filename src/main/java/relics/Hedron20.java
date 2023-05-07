package relics;
//符文20面体
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

public class Hedron20 extends CustomRelic {

    public static final String ID = "hedron20";

    private static final String IMG = "img/relics/hedron20.png";

    private static final String IMG_OTL = "img/relics/outline/hedron20.png";

    public Hedron20() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atTurnStart(){
        //每回合开始时触发
        AbstractPlayer p = AbstractDungeon.player;
        int maxhp = p.maxHealth;
        double per_maxhp;
        int num;
        //转换到整数
        per_maxhp =  Math.ceil(maxhp* 0.5);
        num = Double.valueOf(per_maxhp).intValue();
        flash();
        if(p.currentHealth >= num){
            addToBot(new GainEnergyAction(1));
        }else{
            //p.heal(2);
        }

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Hedron20();
    }

}
