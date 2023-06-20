package relics;
//蚌
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Beng extends CustomRelic {

    public static final String ID = "Beng";

    private static final String IMG = "img/relics/Beng.png";

    private static final String IMG_OTL = "img/relics/outline/Beng.png";

    private boolean isActive;

    public Beng() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        this.isActive = false;
    }

    @Override
    public void atTurnStart() {
        if (this.isActive){
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new EndTurnDeathPower(p)));
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        //被攻击时触发
        AbstractPlayer p = AbstractDungeon.player;
        if(damageAmount > p.currentHealth && !isActive){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            this.isActive = true;
            this.grayscale = true;
            return 0;
        }
        return damageAmount;
    }
    @Override
    public void onVictory() {
        //在胜利时触发
        this.isActive = false;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Beng();
    }

}
