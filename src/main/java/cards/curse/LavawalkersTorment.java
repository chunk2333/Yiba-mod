package cards.curse;
//渡火者的煎熬
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.NecronomicurseEffect;

public class LavawalkersTorment extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("LavawalkersTorment");

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CantRemoveFromeMastDeck");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/LavawalkersTorment.png";

    private static final int COST = 2;

    public static final String ID = "LavawalkersTorment";

    public LavawalkersTorment() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.SPECIAL, CardTarget.NONE);
        //消耗
        this.exhaust = true;
    }
    public void onRemoveFromMasterDeck() {
        if (AbstractDungeon.player.hasRelic("WitchsScorchingHat"))
            AbstractDungeon.player.getRelic("WitchsScorchingHat").flash();
        AbstractDungeon.effectsQueue.add(new NecronomicurseEffect(new LavawalkersTorment(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        //添加一张渡火者的煎熬
        //LavawalkersTorment la = new LavawalkersTorment();
        //CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        //group.addToBottom(la.makeCopy());
        //AbstractDungeon.gridSelectScreen.openConfirmationGrid(group,uiStrings.TEXT[0]);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        this.exhaust = true;
        if (this.dontTriggerOnUseCard){
            this.exhaust = false;
            addToTop(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, 4, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new LavawalkersTorment();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
    }
}