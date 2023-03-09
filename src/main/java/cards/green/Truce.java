package cards.green;
//一时休战
import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class Truce extends CustomCard{
    //从.json文件中提取键名为LeiPu的信息
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Truce");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/Truce.png";
    private static final int COST = 1;
    public static final String ID = "Truce";
    //public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    boolean canUse_self;
    boolean isTurnStart;
    CardType c;
    public Truce() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        //添加消耗效果
        this.exhaust = true;


    }
    @Override
    public void atTurnStart() {
        //回合开始时
        canUse_self = true;
        isTurnStart = true;
        c = CardType.SKILL;
    }
    @Override
    public void triggerWhenDrawn() {
        //抽取到手牌时
        canUse_self = true;
        this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        this.dontTriggerOnUseCard = false;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //腾跃动画
        addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
        //跳过回合
        addToBot(new SkipEnemiesTurnAction());
        //结束回合
        addToBot(new PressEndTurnButtonAction());
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        UpdateStats.logger.info("能否打出："+canUse);
        UpdateStats.logger.info("能否打出-自身："+canUse_self);
        if (!canUse)
            return false;
            if (!canUse_self) {
                canUse = false;
                this.cantUseMessage = (CardCrawlGame.languagePack.getUIString("isUseAttack")).TEXT[0];
        }else {
                canUse =true;
            }
        return canUse;
    }
    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        boolean isAttactCard;
        if(canUse_self){
        //打出其他卡片 时触发
        if(c.type==CardType.ATTACK ){
            isAttactCard=true;
            //出过攻击牌
        }else {
            isAttactCard=false;
            //出过/非攻击牌
        }
        if (isAttactCard && isTurnStart) {
                canUse_self = false;
        }else {
                canUse_self = true;

        }
        if(canUse_self) {
            //出过/非攻击牌
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            this.dontTriggerOnUseCard = false;
        }else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            this.dontTriggerOnUseCard = true;
            //出过攻击牌
        }
    }
    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Truce();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和取消消耗
            upgradeName();
            this.exhaust = false;
            //读取升级后的描述
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            //重载描述
            initializeDescription();

        }
    }
}
