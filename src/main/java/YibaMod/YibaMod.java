package YibaMod;

import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.*;
import cards.colorless.*;
import cards.curse.*;
import cards.element.*;
import cards.green.*;
import cards.purple.*;
import cards.red.FleshShock;
import cards.red.LeiPu;
import cards.red.LetMeSee;
import cards.red.goodtime;
import characters.seles;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import pathes.*;
import relics.*;
import relics.ClickRelic.*;
import potions.*;
import events.*;
import monsters.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import basemod.helpers.RelicType;


@SpireInitializer
public class YibaMod implements RelicGetSubscriber, PostPowerApplySubscriber, PostExhaustSubscriber, PostBattleSubscriber, PostDungeonInitializeSubscriber, EditCharactersSubscriber, PostInitializeSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, OnCardUseSubscriber, EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostDrawSubscriber, PostEnergyRechargeSubscriber {
    private static final String MOD_BADGE = "img/ui/badge.png";
    //攻击、技能、能力牌的背景图片(512)
    private static final String ATTACK_CC = "img/512/bg_attack_SELES_s.png";
    private static final String SKILL_CC = "img/512/bg_skill_SELES_s.png";
    private static final String POWER_CC = "img/512/bg_power_SELES_s.png";
    private static final String ENERGY_ORB_CC = "img/512/SELESOrb.png";
    //攻击、技能、能力牌的背景图片(1024)
    private static final String ATTACK_CC_PORTRAIT = "img/1024/bg_attack_SELES.png";
    private static final String SKILL_CC_PORTRAIT = "img/1024/bg_skill_SELES.png";
    private static final String POWER_CC_PORTRAIT = "img/1024/bg_power_SELES.png";
    private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/SELESOrb.png";
    public static final String CARD_ENERGY_ORB = "img/ui/energyOrb.png";
    //选英雄界面的角色图标、选英雄时的背景图片
    private static final String MY_CHARACTER_BUTTON = "img/charSelect/SelesButton.png";
    private static final String MARISA_PORTRAIT = "img/charSelect/SelesPortrait.png";
    public static final Color SILVER = CardHelper.getColor(200, 200, 200);
    private final ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
    public static ArrayList<AbstractCard> recyclecards = new ArrayList<>();
    public YibaMod() {
        //构造方法，初始化各种参数
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.Seles_COLOR, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT, POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
    }

    @Override
    public void receiveEditCharacters() {
        //添加角色到MOD中
        BaseMod.addCharacter(new seles("Seles"), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, ThmodClassEnum.Seles_CLASS);
    }

    //初始化整个MOD,一定不能删
    public static void initialize() {
        new YibaMod();
    }

    @Override
    public void receiveEditCards() {
        //将卡牌批量添加
        loadCardsToAdd();
        for (AbstractCard card : this.cardsToAdd) {
            BaseMod.addCard(card);
        }
    }

    @Override
    public void receivePostExhaust(AbstractCard c) {
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower pow, AbstractCreature target, AbstractCreature owner) {

    }


    @Override
    public void receivePowersModified() {
    }


    @Override
    public void receivePostDungeonInitialize() {
    }


