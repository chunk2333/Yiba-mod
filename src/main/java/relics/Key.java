package relics;
//破烂锈蚀的钥匙
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Key extends CustomRelic {

    public static final String ID = "Key";

    private static final String IMG = "img/relics/Key.png";

    private static final String IMG_OTL = "img/relics/outline/Key.png";

    public Key() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        //拾取时触发
        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(
                AbstractDungeon.returnRandomRelicTier());
        (AbstractDungeon.getCurrRoom()).rewards.clear();
        AbstractDungeon.getCurrRoom().addRelicToRewards(r);
        if(!Settings.hasSapphireKey){
            //
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(
                    AbstractDungeon.getCurrRoom().rewards.get(
                            AbstractDungeon.getCurrRoom().rewards.size() - 1), RewardItem.RewardType.SAPPHIRE_KEY));
        }
        if(!Settings.hasEmeraldKey){
            //
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(
                    AbstractDungeon.getCurrRoom().rewards.get(
                            AbstractDungeon.getCurrRoom().rewards.size() - 1), RewardItem.RewardType.EMERALD_KEY));
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
    }

    @Override
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Key();
    }

}
