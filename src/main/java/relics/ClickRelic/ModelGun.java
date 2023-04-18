package relics.ClickRelic;
//模型枪
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import relics.abstracrt.ClickableRelic;

public class ModelGun extends ClickableRelic {

    public static final String ID = "ModelGun";

    private static final String IMG = "img/relics/ModelGun.png";

    private static final String IMG_OTL = "img/relics/outline/ModelGun.png";

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("NoneHasBullet");

    public ModelGun() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }
    @Override
    public void onRightClick(){
        if (AbstractDungeon.getMonsters() != null && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)){
            if(this.counter > 0){
                this.counter -= 1;
                show();
                addToBot(new DamageAllEnemiesAction(null,

                        DamageInfo.createDamageMatrix(10, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }else {
                addToBot(new TalkAction(true, uiStrings.TEXT[0], 1.0F, 2.0F));
            }
        }


    }

    @Override
    public void onEquip() {
        this.counter = 1;
    }

    @Override
    public void onVictory() {
        this.counter += 1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ModelGun();
    }
}
