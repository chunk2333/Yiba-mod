package power;

import YibaMod.YibaMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class YanWangDianMaoPower extends AbstractPower {
    public static final String POWER_ID = YibaMod.makeModID("YanWangDianMaoPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static TextureAtlas atlas_self;

    public YanWangDianMaoPower(AbstractCreature owner) {
        super();
        atlas_self = new TextureAtlas(Gdx.files.internal("powers/Selfpowers.atlas"));
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.region48 = atlas_self.findRegion("48/CoffeeBeanPower");
        this.region128 = atlas_self.findRegion("128/CoffeeBeanPower");
        updateDescription();
        this.type = PowerType.BUFF;
    }

    @Override
    public void atStartOfTurn(){
        flash();
        int damage = AbstractDungeon.cardRandomRng.random(1, 999);
        if (AbstractDungeon.cardRandomRng.randomBoolean()){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    int num = damage;
                    if (num > 20){
                        num = 20;
                    }
                    for (int i = 0; i < num; i++){
                        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
                    }
                    tickDuration();
                }
            });
            addToBot(new LoseHPAction(this.owner, this.owner, damage));
        } else {
            addToBot(new SFXAction("THUNDERCLAP", 0.05F));
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.isDeadOrEscaped())
                    addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05F));
            }
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
