//package pathes;
//
//import basemod.patches.com.megacrit.cardcrawl.screens.stats.StatsScreen.UpdateStats;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//
//@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.EnergyPanel", method = "addEnergy")
//public class WhenAddEngry {
//    @SpireInsertPatch(
//            loc=80,
//            localvars={"e"}
//    )
//    public static void Insert(int e){
//        UpdateStats.logger.info("WhenAddEngry触发：addEnergy");
//    }
//}