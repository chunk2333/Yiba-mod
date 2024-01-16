package power;
//临时枯木树枝
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CorruptionAndDeadBranchPower extends AbstractPower {
    public static final String POWER_ID = "CorruptionAndDeadBranchPower";

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("CorruptionAndDeadBranchPower");

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public CorruptionAndDeadBranchPower(AbstractPlayer owner){
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.region48 = atlas_self.findRegion("48/DeadBranch");
        this.region128 = atlas_self.findRegion("128/DeadBranch");
        this.type = PowerType.BUFF;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        flash();
        addToBot(new MakeTempCardInHandAction(AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy(), false));
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

}
