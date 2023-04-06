package cards.colorless;
//俄罗斯转盘
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class RussianTurntable extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("RussianTurntable");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/RussianTurntable.png";

    private static final int COST = 0;

    public static final String ID = "RussianTurntable";

    public RussianTurntable() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int random = AbstractDungeon.cardRandomRng.random(1, 6);
        if(random == 6){
            YibaMod.logger.info("俄罗斯转盘触发：被击中，立即死亡");
            addToBot(new VFXAction(new LightningEffect(p.hb.cX, p.hb.cY)));
            addToBot(new LoseHPAction(p, p, 99999));
        }
        //名利双收动画
        AbstractDungeon.effectList.add(new RainingGoldEffect(300, true));
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        p.gainGold(300);

        if(!this.upgraded){
            //从主牌库移除
            AbstractDungeon.player.masterDeck.removeCard(this.cardID);
        }

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new RussianTurntable();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            //重载描述
            initializeDescription();
        }
    }
}
