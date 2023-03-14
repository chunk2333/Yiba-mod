package cards.colorless;
//小丑
import actions.PlayRandomCardAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Joker extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Joker");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/Joker.png";

    private static final int COST = 1;

    public static final String ID = "Joker";

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public Joker() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        //保留
        this.selfRetain = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        addToBot(new AbstractGameAction() {
            public void update() {
                addToBot(new PlayRandomCardAction(

                        (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                this.isDone = true;
            }
        });

        if(this.upgraded){
            addToBot(new AbstractGameAction() {
                public void update() {
                    addToBot(new PlayRandomCardAction(

                            (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                    this.isDone = true;
                }
            });
        }

    }
    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Joker();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和伤害
            upgradeName();
            upgradeBaseCost(0);
            upgradeMagicNumber(1);
        }
    }
}