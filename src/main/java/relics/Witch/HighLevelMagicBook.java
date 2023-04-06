package relics.Witch;
//很高级的魔导书
import actions.HighLevelMagicBookAction;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HighLevelMagicBook extends CustomRelic {

    public static final String ID = "HighLevelMagicBook";

    private static final String IMG = "img/relics/HighLevelMagicBook.png";

    private static final String IMG_OTL = "img/relics/outline/HighLevelMagicBook.png";

    public HighLevelMagicBook() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void atTurnStart() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new HighLevelMagicBookAction());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("cLanguageProgramBegin");
    }

    @Override
    public void onEquip() {
        //删除自身
        AbstractDungeon.player.loseRelic("HighLevelMagicBook");
        //替换初始遗物
        int relicAtIndex = 0;
        for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
            if (AbstractDungeon.player.relics.get(i).relicId.equals("cLanguageProgramBegin")) {
                relicAtIndex = i;
                break;
            }
        }
        AbstractDungeon.player.relics.get(relicAtIndex).onUnequip();
        AbstractRelic bloodyIdol = RelicLibrary.getRelic("HighLevelMagicBook").makeCopy();
        bloodyIdol.instantObtain(AbstractDungeon.player, relicAtIndex, false);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HighLevelMagicBook();
    }
}
