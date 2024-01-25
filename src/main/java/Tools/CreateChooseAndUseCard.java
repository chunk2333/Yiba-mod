package Tools;
//实现卡牌：编辑 的N种匿名卡牌
import YibaMod.YibaMod;
import actions.*;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.unique.BladeFuryAction;
import com.megacrit.cardcrawl.actions.unique.DoublePoisonAction;
import com.megacrit.cardcrawl.actions.unique.LimitBreakAction;
import com.megacrit.cardcrawl.actions.unique.TriplePoisonAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import java.util.ArrayList;

public class CreateChooseAndUseCard {
    public static ArrayList<AbstractCard> cards = new ArrayList<>();
    public static final String defaultImg = "img/cards/test.png";

    public CreateChooseAndUseCard(){
        addCards();
    }

    public static void addCards() {
        cards.clear();
        AbstractCard temp = new AbstractCard("抽牌", "抽牌",defaultImg, 0, "抽 !M! 张牌", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                addToBot(new DrawCardAction(this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("神抽", "神抽",defaultImg, 0, "将手牌抽满", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                addToBot(new DrawCardAction(BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size() + 1));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("格挡", "格挡",defaultImg, 0, "获得 !B! 点 格挡 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseBlock = 8;
                this.block = this.baseBlock;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("回复", "回复",defaultImg, 0, "回复 !M! 点 生命值 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 4;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                p.heal(this.magicNumber);
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("力量", "力量",defaultImg, 0, "获得 !M! 点 力量 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("敏捷", "敏捷",defaultImg, 0, "获得 !M! 点 敏捷 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("集中", "集中",defaultImg, 0, "获得 !M! 点 集中 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToTop(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("弃牌", "弃牌",defaultImg, 0, "丢弃 !M! 张牌。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new DiscardAction(p, p, this.magicNumber, false));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("小刀", "小刀",defaultImg, 0, "将 !M! 张小刀加入到你的手牌中。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
                this.cardsToPreview = new Shiv();
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new MakeTempCardInHandAction(new Shiv(), this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("洗牌", "洗牌",defaultImg, 0, "将你的弃牌堆洗牌后放入你的抽牌堆。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                if (AbstractDungeon.player.discardPile.size() > 0) {
                    addToBot(new EmptyDeckShuffleAction());
                    addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
                }
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("攻击：决斗", "攻击：决斗",defaultImg, 0, "造成 !D! 点伤害。", AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseDamage = 8;
                this.damage = this.baseDamage;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("攻击：混沌", "攻击：混沌",defaultImg, 0, "对随机敌人造成 !D! 点伤害。", AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseDamage = 10;
                this.damage = this.baseDamage;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new NewRipAndTearAction(this));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("攻击：一切", "攻击：一切",defaultImg, 0, "对全体敌人造成 !D! 点伤害。", AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ALL_ENEMY) {
            {
                this.baseDamage = 8;
                this.damage = this.baseDamage;
                //this.isMultiDamage = true;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    addToBot(new DamageAllEnemiesAction(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("攻击「多段」：单体", "攻击「多段」：单体",defaultImg, 0, "造成 !D! 点伤害 !M! 次。", AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseDamage = 4;
                this.damage = this.baseDamage;
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (int i = 0; i < this.magicNumber; i++)
                    addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("攻击「多段」：随机", "攻击「多段」：随机",defaultImg, 0, "对随机敌人造成 !D! 点伤害 !M! 次。", AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseDamage = 5;
                this.damage = this.baseDamage;
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (int i = 0; i < this.magicNumber; i++)
                    addToBot(new NewRipAndTearAction(this));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("时间，暂停", "时间，暂停",defaultImg, 0, "在本回合结束后，额外获得一回合。 NL 结束你的回合。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseDamage = 8;
                this.isMultiDamage = true;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
                addToBot(new SkipEnemiesTurnAction());
                addToBot(new PressEndTurnButtonAction());
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("操控「回合结束」：抽牌", "操控「回合结束」：抽牌",defaultImg, 0, "下回合抽 !M! 张牌。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber), this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("操控「回合结束」：充能", "操控「回合结束」：充能",defaultImg, 0, "下回合 NL 获得 [E] [E] 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, this.magicNumber), this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("充能", "充能",defaultImg, 0, "获得 [E] [E] 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                p.gainEnergy(this.magicNumber);
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("抽牌：预见未来", "抽牌：预见未来",defaultImg, 0, "从抽牌堆选择一张牌放入到你的手牌中。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new BetterDrawPileToHandAction(this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("抽牌：探囊取物", "抽牌：探囊取物",defaultImg, 0, "从弃牌堆选择一张牌放入到你的手牌中。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new BetterDiscardPileToHandAction(this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("抽牌：扭转乾坤", "抽牌：扭转乾坤",defaultImg, 0, "从消耗牌堆选择一张牌放入到你的手牌中。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ScapegoatActions(false));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("设计：能力", "设计：能力",defaultImg, 0, "增加一张随机能力牌到你的手牌。 NL 这张牌在本回合耗能变为0。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {

            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
                c.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(c, true));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("设计：技能", "设计：技能",defaultImg, 0, "增加一张随机技能牌到你的手牌。 NL 这张牌在本回合耗能变为0。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {

            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
                c.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(c, true));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("设计：攻击", "设计：攻击",defaultImg, 0, "增加一张随机攻击牌到你的手牌。 NL 这张牌在本回合耗能变为0。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {

            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
                c.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(c, true));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("创造：金币", "创造：金币",defaultImg, 0, "获得 !M! 点 金币 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 15;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                p.gainGold(this.magicNumber);
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("创造：药水", "创造：药水",defaultImg, 0, "获得一瓶随机药水。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("创造：遗物", "创造：遗物",defaultImg, 0, "获得一个非boss遗物。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new GetRandomRelicAction());
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("创造：虚空", "创造：虚空",defaultImg, 0, "选择移除一张牌。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new DeletePotionAction(1));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("消耗：指定", "消耗：指定",defaultImg, 0, "选择消耗一张牌。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ExhaustAction(1, false));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("消耗：随机", "消耗：随机",defaultImg, 0, "随机消耗一张牌。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ExhaustAction(1, true, false, false));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("姿态：暴怒", "姿态：暴怒",defaultImg, 0, "进入 愤怒 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ChangeStanceAction("Wrath"));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("姿态：平静", "姿态：平静",defaultImg, 0, "进入 平静 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ChangeStanceAction("Calm"));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("姿态：神格", "姿态：神格",defaultImg, 0, "进入 神格 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ChangeStanceAction("Divinity"));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("姿态：皆空", "姿态：皆空",defaultImg, 0, "退出当前姿态。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new NotStanceCheckAction("Neutral", new VFXAction(new EmptyStanceEffect(p.hb.cX, p.hb.cY), 0.1F)));
                addToBot(new ChangeStanceAction("Neutral"));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("生成：闪电", "生成：闪电",defaultImg, 0, "生成 !M! 个 闪电 充能球。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (int i = 0; i < this.magicNumber; i++)
                    addToBot(new ChannelAction(new Lightning()));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("生成：冰霜", "生成：冰霜",defaultImg, 0, "生成 !M! 个 冰霜 充能球。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (int i = 0; i < this.magicNumber; i++)
                    addToBot(new ChannelAction(new Frost()));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("生成：黑暗", "生成：黑暗",defaultImg, 0, "生成 !M! 个 黑暗 充能球。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (int i = 0; i < this.magicNumber; i++)
                    addToBot(new ChannelAction(new Dark()));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("生成：等离子", "生成：等离子",defaultImg, 0, "生成 !M! 个 等离子 充能球。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (int i = 0; i < this.magicNumber; i++)
                    addToBot(new ChannelAction(new Plasma()));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("指令：释放", "指令：释放",defaultImg, 0, "激发 你最右侧的充能球2次。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new AnimateOrbAction(1));
                addToBot(new EvokeWithoutRemovingOrbAction(1));
                addToBot(new AnimateOrbAction(1));
                addToBot(new EvokeOrbAction(1));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("虚弱「指定」", "虚弱「指定」",defaultImg, 0, "给予 !M! 层 虚弱 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("虚弱「群体」", "虚弱「群体」",defaultImg, 0, "给予全体敌人 !M! 层 虚弱 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
                    addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);





        temp = new AbstractCard("易伤「指定」", "易伤「指定」",defaultImg, 0, "给予 !M! 层 易伤 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("易伤「群体」", "易伤「群体」",defaultImg, 0, "给予全体敌人 !M! 层 易伤 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 2;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
                    addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("中毒「指定」", "中毒「指定」",defaultImg, 0, "给予 !M! 层 中毒 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 5;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
            }

            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("中毒「群体」", "中毒「群体」",defaultImg, 0, "给予全体敌人 !M! 层 中毒 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 3;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters){
                    addToBot(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("中毒「催化」", "中毒「催化」",defaultImg, 0, "将一名敌人的 中毒 层数翻倍。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new DoublePoisonAction(m, p));
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("中毒「催化」", "中毒「催化」",defaultImg, 0, "将一名敌人的 中毒 层数变为三倍。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new TriplePoisonAction(m, p));
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("突破「力量」", "突破「力量」",defaultImg, 0, "将你的 力量 翻倍。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new LimitBreakAction());
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("突破「敏捷」", "突破「敏捷」",defaultImg, 0, "将你的 敏捷 翻倍。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                if(p.hasPower("Dexterity")){
                    int strAmt = (p.getPower("Dexterity")).amount;
                    addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, strAmt), strAmt));
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("突破「集中」", "突破「集中」",defaultImg, 0, "将你的 集中 翻倍。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                if(p.hasPower("Focus")){
                    int strAmt = (p.getPower("Focus")).amount;
                    addToTop(new ApplyPowerAction(p, p, new FocusPower(p, strAmt), strAmt));
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("幽魂", "幽魂",defaultImg, 0, "获得 !M! 层 无实体 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("空间栏位", "空间栏位",defaultImg, 0, "获得 !M! 个药水栏位。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractDungeon.player.potionSlots = AbstractDungeon.player.potionSlots + this.magicNumber;
                for(int i = 0; i < this.magicNumber; i++){
                    AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - (this.magicNumber-i)));
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("甜刀子", "甜刀子",defaultImg, 0, "丢弃所有手牌。 NL 每丢弃一张牌，在你的手牌中增加一张 *小刀 。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            {
                this.baseMagicNumber = 1;
                this.magicNumber = this.baseMagicNumber;
                this.cardsToPreview = new Shiv();
            }
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new BladeFuryAction(this.upgraded));
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("归零", "归零",defaultImg, 0, "将手牌中所有卡牌的费用变为0费。 NL (仅限本回合)。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                for(AbstractCard c: p.hand.group){
                    c.setCostForTurn(0);
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("交替：消耗", "交替：消耗",defaultImg, 0, "消耗所有手牌然后从抽牌堆抽取同样数量的卡牌。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new DianAction());
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);

        temp = new AbstractCard("双发", "双发",defaultImg, 0, "你的下一张攻击牌将被额外打出一次。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(p, p, new DoubleTapPower(p, 1), 1));
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("爆发", "爆发",defaultImg, 0, "你的下一张技能牌将被额外打出一次。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(p, p, new BurstPower(p, 1), 1));
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("增幅", "增幅",defaultImg, 0, "你的下一张能力牌将被额外打出一次。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new ApplyPowerAction(p, p, new AmplifyPower(p, 1), 1));
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("创造「万物皆允」", "创造「万物皆允」",defaultImg, 0, "打出一张随机牌。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                addToBot(new AbstractGameAction() {
                    public void update() {
                        addToBot(new PlayRandomCardAction(

                                (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                        this.isDone = true;
                    }
                });
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
        temp = new AbstractCard("清空：远见明察", "清空：远见明察",defaultImg, 0, "将一名敌人的所有buff移除。", AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.NONE) {
            @Override
            public void upgrade() {
                upgradeName();
            }

            @Override
            public void use(AbstractPlayer p, AbstractMonster m) {
                ArrayList<AbstractPower> power = m.powers;
                for (AbstractPower po : power) {
                    addToBot(new RemoveSpecificPowerAction(m, m, po.ID));
                }
            }
            @Override
            public AbstractCard makeCopy() {
                return this;
            }
        };
        cards.add(temp);
    }

}
