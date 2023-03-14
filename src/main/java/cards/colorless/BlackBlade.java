package cards.colorless;
//暗裔利刃

import actions.KillAndGetRadomRelic;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class BlackBlade extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("BlackBlade");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/BlackBlade.png";
    private static final int COST = 2;
    public static final String ID = "BlackBlade";

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public BlackBlade() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 10;
        this.damage = this.baseDamage;
        //消耗
        this.exhaust = true;
        //虚无
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        if (m != null)
            addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));

        addToBot(new KillAndGetRadomRelic(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new BlackBlade();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和费用
            upgradeName();
            upgradeDamage(3);
            this.isEthereal = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}