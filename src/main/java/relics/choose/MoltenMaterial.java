package relics.choose;
//熔融物质
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import power.UnstableTime;

public class MoltenMaterial extends CustomRelic {
    public static final String ID = YibaMod.makeModID("MoltenMaterial");

    private static final String IMG = "img/relics/MoltenMaterial.png";

    private static final String IMG_OTL = "img/relics/outline/null.png";

    public MoltenMaterial() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!m.isDead && !m.isDying){
                addToBot(new RelicAboveCreatureAction(m, this));
                addToTop(new ApplyPowerAction(m, p, new UnstableTime(p)));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        YibaMod.relicScreen.open();
        YibaMod.relicScreen.isDone = false;
    }

}
