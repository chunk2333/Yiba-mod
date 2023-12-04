package YibaMod;

import Tools.YiBaHelper;
import basemod.BaseMod;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import cards.blue.*;
import cards.colorless.*;
import cards.curse.*;
import cards.element.*;
import cards.green.*;
import cards.purple.*;
import cards.red.*;
import characters.Witch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import events.*;
import monsters.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import patchs.AbstractCardEnum;
import patchs.ThmodClassEnum;
import potions.*;
import relics.*;
import relics.ClickRelic.*;
import relics.Witch.*;
import screens.RelicSelectScreen;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@SpireInitializer
public class YibaMod implements RelicGetSubscriber, PostPowerApplySubscriber, PostRenderSubscriber, PostExhaustSubscriber, PostBattleSubscriber, PostDungeonInitializeSubscriber, EditCharactersSubscriber, PostInitializeSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, OnCardUseSubscriber, EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostDrawSubscriber, PostEnergyRechargeSubscriber, AddAudioSubscriber, PostUpdateSubscriber {
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
    //遗物选择界面
    private static final List<AbstractGameAction> actionList = new ArrayList<>();
    private static final List<AbstractGameAction> offScreenActionList = new ArrayList<>();
    public static RelicSelectScreen relicSelectScreen;

    public static final Logger logger = LogManager.getLogger(YibaMod.class.getName());

    @SpireEnum public static AbstractCard.CardTags ELEMENT;

    @SpireEnum public static AbstractCard.CardTags ANEMO;

    @SpireEnum public static AbstractCard.CardTags GEO;

    @SpireEnum public static AbstractCard.CardTags HYDRO;

    @SpireEnum public static AbstractCard.CardTags PYRO;

