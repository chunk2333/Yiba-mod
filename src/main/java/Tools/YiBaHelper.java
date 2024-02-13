package Tools;

import YibaMod.YibaMod;
import cards.colorless.*;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import relics.*;
import relics.ClickRelic.*;

import java.util.ArrayList;
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

    public static ArrayList<AbstractCard> YibaColorlessCards = new ArrayList<>();
    public static ArrayList<AbstractRelic> YibaRelics = new ArrayList<>();

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

    public static void InitializationYibaColorlessCards(){
        YibaColorlessCards.clear();
        YibaColorlessCards.add(new Abstract());
        YibaColorlessCards.add(new AllInOne());
        YibaColorlessCards.add(new Backtrack());
        YibaColorlessCards.add(new BlackBlade());
        YibaColorlessCards.add(new BluePill());
        YibaColorlessCards.add(new CallWhiteBlack());
        YibaColorlessCards.add(new ChoseMiracle());
        YibaColorlessCards.add(new ConjuredHealth());
        YibaColorlessCards.add(new CorruptionAndDeadBranch());
        YibaColorlessCards.add(new DoubleChanting());
        YibaColorlessCards.add(new Edit());
        YibaColorlessCards.add(new FlameOfLife());
        YibaColorlessCards.add(new ForcedExit());
        YibaColorlessCards.add(new ForesightAndInsight());
        YibaColorlessCards.add(new FrontalAmbush());
        YibaColorlessCards.add(new Goodtime());
        YibaColorlessCards.add(new GuardianForce());
        YibaColorlessCards.add(new HiddenEnergyInTheBack());
        YibaColorlessCards.add(new ImmortalityThroughBareHands());
        YibaColorlessCards.add(new Joker());
        //YibaColorlessCards.add(new KingCrimson());
        YibaColorlessCards.add(new Luck());
        YibaColorlessCards.add(new PerfectClosing());
        YibaColorlessCards.add(new Provoke());
        YibaColorlessCards.add(new PurplePoison());
        YibaColorlessCards.add(new Rebound_My());
        YibaColorlessCards.add(new RedPill());
        YibaColorlessCards.add(new RussianTurntable());
        YibaColorlessCards.add(new SinfulKarma());
        YibaColorlessCards.add(new SpireScience());
        YibaColorlessCards.add(new Spiritual());
        YibaColorlessCards.add(new StrongPhysique());
        YibaColorlessCards.add(new TanNangQuWu());
        YibaColorlessCards.add(new TempSneckoEye());
        YibaColorlessCards.add(new TimeReversal());
        YibaColorlessCards.add(new VR());
        YibaColorlessCards.add(new WeirdThings());
        YibaColorlessCards.add(new WuhuTakeOff());
        YibaColorlessCards.add(new WuZhongShengYou());
    }
    public static void InitializationYibaRelics(){
        YibaRelics.clear();
        YibaRelics.add(new Kakaa());    //一血传奇
        YibaRelics.add(new Beef());     //牛排
        YibaRelics.add(new Homa());     //护摩之杖
        YibaRelics.add(new Hedron20()); //符文20面体
        YibaRelics.add(new Widsith());  //流浪乐章
        YibaRelics.add(new Fafa());  //悲伤小花
        YibaRelics.add(new Muli());  //鸭蛋
        YibaRelics.add(new Yadan()); //牡蛎
        YibaRelics.add(new APaTea()); //阿帕茶
        YibaRelics.add(new Scallion()); //大葱
        YibaRelics.add(new HomoHead()); //homo头
        YibaRelics.add(new Countdown()); //终焉倒计时
        YibaRelics.add(new Cat()); //猫猫
        YibaRelics.add(new Seals()); //海豹
        YibaRelics.add(new Lemon()); //柠檬
        YibaRelics.add(new Dove()); //鸽子
        YibaRelics.add(new Repeater()); //复读机
        YibaRelics.add(new PandaMan()); //熊猫人
        YibaRelics.add(new PhantomHand()); //空手假象
        YibaRelics.add(new Key()); //破烂锈蚀的钥匙
        YibaRelics.add(new MoneyBag()); //带有血渍的钱袋
        YibaRelics.add(new BloodiedWarhammer()); //染血战锤
        YibaRelics.add(new RocoCoin()); //洛克贝
        YibaRelics.add(new HouseholdRegister()); //户口本
        YibaRelics.add(new LuLu()); //LuLu
        YibaRelics.add(new Dog()); //狗狗
        YibaRelics.add(new FairyBlessing()); //精灵祝福
        YibaRelics.add(new NonexistentSacrificialLance()); //不曾存在的祭礼枪
        YibaRelics.add(new CoffeeBean()); //咖啡豆
        YibaRelics.add(new BottledAir()); //瓶装空气
        YibaRelics.add(new BottledPoop()); //瓶装答辩
        YibaRelics.add(new Time()); //”时“
        YibaRelics.add(new RichTool()); //致富神器
        YibaRelics.add(new BottledCultistMask()); //瓶装异教徒头套
        YibaRelics.add(new IntertwinedFate()); //纠缠之缘
        YibaRelics.add(new AcquaintFate()); //相遇之缘
        YibaRelics.add(new ScatterCoins()); //撒币
        YibaRelics.add(new BrewingStand()); //酿造台
        YibaRelics.add(new TrashFish()); //杂鱼
        YibaRelics.add(new RussianDolls()); //俄罗斯套娃
        YibaRelics.add(new WitchsScorchingHat()); //焦灼的魔女帽
        YibaRelics.add(new Lance()); //极霸矛
        YibaRelics.add(new GrandVilla()); //大别墅
        YibaRelics.add(new Grail()); //圣杯
        YibaRelics.add(new DragonTooth()); //龙牙
        YibaRelics.add(new SacrificialSword()); //祭礼剑
        YibaRelics.add(new SacrificialFragments()); //祭礼残章
        YibaRelics.add(new SacrificialBow()); //祭礼弓
        YibaRelics.add(new Amber()); //琥珀
        YibaRelics.add(new TheKeyToHeaven()); //通往天堂的钥匙
        YibaRelics.add(new Dice()); //骰子
        YibaRelics.add(new BloodBag()); //血袋
        YibaRelics.add(new BlindBox()); //盲盒
        YibaRelics.add(new RuneCircularDisk()); //符文圆盘
        YibaRelics.add(new VampireFestivalStick()); //吸血鬼节仗
        YibaRelics.add(new DingZhen()); //异眼丁真
        YibaRelics.add(new OpenMindedness()); //开放性思维
        YibaRelics.add(new DarkMask()); //Dark面罩
        YibaRelics.add(new Eraser()); //一块橡皮擦
        YibaRelics.add(new ModelGun()); //模型枪
        YibaRelics.add(new Tumbler()); //不倒翁
        YibaRelics.add(new YoyoBall()); //悠悠球
        YibaRelics.add(new BlessingOfTheWelkinMoon()); //空月祝福
        YibaRelics.add(new GatlingPea()); //机枪豌豆
        YibaRelics.add(new RELX5th()); //锐刻五代
        YibaRelics.add(new ChaoticReality()); //混乱现实
        YibaRelics.add(new StoneGhostFace()); //石鬼面
        YibaRelics.add(new ChickenFeet()); //永恒鸡爪
        YibaRelics.add(new PencilBox()); //文具盒
        YibaRelics.add(new CyberDriftBottle()); //赛博漂流瓶
        YibaRelics.add(new SealedBox()); //被封印的...
        YibaRelics.add(new Ji()); //急
        YibaRelics.add(new Ying()); //赢
        YibaRelics.add(new Dian()); //典
        YibaRelics.add(new Beng()); //蚌
        YibaRelics.add(new Xiao()); //孝
        YibaRelics.add(new Le()); //乐
        YibaRelics.add(new TimeInABottle()); //时间之瓶
        YibaRelics.add(new BlackHand()); //黑手
        YibaRelics.add(new Crossbow()); //诸葛连弩
        YibaRelics.add(new Akaishi()); //赤石
        YibaRelics.add(new Antimatter()); //反物质
        YibaRelics.add(new MechanismScroll()); //机关卷轴
    }

    public static AbstractCard getYibaRandomColorlessCard(){
        return YibaColorlessCards.get(AbstractDungeon.cardRandomRng.random(YibaColorlessCards.size() - 1));
    }

    public static AbstractRelic getYibaRandomRelics(){
        return YibaRelics.get(AbstractDungeon.relicRng.random(YibaRelics.size() - 1));
    }

    public static ArrayList<AbstractCard> getMultiYibaRandomColorlessCards(int count){
        return getRandomMultiCards(YibaColorlessCards, count);
    }

    public static ArrayList<AbstractCard> getRandomMultiCards(ArrayList<AbstractCard> cardsArray, int num){
        ArrayList<AbstractCard> temp = new ArrayList<>();
        if (cardsArray.size() < num){
            num = cardsArray.size();
        }
        int random;
        do {
            temp.clear();  // 清空temp数组
            for (int i = 0; i < num; i++) {
                do {
                    random = AbstractDungeon.cardRandomRng.random(1, cardsArray.size());
                } while (temp.contains(cardsArray.get(random - 1)));  // 检查是否已经包含该卡牌
                temp.add(cardsArray.get(random - 1));
            }

        } while (temp.size() != num);

        return temp;
    }

}