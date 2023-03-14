package cards.element;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;

public class Defend_Seles extends CustomCard
    {
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_Seles");
        public static final String NAME = cardStrings.NAME;
        public static final String DESCRIPTION = cardStrings.DESCRIPTION;
        private static final int COST = 1;
        private static final int BLOCK_AMT = 5;
        private static final int UPGRADE_PLUS_BLOCK = 3;
        public static final String ID = "Defend_Seles";
        public static final String IMG_PATH = "img/cards/Defend.png";

        //调用父类的构造方法，传参为super(卡牌ID,卡牌名称，能量花费，卡牌描述，卡牌类型，卡牌颜色，卡牌稀有度，卡牌目标)
        public Defend_Seles() {
            super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.Seles_COLOR, CardRarity.BASIC, CardTarget.SELF);
            //添加基础防御标签和将格挡设为5
            this.tags.add(BaseModCardTags.BASIC_DEFEND);
            this.baseBlock = BLOCK_AMT;
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            //使用卡牌时触发的动作
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }

        @Override
        public AbstractCard makeCopy() {
            //复制卡牌时触发
            return new Defend_Seles();
        }

        @Override
        public boolean isDefend() {
            //是否是最基础防御牌，
            return true;
        }

        @Override
        public void upgrade() {
            //卡牌升级后的效果
            if (!this.upgraded) {
                //更改名字和提高3点格挡
                upgradeName();
                upgradeBlock(UPGRADE_PLUS_BLOCK);
            }
        }
}