    @Override
    public void receivePostDraw(AbstractCard arg0) {
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }


    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(new String[]{"诡谲状态"},"每回合：暂时将抽牌堆的所有技能牌降低 #b1 费，之后选一张加入手牌。当你打出任意一张牌时失效。");
        BaseMod.addKeyword(new String[]{"不同效果"},"#r攻击 ： #b1 点 力量 。 NL  #g技能 ： #b1 点 敏捷 。 NL  #b能力 ：获得 [E] 。");
        BaseMod.addKeyword(new String[]{"移除"},"使用后从主牌库移除。");
        BaseMod.addKeyword(new String[]{"亢奋"},"本回合你造成的伤害变为 #b1.3 倍。");
        BaseMod.addKeyword(new String[]{"扩散"},"将指定目标的元素扩散到所有敌人身上。");
        BaseMod.addKeyword(new String[]{"水元素"},"目标将被 #b水元素 附着。");
        BaseMod.addKeyword(new String[]{"火元素"},"目标将被 #r火元素 附着。");
        BaseMod.addKeyword(new String[]{"岩元素"},"目标将被 #y岩元素 附着。");
        BaseMod.addKeyword(new String[]{"剧毒"},"累计 #b3 层后造成大量伤害。");
    }

    @Override
    public void receiveEditStrings() {
        //读取遗物，卡牌，能力，药水，事件的JSON文本

        String relic = "", card = "", power = "", potion = "", event = "", ui = "", monster = "";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            card = "localization/ThMod_Seles_cards-zh.json";
            relic = "localization/ThMod_Seles_relics-zh.json";
            power = "localization/ThMod_Seles_powers-zh.json";
            potion = "localization/ThMod_Seles_potions-zh.json";
            event = "localization/ThMod_Seles_events-zh.json";
            ui = "localization/ThMod_Seles_uis-zh.json";
            monster = "localization/ThMod_Seles_monsters-zh.json";
        }  //其他语言配置的JSON


        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String potionStrings = Gdx.files.internal(potion).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
        String monsterStrings = Gdx.files.internal(monster).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class, monsterStrings);

    }

    private void loadCardsToAdd() {
        //将自定义的卡牌添加到这里
        this.cardsToAdd.clear();
        this.cardsToAdd.add(new Strike_Seles());
        this.cardsToAdd.add(new Defend_Seles());
        this.cardsToAdd.add(new SavePower());
        this.cardsToAdd.add(new Printf());
        //this.cardsToAdd.add(new yiba());
        this.cardsToAdd.add(new LeiPu());
        this.cardsToAdd.add(new goodtime());
        this.cardsToAdd.add(new LetMeSee());
        this.cardsToAdd.add(new snowman());
        this.cardsToAdd.add(new desire());//三大欲望
        this.cardsToAdd.add(new Hahaknife());//哈哈，刀！TM全是刀！
        this.cardsToAdd.add(new rebornGun());//人生重来枪
        this.cardsToAdd.add(new happyOldHome());//快乐老家
        this.cardsToAdd.add(new Truce());//一时休战D
        this.cardsToAdd.add(new DutifulSon());//孝子
        //this.cardsToAdd.add(new Baa());//大咩 - 未完成
        this.cardsToAdd.add(new AcrobaticsPlus());//技杂
        this.cardsToAdd.add(new InstantThrowingDagger());//瞬息飞刀
        this.cardsToAdd.add(new IdleDagger());//闲置匕首
        this.cardsToAdd.add(new MakeASurpriseAttack());//虚诱掩杀
        this.cardsToAdd.add(new PunctureStake());//穿刺木桩
        this.cardsToAdd.add(new Bonesnap());//碎骨
        this.cardsToAdd.add(new Mimicry());//拟态
        this.cardsToAdd.add(new ColdVoice());//寒音索绕
        this.cardsToAdd.add(new Treacherous());//诡谲
        this.cardsToAdd.add(new LongevityInHeavenAndEarth());//天地同寿
        this.cardsToAdd.add(new HarmonyOfLightAndDust());//和光同尘
        this.cardsToAdd.add(new SumUpExperience());//总结经验
        this.cardsToAdd.add(new KnockOut());//最后一击
        this.cardsToAdd.add(new PeopleAndGodsAreOutraged());//人神共愤
        this.cardsToAdd.add(new Abstract());//抽象
        this.cardsToAdd.add(new Imprisonment());//坐牢-诅咒
        this.cardsToAdd.add(new Rebound_My());//斗转星移
        //this.cardsToAdd.add(new VFXTestCard());//特效测试卡牌
        this.cardsToAdd.add(new ForesightAndInsight());//远见明察
        this.cardsToAdd.add(new EyesHeart());//心之眼
        this.cardsToAdd.add(new ThunderstormVientiane());//风雷万象
        this.cardsToAdd.add(new TimeReversal());//时光倒流
        this.cardsToAdd.add(new WuhuTakeOff());//芜湖～起飞！
        this.cardsToAdd.add(new SpireScience());//尖塔学
        this.cardsToAdd.add(new Provoke());//挑衅
        this.cardsToAdd.add(new HydroCard());//水牌
        this.cardsToAdd.add(new PyroCard());//火牌
        this.cardsToAdd.add(new GeoCard());//岩牌
        this.cardsToAdd.add(new AnemoCard());//风牌
        this.cardsToAdd.add(new YouAreOne_OneByOne());//风牌
        //this.cardsToAdd.add(new Evil());//恶德
        this.cardsToAdd.add(new ConjuredHealth());//皮糙肉厚
        this.cardsToAdd.add(new HiddenEnergyInTheBack());//后背隐藏能源
        this.cardsToAdd.add(new PurplePoison());//幽鳞紫尘
        this.cardsToAdd.add(new WeirdThings());//怪神乱力
        this.cardsToAdd.add(new A_BeamOfLight());//素景垂光
        this.cardsToAdd.add(new BlackBlade());//暗裔利刃
        this.cardsToAdd.add(new TempSneckoEye());//蛇眼体验卡
        this.cardsToAdd.add(new ThePeaceOfMind());//明镜止水
        this.cardsToAdd.add(new FleshShock());//肉身震撼
        this.cardsToAdd.add(new PiFace());//坐牢-批脸
        this.cardsToAdd.add(new KillGod());//弑神诛仙
        this.cardsToAdd.add(new AllInOne());//知行合一
        this.cardsToAdd.add(new InstantaneousRage());//一瞬狂暴
        this.cardsToAdd.add(new Joker());//小丑
    }

    @Override
    public void receiveEditRelics() {
        //将自定义的遗物添加到这里
        BaseMod.addRelicToCustomPool(new cLanguageProgramBegin(), AbstractCardEnum.Seles_COLOR);
        BaseMod.addRelic(new kakaa(), RelicType.SHARED);    //kakaa镰刀
        BaseMod.addRelic(new beef(), RelicType.SHARED);     //牛排
        BaseMod.addRelic(new homa(), RelicType.SHARED);     //护摩之杖
        BaseMod.addRelic(new hedron20(), RelicType.SHARED); //符文20面体
        BaseMod.addRelic(new widsith(), RelicType.SHARED);  //流浪乐章
        BaseMod.addRelic(new fafa(), RelicType.SHARED);  //悲伤小花
        BaseMod.addRelic(new muli(), RelicType.SHARED);  //鸭蛋
        BaseMod.addRelic(new yadan(), RelicType.SHARED); //牡蛎
        BaseMod.addRelic(new APaTea(), RelicType.SHARED); //阿帕茶
        BaseMod.addRelic(new Scallion(), RelicType.SHARED); //大葱
        BaseMod.addRelic(new HomoHead(), RelicType.SHARED); //homo头
        BaseMod.addRelic(new Countdown(), RelicType.SHARED); //终焉倒计时
        BaseMod.addRelic(new Cat(), RelicType.SHARED); //猫猫
        BaseMod.addRelic(new Seals(), RelicType.SHARED); //海豹
        BaseMod.addRelic(new Lemon(), RelicType.SHARED); //柠檬
        BaseMod.addRelic(new Dove(), RelicType.SHARED); //鸽子
        BaseMod.addRelic(new Repeater(), RelicType.SHARED); //复读机
        BaseMod.addRelic(new PandaMan(), RelicType.SHARED); //熊猫人
        BaseMod.addRelic(new PhantomHand(), RelicType.SHARED); //空手假象
        BaseMod.addRelic(new Key(), RelicType.SHARED); //破烂锈蚀的钥匙
        BaseMod.addRelic(new MoneyBag(), RelicType.SHARED); //带有血渍的钱袋
        BaseMod.addRelic(new Cranium(), RelicType.GREEN); //弑罚罪证----猎人专属
        BaseMod.addRelic(new BloodiedWarhammer(), RelicType.SHARED); //染血战锤
        BaseMod.addRelic(new RocoCoin(), RelicType.SHARED); //洛克贝
        BaseMod.addRelic(new HouseholdRegister(), RelicType.SHARED); //户口本
        BaseMod.addRelic(new CovertTrap(), RelicType.GREEN); //隐蔽陷阱----猎人专属
        BaseMod.addRelic(new LuLu(), RelicType.SHARED); //LuLu
        BaseMod.addRelic(new Dog(), RelicType.SHARED); //狗狗
        BaseMod.addRelic(new FairyBlessing(), RelicType.SHARED); //精灵祝福
        BaseMod.addRelic(new NonexistentSacrificialLance(), RelicType.SHARED); //不曾存在的祭礼枪
        BaseMod.addRelic(new CoffeeBean(), RelicType.SHARED); //咖啡豆
        BaseMod.addRelic(new BottledAir(), RelicType.SHARED); //瓶装空气
        BaseMod.addRelic(new BottledPoop(), RelicType.SHARED); //瓶装答辩
        BaseMod.addRelic(new Time(), RelicType.SHARED); //”时“
        BaseMod.addRelic(new RichTool(), RelicType.SHARED); //致富神器
        BaseMod.addRelic(new BottledCultistMask(), RelicType.SHARED); //瓶装异教徒头套
        BaseMod.addRelic(new IntertwinedFate(), RelicType.SHARED); //纠缠之缘
        BaseMod.addRelic(new AcquaintFate(), RelicType.SHARED); //相遇之缘
        BaseMod.addRelic(new EntropyIncrease(), RelicType.SHARED); //熵增
        BaseMod.addRelic(new ScatterCoins(), RelicType.SHARED); //撒币
        BaseMod.addRelic(new BrewingStand(), RelicType.SHARED); //酿造台
        BaseMod.addRelic(new TrashFish(), RelicType.SHARED); //杂鱼
        //添加事件:会员制餐厅
        BaseMod.addEvent(new AddEventParams.Builder(Restaurant.ID, Restaurant.class).eventType(EventUtils.EventType.NORMAL).dungeonIDs(TheCity.ID, Exordium.ID).create());
        //添加事件:三幻批
        BaseMod.addEvent(new AddEventParams.Builder(SanHuanPi.ID, SanHuanPi.class).dungeonID(TheBeyond.ID).create());
        //添加事件：LuLuEvent
        BaseMod.addEvent(new AddEventParams.Builder(LuLuEvent.ID, LuLuEvent.class).eventType(EventUtils.EventType.ONE_TIME).create());
        //添加事件：精灵祝福
        BaseMod.addEvent(new AddEventParams.Builder(FairyBlessingEvent.ID, FairyBlessingEvent.class).dungeonID(TheBeyond.ID).create());
        //添加药水
        BaseMod.addPotion(time.class, null, null, null, "time");
        BaseMod.addPotion(money.class, null, null, null, "money");
        BaseMod.addPotion(reborn.class, null, null, null, "reborn");
        BaseMod.addPotion(Escape.class, null, null, null, "Escape");
        BaseMod.addPotion(SpacePotions.class, null, null, null, "SpacePotions");
    }

    @Override
    public void receiveRelicGet(AbstractRelic relic) {
        //移除遗物,这里移除了小屋子，太垃圾了。
        //if (AbstractDungeon.player.name.equals("Seles")) {
            //AbstractDungeon.shopRelicPool.remove("TinyHouse");
        //}
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {

    }

    @Override
    public void receivePostBattle(AbstractRoom r) {

    }

    @Override
    public void receivePostInitialize() {
        //添加怪物
        //原批
        BaseMod.addMonster(YuanPi.ID, () -> new YuanPi(50F, -20F));
        BaseMod.addMonsterEncounter(TheCity.ID, new MonsterInfo(YuanPi.ID, 20));
        //粥批
        BaseMod.addMonster(ZhouPi.ID, () -> new ZhouPi(50F, -20F));
        BaseMod.addMonsterEncounter(TheCity.ID, new MonsterInfo(ZhouPi.ID, 20));
        //农批
        BaseMod.addMonster(NongPi.ID, () -> new NongPi(50F, -20F));
        BaseMod.addMonsterEncounter(TheCity.ID, new MonsterInfo(NongPi.ID, 20));
        //三幻批
        BaseMod.addMonster("SanHuanPi",
                () -> new MonsterGroup(new AbstractMonster[]{
                        new YuanPi(200F, -20F),
                        new ZhouPi(-150F, -20),
                        new NongPi(-500F, -20)
                }));
        //迪奥·布兰度
        BaseMod.addMonster(Dio.ID, Dio::new);

        BaseMod.addBoss(TheCity.ID, Dio.ID,
                "map/boss/Dio.png",
                "map/bossOutline/Dio.png");
    }
    @Override
    public void receivePostEnergyRecharge() {

        for (AbstractCard c : recyclecards) {
            AbstractCard card = c.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, false, true, true));
        }
        recyclecards.clear();
    }

}