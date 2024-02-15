package potions;
//绯色物质药水
import VFX.KingCrimsonEffect;
import YibaMod.YibaMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import power.KingCrimsonPower;

public class KingCrimsonPotion extends AbstractPotion {
    public static final String POTION_ID = YibaMod.makeModID("KingCrimsonPotion");

    private static final PotionStrings potionString = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    private static final String NAME = PotionStrings.getMockPotionString().NAME;

    public KingCrimsonPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.HEART, PotionColor.WEAK);
        this.isThrown = false;
    }

    public void use(AbstractCreature target) {
        CardCrawlGame.sound.playA("KingCrimson", 0F);
        addToBot(new VFXAction(new KingCrimsonEffect(new Color(255.0F, 77.0F, 255.0F, 0.5F), false)));
        addToBot(new SkipEnemiesTurnAction());
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                    if (!m.isDying && !m.isDead){
                        addToBot(new ApplyPowerAction(m, m, new KingCrimsonPower(m, 1)));
                    }
                }
                tickDuration();
            }
        });
    }

    public void initializeData(){
        this.potency = getPotency();
        this.description = potionString.DESCRIPTIONS[0];
        this.name = potionString.NAME;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    public AbstractPotion makeCopy() {
        return new KingCrimsonPotion();
    }
}
