package patchs;
//当你的药水栏位同时是瓶中精灵或罐装幽灵时，会给予你一个添水遗物
import Tools.YiBaHelper;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.rewards.RewardItem;
import java.util.Iterator;

@SpirePatch(clz= RewardItem.class, method = "claimReward")
class positionRewardsFix{
    @SpireInsertPatch(loc = 323)
    public static void GetPosition(RewardItem __instance){
        //YibaMod.logger.info("你拾起了药水：" + __instance.potion.name);
        int number = 0;
        int potionsNum = AbstractDungeon.player.potionSlots;
        for (AbstractPotion p : AbstractDungeon.player.potions){
            if(p.ID.equals("FairyPotion") || p.ID.equals("GhostInAJar")){
                number += 1;
            }
        }
        if(!AbstractDungeon.player.hasRelic("Sozu") && number == potionsNum){
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY, new Sozu());
        }
    }

}
@SpirePatch(clz= AbstractPlayer.class, method = "obtainPotion", paramtypez = {AbstractPotion.class})
class obtainPotionFix{
    @SpireInsertPatch(loc = 2709, localvars = {"potionToObtain"})
    public static SpireReturn<Boolean> obtainPotionGet(AbstractPlayer __instance, AbstractPotion potionToObtain) {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SETTINGS || !YiBaHelper.LoadOutModIsClose){
            return SpireReturn.Continue();
        }
        //YibaMod.logger.info("直接获得了药水：" + potionToObtain.name);

        int index = 0;
        for (AbstractPotion p : __instance.potions) {
            if (p instanceof PotionSlot)
                break;
            index++;
        }
        if (index < __instance.potionSlots) {
            __instance.potions.set(index, potionToObtain);
            potionToObtain.setAsObtained(index);
            potionToObtain.flash();
            AbstractPotion.playPotionSound();

            int number = 0;
            Iterator<AbstractPotion> iterator = AbstractDungeon.player.potions.iterator();
            while (iterator.hasNext()) {
                AbstractPotion p = iterator.next();
                if (p.ID.equals("FairyPotion") || p.ID.equals("GhostInAJar")) {
                    number += 1;
                }
            }

            if (!AbstractDungeon.player.hasRelic("Sozu") && number == AbstractDungeon.player.potionSlots) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY, new Sozu());
            }

            return SpireReturn.Return(true);
        }else {
            return SpireReturn.Continue();
        }
    }
}
