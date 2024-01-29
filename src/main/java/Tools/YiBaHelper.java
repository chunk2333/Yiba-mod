package Tools;

import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class YiBaHelper {
    public static String MakeSoundPath(String id){
        return "YiBa_Sound:" + id;
    }

    public static Boolean hasMod(String modId){
        return Loader.isModLoadedOrSideloaded(modId);
    }

    public static boolean TempBoolen;

    public static boolean YuanShenStartBool;

    public static int YuanShenStartInt = 5;

    public static int YuanShenStartRandomNum = YiBaHelper.getRandomNum();

    static {
        regenerateYuanShenStartBool();
    }

    public static boolean LoadOutModIsClose = true;

    public static AbstractCard LastPlayedCard = null;

    public static AbstractMonster LastAttackMonster = null;

    private static String lastTriggerElementName;

    private static String lastTriggerElementFinalName;

    private static String BlindBoxRelicId;

    private static String TempRelicId;

    private static String TempRelicId2;

    public static int getPlayerMystery(){
        int mystery = 0;
        AbstractPlayer p = AbstractDungeon.player;
        for(AbstractPower power:p.powers){
            if(power.ID.equals("MysteryPower")){
                mystery = power.amount;
            }
        }
        return mystery;
    }

    public static AbstractCard getRandomCurseCard(){
        //取随机诅咒牌
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.curseCardPool.group) {
            if(c.type== AbstractCard.CardType.CURSE){
                list.add(c);
            }
        }
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }


    public static int getTagsCardNum(AbstractCard.CardTags tags){
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.hasTag(tags)){
                list.add(c);
            }
        }
        return list.size();
    }

    public static boolean canTriggerElement(AbstractMonster m, String ElementName){
        if(ElementName.equals("HydroPower")){
            if(m.hasPower("PyroPower")){
                return true;
            }
            return m.hasPower("GeoPower");
        }
        if(ElementName.equals("PyroPower")){
            if(m.hasPower("HydroPower")){
                return true;
            }
            return m.hasPower("GeoPower");
        }
        if(ElementName.equals("GeoPower")){
            if(m.hasPower("HydroPower")){
                return true;
            }
            return m.hasPower("PyroPower");
        }
        return false;
    }

    public static void setLastTriggerElement(String name, String finalName){
        lastTriggerElementName = name;
        lastTriggerElementFinalName = finalName;
    }

    public static String getLastTriggerElementName(){
        return lastTriggerElementName;
    }
    public static String getLastTriggerElementFinalName(){
        return lastTriggerElementFinalName;
    }

    public static Boolean hasElement(AbstractMonster m){
        for(AbstractPower power : m.powers){
            if(power.ID.equals("HydroPower")){
                return true;
            }
            if(power.ID.equals("PyroPower")){
                return true;
            }
            if(power.ID.equals("GeoPower")){
                return true;
            }
        }
        return false;
    }

    public static Boolean hasHydroElement(AbstractMonster m){
        if(m == null){
            return false;
        }

        for(AbstractPower power : m.powers){
            if(power.ID.equals("HydroPower")){
                return true;
            }
        }
        return false;
    }

    public static Boolean hasPyroElement(AbstractMonster m){
        if(m == null){
            return false;
        }

        for(AbstractPower power : m.powers){
            if(power.ID.equals("PyroPower")){
                return true;
            }
        }
        return false;
    }

    public static Boolean hasGeoElement(AbstractMonster m){
        if(m == null){
            return false;
        }

        for(AbstractPower power : m.powers){
            if(power.ID.equals("GeoPower")){
                return true;
            }
        }
        return false;
    }

    public static void setBlindBoxRelic(String relicId){

        BlindBoxRelicId = relicId;
        YibaMod.logger.info(BlindBoxRelicId);
    }

    public static String getBlindBoxRelic(){
        return BlindBoxRelicId;
    }

    public static void setTempRelic(String relicId){

        TempRelicId = relicId;
        YibaMod.logger.info(TempRelicId);
    }

    public static String getTempRelic(){
        return TempRelicId;
    }

    public static void setTempRelic2(String relicId){

        TempRelicId2 = relicId;
        YibaMod.logger.info(TempRelicId2);
    }

    public static String getTempRelic2(){
        return TempRelicId2;
    }

    public static void regenerateYuanShenStartBool() {
        Random random = new Random();
        YuanShenStartBool = random.nextBoolean();
    }

    public static int getRandomNum(int min, int max){
        if (min > max){
            return -1;
        }
        Random random = new Random();
        int range = max - min + 1;

        return random.nextInt(range) + 1;
    }

    public static int getRandomNum(){
        Random random = new Random();
        return random.nextInt(101);
    }

    public static void addRelic(List<List<AbstractRelic>> relics, List<AbstractRelic> selectedRelics, int index) {
        AbstractDungeon.isScreenUp = true;
        if (index == relics.size()) {
            selectedRelics.forEach(AbstractRelic::instantObtain);
            YibaMod.relicSelectScreen.isDone = true;
            AbstractDungeon.isScreenUp = false;
            return;
        }
        YibaMod.relicSelectScreen.open(relics.get(index), relic -> {
            selectedRelics.add(relic);
            YibaMod.relicSelectScreen.isDone = true;
            AbstractDungeon.isScreenUp = false;
        },() -> addRelic(relics, selectedRelics, index + 1));
    }
}