package relics;
//一血传奇
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class Kakaa extends CustomRelic {
    public static final String ID = "Kakaa";

    private static final String IMG = "img/relics/kakaa.png";

    private static final String IMG_OTL = "img/relics/outline/kakaa.png";

    public Kakaa() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void onBloodied() {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.currentHealth==1){
            flash();
            addToTop(new RelicAboveCreatureAction(p, this));
            //说话
            addToBot(new TalkAction(true, "接受 #r@我的怒火吧~~~~~@ ", 1.5F, 2.0F));
            //秒杀场上所有怪物
            killAllMonsters(false);
            //回满血
            p.heal(p.maxHealth);
        }
    }

    @Override
    public void onLoseHp(int damageAmount) {
        onBloodied();
    }

    @Override
    public void onNotBloodied(){
        onBloodied();
    }

    public void killAllMonsters(boolean isKillAllMode) {
        if (AbstractDungeon.getMonsters() == null)
            return;
        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
            if (monster != null) {
                AbstractDungeon.actionManager.addToTop(new InstantKillAction(monster));
                if (!isKillAllMode) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY)));
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Kakaa();
    }

}
