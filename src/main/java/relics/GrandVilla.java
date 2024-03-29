package relics;
//大别墅
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GrandVilla extends CustomRelic {

    public static final String ID = "GrandVilla";

    private static final String IMG = "img/relics/GrandVilla.png";

    private static final String IMG_OTL = "img/relics/outline/GrandVilla.png";

    public GrandVilla() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        //拾取时触发

        ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade())
                upgradableCards.add(c);
        }
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty())
            if (upgradableCards.size() == 1) {
                upgradableCards.get(0).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards
                        .get(0).makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            } else {
                upgradableCards.get(0).upgrade();
                upgradableCards.get(1).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards

                        .get(0).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradableCards

                        .get(1).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            }
        //初始化卡片奖励
        RewardItem cardReward = new RewardItem();
        //卡片奖励清空。
        cardReward.cards.clear();
        //添加金卡
        AbstractPlayer player = AbstractDungeon.player;

        int numCards = 3;
        for (AbstractRelic r : player.relics)
            numCards = r.changeNumberOfCardsInReward(numCards);
        if (ModHelper.isModEnabled("Binary"))
            numCards--;
        YibaMod.logger.info("掉落卡牌数：" + numCards);
        ArrayList<AbstractCard> temp = new ArrayList<>();
        temp = YiBaHelper.getRandomMultiCards(AbstractDungeon.rareCardPool.group, numCards);
        cardReward.cards.addAll(temp);
        AbstractDungeon.player.increaseMaxHp(10, true);
        AbstractDungeon.getCurrRoom().addGoldToRewards(100);
        AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion(AbstractDungeon.potionRng));
        AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion(AbstractDungeon.potionRng));
        AbstractDungeon.getCurrRoom().addCardReward(cardReward);
        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.0F;

    }

    @Override
    public AbstractRelic makeCopy() {
        return new GrandVilla();
    }

}
