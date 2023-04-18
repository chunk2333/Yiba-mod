package relics.ClickRelic;
//通往天堂的钥匙
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import relics.abstracrt.ClickableRelic;

public class TheKeyToHeaven extends ClickableRelic {

    public static final String ID = "TheKeyToHeaven";

    private static final String IMG = "img/relics/TheKeyToHeaven.png";

    private static final String IMG_OTL = "img/relics/outline/TheKeyToHeaven.png";

    public TheKeyToHeaven() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.FLAT);
    }
    @Override
    public void onRightClick(){
        if (AbstractDungeon.getMonsters() != null && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)){
            flash();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new VFXAction(new LightningEffect(p.hb.cX, p.hb.cY)));
            addToBot(new LoseHPAction(p, p, 99999));
            killAllMonsters(false);
        }

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
        return new TheKeyToHeaven();
    }
}
