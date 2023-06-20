package relics;
//急
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Ji extends CustomRelic implements CustomSavable<String> {

    public static final String ID = "Ji";

    private static final String IMG = "img/relics/Ji.png";

    private static final String IMG_OTL = "img/relics/outline/Ji.png";

    public Ji() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (Settings.FAST_MODE){
            Settings.FAST_MODE = false;
        }
    }

    @Override
    public void onEquip() {
        Settings.FAST_MODE = false;
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public String onSave(){
        return "1";
    }

    @Override
    public void onLoad(String relicId)
    {
        if (Settings.FAST_MODE){
            Settings.FAST_MODE = false;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Ji();
    }
}
