package cards;
//特效测试卡
//public class VFXTestCard {

import VFX.SteamBarrierEffect;
import basemod.abstracts.CustomCard;
import basemod.helpers.VfxBuilder;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import power.*;

public class VFXTestCard extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("VFXTestCard");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/Rebound_My.png";
    private static final int COST = 0;
    public static final String ID = "VFXTestCard";

    public VFXTestCard() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.COMMON, CardTarget.ENEMY);
        //设置 magicNumber
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //AbstractDungeon.effectsQueue.add(new SteamBarrierEffect(p.drawX, p.drawY));
        UpdateStats.logger.info("p.drawX:"+p.drawX);
        UpdateStats.logger.info("m.drawY:"+m.drawY);
        UpdateStats.logger.info("m.hb.y:"+m.hb.y);
        UpdateStats.logger.info("m.hb.cY:"+m.hb.cY);
        Texture ballTexture;

        ballTexture = new Texture(Gdx.files.internal("img/VFX/c2.png"));

        AbstractGameEffect shootStar = new VfxBuilder(ImageMaster.TINY_STAR, p.drawX, m.hb.cY, 0.8f)
                .scale(0.8f, 2.2f, VfxBuilder.Interpolations.SWING)
                .setColor(Color.GOLD)
                .moveX(p.drawX, m.drawX, VfxBuilder.Interpolations.EXP5IN)
                .rotate(-400f)
                .build();
        //AbstractDungeon.effectsQueue.add(ballEffect);

        AbstractGameEffect rockEffect = new VfxBuilder(ballTexture, Settings.WIDTH/2, (float) (Settings.HEIGHT/1.5), 2f)
                //.moveY(Settings.HEIGHT, m.hb.y + m.hb.height / 6, VfxBuilder.Interpolations.BOUNCE)
                //.moveX()
                .setColor(Color.GOLD)
                .fadeIn(0.25f)
                .fadeOut(0.25f)
                .setScale(1.5f)
                .rotate(-200f)
                .playSoundAt( 0.35f,"BELL")
                .build();
        //AbstractDungeon.effectsQueue.add(shootStar);
        AbstractDungeon.effectsQueue.add(rockEffect);
        addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 2.0F), true)));
        addToTop(new MakeTempCardInHandAction(makeStatEquivalentCopy()));

    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new VFXTestCard();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和费用
            upgradeName();
        }
    }
}