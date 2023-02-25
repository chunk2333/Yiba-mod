
//Hahaknife
package cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
public class Hahaknife extends CustomCard{
    //从.json文件中提取键名为LeiPu的信息
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Hahaknife");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards_Seles/Hahaknife.png";
    private static final int COST = 2;
    public static final String ID = "Hahaknife";
    //取升级后的词条描述:
    //public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
    public Hahaknife() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        //CardModifierManager.addModifier(this, new ExampleModifier());
        int cardnum_hand;
        int cardnum_add;
        //取剩余手牌（上限手牌-现有手牌）
        //cardnum_hand = AbstractDungeon.player.hand.size();
        cardnum_hand = p.hand.size();
        cardnum_add = 10 - cardnum_hand;
        UpdateStats.logger.info("添加剩余手牌数量的小刀："+String.valueOf(cardnum_add));
        //取小刀卡的对象
        AbstractCard s = (new Shiv()).makeCopy();
        //添加指定手牌数量的小刀卡           //添加剩余手牌数的小刀到手牌
        addToTop(new MakeTempCardInHandAction(s, cardnum_add));
    }

    @Override

    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new Hahaknife();
    }
    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            //更改名字和减1费
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