    public YibaMod() {
        //构造方法，初始化各种参数
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.Witch_COLOR, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, SILVER, ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC, ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT, POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB);
    }

    @Override
    public void receiveEditCharacters() {
        //添加角色到MOD中
        BaseMod.addCharacter(new Witch("Witch"), MY_CHARACTER_BUTTON, MARISA_PORTRAIT, ThmodClassEnum.Witch_CLASS);
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
        //加载关键词
        String key_WorldFilePatch = "";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            //zhs
            key_WorldFilePatch = "localization/keywords-zh.json";
        }
        Gson gson = new Gson();
        String json = Gdx.files.internal(key_WorldFilePatch).readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null)
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
            }
    }

    @Override
    public void receiveEditStrings() {
        //读取遗物，卡牌，能力，药水，事件的JSON文本
        String relic = "", card = "", power = "", potion = "", event = "", ui = "", monster = "";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            card = "localization/cards-zh.json";
            relic = "localization/relics-zh.json";
            power = "localization/powers-zh.json";
            potion = "localization/potions-zh.json";
            event = "localization/events-zh.json";
            ui = "localization/uis-zh.json";
            monster = "localization/monsters-zh.json";
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
        //---------------魔女卡-----------------------
        this.cardsToAdd.add(new Strike_Seles());//基础打击
        this.cardsToAdd.add(new Defend_Seles());//基础防御
        this.cardsToAdd.add(new FireBall());//火球术
        this.cardsToAdd.add(new WaterWaveTechnique());//水波术
        this.cardsToAdd.add(new MeleeAttack());//近身攻击
        this.cardsToAdd.add(new Explosion());//爆裂魔法
        this.cardsToAdd.add(new Clumsy_My());//弄巧成拙
        this.cardsToAdd.add(new WaterMirror());//水镜
        this.cardsToAdd.add(new NairaDescends());//奈落降临
        this.cardsToAdd.add(new LookingGlass());//镜花水月
        this.cardsToAdd.add(new DeathComes());//死神降临
        this.cardsToAdd.add(new InflammationOfTheWheel());//转轮之炎
        this.cardsToAdd.add(new Relearn());//重修
        this.cardsToAdd.add(new WaterProficiency());//水精通
        this.cardsToAdd.add(new FlamingCharge());//烈焰冲锋
        this.cardsToAdd.add(new FastFire());//快速火焰
        this.cardsToAdd.add(new FastWater());//快速水纹
        this.cardsToAdd.add(new PotentialLiberation());//潜能解放
        this.cardsToAdd.add(new LandJurisdiction());//大地管辖
        this.cardsToAdd.add(new MagicShield());//魔法护盾
        this.cardsToAdd.add(new FlamingStorm());//烈焰风暴
        this.cardsToAdd.add(new FlamingHoard());//烈焰囤积
        this.cardsToAdd.add(new MagicLoop());//魔力循环
        this.cardsToAdd.add(new SpiritualDomination());//精神支配
        //this.cardsToAdd.add(new AffinityMorphology());//亲和形态
        this.cardsToAdd.add(new MagicPress());//魔力压榨
        this.cardsToAdd.add(new TransformingFeatherIntoSpirit());//化羽为灵
        this.cardsToAdd.add(new Chant());//吟唱
        this.cardsToAdd.add(new GaleSpell());//疾风术
        this.cardsToAdd.add(new BladeOfWind());//风刃剑术
        this.cardsToAdd.add(new MagicalProgress());//魔力精进
        this.cardsToAdd.add(new BouncingWaterPolo());//弹跳水球
        this.cardsToAdd.add(new WindFeatherLightFeather());//风翎光羽
        this.cardsToAdd.add(new PenetrationWaterGun());//贯穿水枪
        this.cardsToAdd.add(new MagicFeedback());//魔力反馈
        this.cardsToAdd.add(new AngrySea());//怒海狂涛
        this.cardsToAdd.add(new ExercisePatience());//忍耐
        this.cardsToAdd.add(new ElementPunch());//元素拳
        this.cardsToAdd.add(new DancingFireAndFlowingWind());//舞火流风
        this.cardsToAdd.add(new TheSage());//贤者
        this.cardsToAdd.add(new SimpleMagic());//简易魔法
        this.cardsToAdd.add(new LiquidFire());//液态火
        this.cardsToAdd.add(new PleaseKeepBack());//生人勿近
        this.cardsToAdd.add(new SoftWaterShield());//柔水盾
        this.cardsToAdd.add(new OrangeRoad());//古灵精怪
        this.cardsToAdd.add(new StealthFogColor());//隐身雾色
        this.cardsToAdd.add(new TheRoarOfAHurricane());//飓风呼啸
        this.cardsToAdd.add(new ShakeTheWorld());//掀天揭地
        this.cardsToAdd.add(new ThousandCragMyriadRavine());//千岩万壑
        this.cardsToAdd.add(new Penetrate());//识破
        this.cardsToAdd.add(new FranticAndChaotic());//手忙脚乱
        this.cardsToAdd.add(new InstantKill());//瞬杀
        this.cardsToAdd.add(new PositiveFeedback());//正反馈 -> 亲和形态
        this.cardsToAdd.add(new Annihilate());//湮灭
        this.cardsToAdd.add(new HyperElements());//高浓度元素
        this.cardsToAdd.add(new MagicOverflow());//魔力溢出
        this.cardsToAdd.add(new CrazyHurricaneCut());//狂飓切裂
        this.cardsToAdd.add(new BelieveFirmly());//深信不疑
        this.cardsToAdd.add(new Earthshaking());//石破天惊
        this.cardsToAdd.add(new ElementalRefining());//元素精炼
        this.cardsToAdd.add(new Learn());//现学
        this.cardsToAdd.add(new WandStrike());//魔杖敲击
        this.cardsToAdd.add(new SuddenKick());//突然一jio
        this.cardsToAdd.add(new InstantCutting());//瞬身切割
        this.cardsToAdd.add(new BeAlmostWipedOut());//殆尽
        this.cardsToAdd.add(new OmnipotentAndOmniscient());//全能全知
        this.cardsToAdd.add(new RegardMoneyAsFate());//视金如命
        this.cardsToAdd.add(new ObserverBehavior());//观察者行为
        this.cardsToAdd.add(new FallToHeaven());//堕天
        this.cardsToAdd.add(new MagicRedStone());//红色魔法石
        this.cardsToAdd.add(new MagicGreenStone());//绿色魔法石
        this.cardsToAdd.add(new MagicYellowStone());//黄色魔法石
        this.cardsToAdd.add(new MagicWhiteStone());//白色魔法石
        this.cardsToAdd.add(new MagicStoneMining());//魔法石开采
        this.cardsToAdd.add(new AdvancedTrainingTechniques());//高等练成术
        this.cardsToAdd.add(new SlashAndBurn());//火耕水耨
        this.cardsToAdd.add(new ReconstructEverything());//重构万物
        this.cardsToAdd.add(new Tide());//潮水啊，我已归来
        //this.cardsToAdd.add(new SavePower());
        //this.cardsToAdd.add(new Printf());

        //---------------魔女卡-----------------------

        //this.cardsToAdd.add(new yiba());
        //this.cardsToAdd.add(new LeiPu());//雷普
        this.cardsToAdd.add(new Goodtime());//好时代来临力
        this.cardsToAdd.add(new LetMeSee());//让我康康
        this.cardsToAdd.add(new snowman());//食雪汉
        this.cardsToAdd.add(new desire());//三大欲望
        this.cardsToAdd.add(new Hahaknife());//哈哈，刀！TM全是刀！
        this.cardsToAdd.add(new rebornGun());//人生重来枪
        this.cardsToAdd.add(new happyOldHome());//出千
        this.cardsToAdd.add(new Truce());//一时休战
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
        this.cardsToAdd.add(new LavawalkersTorment());//诅咒-渡火者的煎熬
        this.cardsToAdd.add(new VR());//虚拟现实
        this.cardsToAdd.add(new ChoseMiracle());//天若有情
        this.cardsToAdd.add(new ReturnGoodForEvil());//以德报怨
        this.cardsToAdd.add(new StrongPhysique());//强健体魄
        this.cardsToAdd.add(new Scapegoat());//火中取栗
        this.cardsToAdd.add(new ThunderStorm());//雷雨交加
        this.cardsToAdd.add(new StepDown());//降压
        this.cardsToAdd.add(new Luck());//运气
        this.cardsToAdd.add(new Rally());//重振旗鼓
        this.cardsToAdd.add(new BluePill());//蓝色药丸
        this.cardsToAdd.add(new RedPill());//红色药丸
        this.cardsToAdd.add(new RussianTurntable());//红色药丸
        this.cardsToAdd.add(new Spiritual());//宁神
        this.cardsToAdd.add(new genocidePain());//灭族之痛
        this.cardsToAdd.add(new SealedLeftFoot());//被封印者的左足
        this.cardsToAdd.add(new SealedRightFoot());//被封印者的右足
        this.cardsToAdd.add(new SealedRightArm());//被封印者的右腕
        this.cardsToAdd.add(new SealedLeftArm());//被封印者的左腕
        this.cardsToAdd.add(new SealedEkkusu());//被封印的艾克佐迪亚
        this.cardsToAdd.add(new SuddenHungerSickness());//突发饿疾
        this.cardsToAdd.add(new CallWhiteBlack());//指鹿为马
        this.cardsToAdd.add(new DemonicPact());//恶魔契约
        this.cardsToAdd.add(new Virtual());//虚拟机
        this.cardsToAdd.add(new ArtificialBrainOverflow());//人工脑溢出
        this.cardsToAdd.add(new SnowStorm());//暴风雪
        this.cardsToAdd.add(new SinfulKarma());//涤罪业焰
        this.cardsToAdd.add(new WuZhongShengYou());//无中生有
        this.cardsToAdd.add(new TanNangQuWu());//探囊取物
        this.cardsToAdd.add(new PerfectClosing());//完美谢幕
        this.cardsToAdd.add(new ForbiddenCurse());//禁制诅咒-战士




        //this.cardsToAdd.add(new GifTestCard());//Gif测试卡牌
        //this.cardsToAdd.add(new CatBreath());//破败猫息
    }

    @Override
    public void receiveEditRelics() {
        //---------------魔女遗物-----------------------
        //BaseMod.addRelicToCustomPool(new TestTriggerElement(), AbstractCardEnum.Witch_COLOR);//元素反应测试遗物
        BaseMod.addRelicToCustomPool(new HighLevelMagicBook(), AbstractCardEnum.Witch_COLOR); //很高级的魔导书----魔女专属Boss遗物
        BaseMod.addRelicToCustomPool(new NaturalQuenchedStaff(), AbstractCardEnum.Witch_COLOR); //自然淬炼之杖----魔女专属稀有遗物
        BaseMod.addRelicToCustomPool(new TheLastCoin(), AbstractCardEnum.Witch_COLOR); //最后的一枚硬币
        BaseMod.addRelicToCustomPool(new BlackCat(), AbstractCardEnum.Witch_COLOR); //黑猫
        BaseMod.addRelicToCustomPool(new Fructose(), AbstractCardEnum.Witch_COLOR); //果儿糖
        BaseMod.addRelicToCustomPool(new AlternateDimensionalPocket(), AbstractCardEnum.Witch_COLOR); //异次元口袋
        BaseMod.addRelicToCustomPool(new WaterGun(), AbstractCardEnum.Witch_COLOR); //滋水枪
        BaseMod.addRelicToCustomPool(new StuntDoll(), AbstractCardEnum.Witch_COLOR); //替身娃娃
        //---------------魔女遗物-----------------------

        BaseMod.addRelicToCustomPool(new cLanguageProgramBegin(), AbstractCardEnum.Witch_COLOR);
        BaseMod.addRelic(new Kakaa(), RelicType.SHARED);    //一血传奇
        BaseMod.addRelic(new Beef(), RelicType.SHARED);     //牛排
        BaseMod.addRelic(new Homa(), RelicType.SHARED);     //护摩之杖
        BaseMod.addRelic(new Hedron20(), RelicType.SHARED); //符文20面体
        BaseMod.addRelic(new Widsith(), RelicType.SHARED);  //流浪乐章
        BaseMod.addRelic(new Fafa(), RelicType.SHARED);  //悲伤小花
        BaseMod.addRelic(new Muli(), RelicType.SHARED);  //鸭蛋
        BaseMod.addRelic(new Yadan(), RelicType.SHARED); //牡蛎
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
        BaseMod.addRelic(new EntropyIncrease(), RelicType.RED); //熵增
        BaseMod.addRelic(new ScatterCoins(), RelicType.SHARED); //撒币
        BaseMod.addRelic(new BrewingStand(), RelicType.SHARED); //酿造台
        BaseMod.addRelic(new TrashFish(), RelicType.SHARED); //杂鱼
        BaseMod.addRelic(new RussianDolls(), RelicType.SHARED); //俄罗斯套娃
        BaseMod.addRelic(new WitchsScorchingHat(), RelicType.SHARED); //焦灼的魔女帽
        BaseMod.addRelic(new Lance(), RelicType.SHARED); //极霸矛
        BaseMod.addRelic(new GrandVilla(), RelicType.SHARED); //大别墅
        BaseMod.addRelic(new Grail(), RelicType.SHARED); //圣杯
        BaseMod.addRelic(new DragonTooth(), RelicType.SHARED); //龙牙
        BaseMod.addRelic(new SacrificialSword(), RelicType.SHARED); //祭礼剑
        BaseMod.addRelic(new SacrificialFragments(), RelicType.SHARED); //祭礼残章
        BaseMod.addRelic(new SacrificialBow(), RelicType.SHARED); //祭礼弓
        BaseMod.addRelic(new Amber(), RelicType.SHARED); //琥珀
        BaseMod.addRelic(new DeadLeaves(), RelicType.RED); //腐朽枯叶
        BaseMod.addRelic(new SolarPanels(), RelicType.BLUE); //太阳能电路板
        BaseMod.addRelic(new RedPants(), RelicType.RED); //红裤衩
        BaseMod.addRelic(new TheKeyToHeaven(), RelicType.SHARED); //通往天堂的钥匙
        BaseMod.addRelic(new Dice(), RelicType.SHARED); //骰子
        BaseMod.addRelic(new BloodBag(), RelicType.SHARED); //血袋
        BaseMod.addRelic(new BlindBox(), RelicType.SHARED); //盲盒
        BaseMod.addRelic(new RuneCircularDisk(), RelicType.SHARED); //符文圆盘
        BaseMod.addRelic(new VampireFestivalStick(), RelicType.SHARED); //吸血鬼节仗
        BaseMod.addRelic(new DingZhen(), RelicType.SHARED); //异眼丁真
        BaseMod.addRelic(new OpenMindedness(), RelicType.SHARED); //开放性思维
        BaseMod.addRelic(new DarkMask(), RelicType.SHARED); //Dark面罩
        BaseMod.addRelic(new Eraser(), RelicType.SHARED); //一块橡皮擦
        BaseMod.addRelic(new ModelGun(), RelicType.SHARED); //模型枪
        BaseMod.addRelic(new Tumbler(), RelicType.SHARED); //不倒翁
        BaseMod.addRelic(new YoyoBall(), RelicType.SHARED); //悠悠球
        BaseMod.addRelic(new BlessingOfTheWelkinMoon(), RelicType.SHARED); //空月祝福
        BaseMod.addRelic(new GatlingPea(), RelicType.SHARED); //机枪豌豆
        BaseMod.addRelic(new RELX5th(), RelicType.SHARED); //锐刻五代
        BaseMod.addRelic(new ChaoticReality(), RelicType.SHARED); //混乱现实
        BaseMod.addRelic(new StoneGhostFace(), RelicType.SHARED); //石鬼面
        BaseMod.addRelic(new ChickenFeet(), RelicType.SHARED); //永恒鸡爪
        BaseMod.addRelic(new PencilBox(), RelicType.SHARED); //文具盒
        BaseMod.addRelic(new CyberDriftBottle(), RelicType.SHARED); //赛博漂流瓶
        BaseMod.addRelic(new SealedBox(), RelicType.SHARED); //被封印的...
        BaseMod.addRelic(new Ji(), RelicType.SHARED); //急
        BaseMod.addRelic(new Ying(), RelicType.SHARED); //赢
        BaseMod.addRelic(new Dian(), RelicType.SHARED); //典
        BaseMod.addRelic(new Beng(), RelicType.SHARED); //蚌
        BaseMod.addRelic(new Xiao(), RelicType.SHARED); //孝
        BaseMod.addRelic(new Le(), RelicType.SHARED); //乐
        BaseMod.addRelic(new TimeInABottle(), RelicType.SHARED); //时间之瓶
        BaseMod.addRelic(new PhotonGenerator(), RelicType.BLUE); //光子发生装置
        BaseMod.addRelic(new BlackHand(), RelicType.SHARED); //黑手
        BaseMod.addRelic(new Crossbow(), RelicType.SHARED); //诸葛连弩
        //BaseMod.addRelic(new TestRelicSelect(), RelicType.SHARED); //测试遗物选择
        BaseMod.addRelic(new Charcoal(), RelicType.RED); //木炭-战士专属


        //添加事件:会员制餐厅
        BaseMod.addEvent(new AddEventParams.Builder(Restaurant.ID, Restaurant.class).eventType(EventUtils.EventType.ONE_TIME).dungeonIDs(TheCity.ID, Exordium.ID).create());
        //添加事件:三幻批
        BaseMod.addEvent(new AddEventParams.Builder(SanHuanPi.ID, SanHuanPi.class).dungeonID(TheBeyond.ID).create());
        //添加事件：LuLuEvent
        BaseMod.addEvent(new AddEventParams.Builder(LuLuEvent.ID, LuLuEvent.class).eventType(EventUtils.EventType.ONE_TIME).create());
        //添加事件：精灵祝福
        BaseMod.addEvent(new AddEventParams.Builder(FairyBlessingEvent.ID, FairyBlessingEvent.class).dungeonID(TheBeyond.ID).create());
        //添加事件：猫党？狗党？
        BaseMod.addEvent(new AddEventParams.Builder(CatOrDogEvent.ID, CatOrDogEvent.class).eventType(EventUtils.EventType.ONE_TIME).dungeonIDs(TheCity.ID).create());
        //添加药水
        BaseMod.addPotion(time.class, null, null, null, "time");
        BaseMod.addPotion(money.class, null, null, null, "money");
        BaseMod.addPotion(reborn.class, null, null, null, "reborn");
        BaseMod.addPotion(Escape.class, null, null, null, "Escape");
        BaseMod.addPotion(SpacePotions.class, null, null, null, "SpacePotions");
        BaseMod.addPotion(ElementPotions.class, null, null, null, "ElementPotions", ThmodClassEnum.Witch_CLASS);
        BaseMod.addPotion(ElementCardPotions.class, null, null, null, "ElementCardPotions", ThmodClassEnum.Witch_CLASS);
        BaseMod.addPotion(BottledRelicsPotion.class, null, null, null, "BottledRelicsPotion");
        BaseMod.addPotion(Yiba_ChaoticPotion.class, null, null, null, "Yiba_ChaoticPotion");
        BaseMod.addPotion(DeletePotion.class, null, null, null, "DeletePotion");


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
        //BaseMod.addMonsterEncounter(TheCity.ID, new MonsterInfo(YuanPi.ID, 5));
        //粥批
        BaseMod.addMonster(ZhouPi.ID, () -> new ZhouPi(50F, -20F));
        //BaseMod.addMonsterEncounter(TheCity.ID, new MonsterInfo(ZhouPi.ID, 5));
        //农批
        BaseMod.addMonster(NongPi.ID, () -> new NongPi(50F, -20F));
        //BaseMod.addMonsterEncounter(TheCity.ID, new MonsterInfo(NongPi.ID, 5));
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

        relicSelectScreen = new RelicSelectScreen();
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("HomoVoice"),"sound/HomoVoice.ogg");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("Dio_The_World_Voice01"),"sound/Dio_The_World_Voice01.mp3");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("Dio_The_World_Voice02"),"sound/Dio_The_World_Voice02.mp3");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("DaMie"),"sound/AatroxR.ogg");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("AbaAba"),"sound/AbaAba.mp3");
        //BaseMod.addAudio(YiBaHelper.MakeSoundPath("Explosion"),"sound/Explosion.mp3");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("Dio_high"),"sound/Dio_high.mp3");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("Dio_wryyyy"),"sound/Dio_wryyyy.mp3");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("Dio_BGM"),"sound/Dio_BGM.mp3");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("Fall_Steel"),"sound/Steelpipefalling.mp3");
        BaseMod.addAudio(YiBaHelper.MakeSoundPath("HelloLBW"),"sound/HelloLBW.mp3");
    }

    @Override
    public void receivePostEnergyRecharge() {

        for (AbstractCard c : recyclecards) {
            AbstractCard card = c.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(card, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, false, true, true));
        }
        recyclecards.clear();
    }

    @Override
    public void receivePostUpdate() {
        if (!actionList.isEmpty()) {
            (actionList.get(0)).update();
            if ((actionList.get(0)).isDone)
                actionList.remove(0);
        }
        if (!offScreenActionList.isEmpty() && !AbstractDungeon.isScreenUp) {
            (offScreenActionList.get(0)).update();
            if ((offScreenActionList.get(0)).isDone)
                offScreenActionList.remove(0);
        }
        if (!relicSelectScreen.isDone)
            relicSelectScreen.update();
    }

    @Override
    public void receivePostRender(SpriteBatch sb) {
        if (!relicSelectScreen.isDone)
            relicSelectScreen.render(sb);
    }

}