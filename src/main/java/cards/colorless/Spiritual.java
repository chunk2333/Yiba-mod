package cards.colorless;
//宁神
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import power.MysteryPower;

public class Spiritual extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Spiritual");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Spiritual.png";

    private static final int COST = 1;

    public static final String ID = "Spiritual";

    public Spiritual() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.baseDamage = 5;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        int random;
        random = AbstractDungeon.cardRandomRng.random(1, 3); //随机数

        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        if(random == 3){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1)); //力量
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1)); //敏捷
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, 1), 1)); //集中
            addToBot(new ApplyPowerAction(p, p, new MysteryPower(p, 1),1)); //精通
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, 1), 1)); //多层护甲
            addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p, 1), 1)); //金属化
            addToBot(new GainBlockAction(p, p, 1));

        }

    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Spiritual();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
