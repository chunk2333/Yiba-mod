package relics.choose;
//众神的诅咒
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import power.TheCurseOfTheGodsPower;

public class TheCurseOfTheGods extends CustomRelic implements CustomSavable<Boolean> {

    public static final String ID = "TheCurseOfTheGods";

    private static final String IMG = "img/relics/TheCurseOfTheGods.png";

    private static final String IMG_OTL = "img/relics/outline/null.png";

    public static boolean isActive;

    public TheCurseOfTheGods() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        if(this.counter >= 1 || this.counter == -1){
            this.counter = -1;
            this.grayscale = true;
            return;
        }
        this.pulse = true;
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ApplyPowerAction(p, p, new TheCurseOfTheGodsPower(p)));
        this.counter += 1;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount){
        if(this.counter == -1){
            return damageAmount;
        }
        if (isActive || damageAmount <= 0){
            return damageAmount;
        }
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToTop(new RelicAboveCreatureAction(p, this));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                addToBot(new VFXAction(new LightningEffect(p.hb.cX, p.hb.cY)));
                addToBot(new LoseHPAction(p, p, 99999));
                this.isDone = true;
            }
        });
        isActive = true;
        this.grayscale = true;
        this.pulse = false;
        this.counter = -1;
        return damageAmount;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public Boolean onSave(){
        return isActive;
    }

    @Override
    public void onLoad(Boolean b){
        isActive = b;
    }

    @Override
    public void onEquip() {
        isActive = false;
        this.counter = 0;
        YibaMod.relicScreen.open();
        YibaMod.relicScreen.isDone = false;
    }

}
