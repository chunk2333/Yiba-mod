package relics;
//精灵祝福
import basemod.abstracts.CustomRelic;
import YibaMod.YibaMod;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FairyBlessing extends CustomRelic {

    public static final String ID = "FairyBlessing";

    private static final String IMG = "img/relics/FairyBlessing.png";

    private static final String IMG_OTL = "img/relics/outline/FairyBlessing.png";

    private boolean isActive;

    public FairyBlessing() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        this.isActive = false;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        //被攻击时触发
        AbstractPlayer p = AbstractDungeon.player;
        if(damageAmount>p.currentHealth && !isActive ){
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            YibaMod.logger.info("精灵祝福触发，受到的伤害：" + damageAmount);
            this.isActive = true;
            this.grayscale = true;
            return 0;
        }
        return damageAmount;
    }
    @Override
    public void onVictory() {
        //在胜利时触发
        this.isActive = false;
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FairyBlessing();
    }

}
