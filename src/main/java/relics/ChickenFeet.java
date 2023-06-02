package relics;
//永恒鸡爪
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ChickenFeet extends CustomRelic {

    public static final String ID = "ChickenFeet";

    private static final String IMG = "img/relics/ChickenFeet.png";

    private static final String IMG_OTL = "img/relics/outline/ChickenFeet.png";

    public ChickenFeet() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m.type == AbstractMonster.EnemyType.ELITE) {
                flash();
                addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 15, 0.0F));
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                return;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ChickenFeet();
    }

}
