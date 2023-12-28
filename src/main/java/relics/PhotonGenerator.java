package relics;
//光子发生装置
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PhotonGenerator extends CustomRelic {

    public static final String ID = "PhotonGenerator";

    private static final String IMG = "img/relics/PhotonGenerator.png";

    private static final String IMG_OTL = "img/relics/outline/PhotonGenerator.png";

    private Boolean isFirst = false;

    public PhotonGenerator() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        this.isFirst = true;
    }

    @Override
    public void atTurnStart() {
        if (this.counter <= 7 && !this.isFirst){
            AbstractPlayer p = AbstractDungeon.player;
            flash();
            addToBot(new RelicAboveCreatureAction(p, this));
            //获得缓冲
            addToBot(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
        }else {
            this.isFirst = false;
        }

        //清零计数
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        //flash();
        this.counter += 1;
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PhotonGenerator();
    }
}
