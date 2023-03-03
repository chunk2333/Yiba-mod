package relics;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;


public class kakaa extends CustomRelic {
    public static final String ID = "Kakaa";
    private static final String IMG = "img/relics_Seles/kakaa.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/kakaa.png";
    private boolean isActive = false;
    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public kakaa() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发

    }
    @Override
    public void onBloodied() {
        AbstractPlayer p = AbstractDungeon.player;
        if(p.currentHealth==1){
            flash();
            addToTop(new RelicAboveCreatureAction(p, this));
            //说话
            addToBot(new TalkAction(true, "接受 #r@我的怒火吧~~~~~@ ", 1.5F, 2.0F));
            //秒杀场上所有怪物
            killAllMonsters(false);
            //回满血
            p.heal(p.maxHealth);
        }
    }
    public void killAllMonsters(boolean isKillAllMode) {
        if (AbstractDungeon.getMonsters() == null)
            return;
        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
            if (monster != null) {
                AbstractDungeon.actionManager.addToTop(new InstantKillAction(monster));
                if (!isKillAllMode) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new WeightyImpactEffect(monster.hb.cX, monster.hb.cY)));
                }
            }
        }
    }
    @Override
    public void onVictory() {
        //在胜利时触发
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    @Override
    //拾取时触发
    public void onEquip() {
    }
    @Override
    public AbstractRelic makeCopy() {
        return new kakaa();
    }
}
