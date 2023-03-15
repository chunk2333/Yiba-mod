package relics;
//琥珀
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Amber extends CustomRelic {

    public static final String ID = "Amber";

    private static final String IMG = "img/relics/Amber.png";

    private static final String IMG_OTL = "img/relics/outline/Amber.png";

    private float MODIFIER_AMT = 0.25F;


    public Amber() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        boolean isBoss = false;
        boolean isElite = false;
        for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS){
                isBoss = true;
            }
            if (m.type == AbstractMonster.EnemyType.ELITE){
                isElite = true;
            }
        }

        if (!isBoss && !isElite) {
            flash();
            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (m.currentHealth > (int)(m.maxHealth * (1.0F - this.MODIFIER_AMT))) {
                    m.currentHealth = (int)(m.maxHealth * (1.0F - this.MODIFIER_AMT));
                    m.healthBarUpdatedEvent();
                }
            }
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

    @Override
    public void onEquip() {
        //拾取时触发
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Amber();
    }
}
