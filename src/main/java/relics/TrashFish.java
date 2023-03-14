package relics;
//杂鱼
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class TrashFish extends CustomRelic {
    public static final String ID = "TrashFish";

    private static final String IMG = "img/relics/TrashFish.png";

    private static final String IMG_OTL = "img/relics/outline/TrashFish.png";

    public TrashFish() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.HEAVY);
    }
    @Override
    public void atBattleStart() {
        //战斗开始给予三层生气
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new ApplyPowerAction(p, p, new AngryPower(p, 3), 3));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public AbstractRelic makeCopy() {
        return new TrashFish();
    }
}
