package cards.colorless;
//斗转星移
import basemod.abstracts.CustomCard;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import power.*;

public class Rebound_My extends CustomCard{
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Rebound_My");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Rebound_My.png";
    private static final int COST = 3;
    public static final String ID = "Rebound_My";

    public Rebound_My() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        //设置 magicNumber
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
//        Texture Circle;
//
//        Circle = new Texture(Gdx.files.internal("img/VFX/c2.png"));
//
//        AbstractGameEffect CircleEffect = new VfxBuilder(Circle, Settings.WIDTH/2, (float) (Settings.HEIGHT/1.5), 2f)
//                //.moveY(Settings.HEIGHT, m.hb.y + m.hb.height / 6, VfxBuilder.Interpolations.BOUNCE)
//                //.moveX()
//                .setColor(Color.GOLD)
//                .fadeIn(0.25f)
//                .fadeOut(0.25f)
//                .setScale(1.5f)
//                .rotate(-200f)
//                .playSoundAt( 0.35f,"BELL")
//                .build();
//
//        AbstractDungeon.effectsQueue.add(CircleEffect);
//
//        addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 2.0F), true)));

        addToBot(new ApplyPowerAction(p, p, new Rebound_MyPower(p, this.magicNumber), this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Rebound_My();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和费用
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}