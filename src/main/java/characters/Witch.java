package characters;

import Tools.YiBaHelper;
import basemod.abstracts.CustomPlayer;
import cards.element.FireBall;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import patchs.ThmodClassEnum;
import patchs.AbstractCardEnum;
import java.util.ArrayList;

public class Witch extends CustomPlayer {
    //初始能量
    private static final int ENERGY_PER_TURN = 3;
    //以下图片依次作用为[篝火休息处的角色背影2，篝火休息处的角色背影1，角色死亡后倒下的图片，角色平常站立时的图片]
    private static final String SELES_SHOULDER_2 = "img/char_Seles/shoulder2.png";
    private static final String SELES_SHOULDER_1 = "img/char_Seles/shoulder1.png";
    private static final String SELES_CORPSE = "img/char_Seles/fallen.png";
    private static final String SELES_STAND = "img/char_Seles/Seles.png";
    //各种素材，不是很懂
    private static final String[] ORB_TEXTURES = new String[] { "img/ui/EPanel/layer5.png", "img/ui/EPanel/layer4.png", "img/ui/EPanel/layer3.png", "img/ui/EPanel/layer2.png", "img/ui/EPanel/layer1.png", "img/ui/EPanel/layer0.png", "img/ui/EPanel/layer5d.png", "img/ui/EPanel/layer4d.png", "img/ui/EPanel/layer3d.png", "img/ui/EPanel/layer2d.png", "img/ui/EPanel/layer1d.png" };
    private static final String ORB_VFX = "img/ui/energyBlueVFX.png";
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F };
    //初始生命，最大生命，初始金币,初始的充能球栏位（机器人）,最后一个应该是进阶14时的最大生命值下降
    private static final int STARTING_HP = 71;
    private static final int MAX_HP = 71;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 4;
    private static final int maxOrbs = 0;
    private static final int cardDraw = 5;
    //返回一个颜色
    public static final Color SILVER = CardHelper.getColor(200, 200, 200);

    public Witch(String name) {
        //构造方法，初始化参数
        super(name, ThmodClassEnum.Witch_CLASS, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null);
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 220.0F * Settings.scale;
        initializeClass(SELES_STAND, SELES_SHOULDER_2, SELES_SHOULDER_1, SELES_CORPSE,
                         getLoadout(),
                         0.0F, 5.0F, 240.0F, 300.0F,
                         new EnergyManager(ENERGY_PER_TURN));
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        //添加初始卡组
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Strike_Seles");
        retVal.add("Strike_Seles");
        retVal.add("Strike_Seles");
        retVal.add("Strike_Seles");
        retVal.add("Defend_Seles");
        retVal.add("Defend_Seles");
        retVal.add("Defend_Seles");
        retVal.add("Defend_Seles");
        retVal.add("FireBall");
        retVal.add("WaterWaveTechnique");
        //retVal.add("Printf");
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        //添加初始遗物
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("cLanguageProgramBegin");
        UnlockTracker.markRelicAsSeen("cLanguageProgramBegin");
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        //选英雄界面的文字描述
        String title="";
        String flavor="";

        title = "魔女";
        flavor = "来自符文之地的天才魔女，前来攀登这座高塔，其可以运用元素之力造成伤害。";


        return new CharSelectInfo(title, flavor, STARTING_HP, MAX_HP,maxOrbs , STARTING_GOLD, cardDraw, this, getStartingRelics(), getStartingDeck(), false);
    }


    @Override
    public String getTitle(PlayerClass playerClass) {
        //应该是进游戏后左上角的角色名
        String title;
        title = "魔女";
        return title;
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("img/char_Seles/Victory1.png", YiBaHelper.MakeSoundPath("DaMie"))); //sfx：应该是通关时播放的音乐;更正：应该是特效
        panels.add(new CutscenePanel("img/char_Seles/Victory2.png"));
        panels.add(new CutscenePanel("img/char_Seles/Victory3.png"));
        return panels;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        //选择卡牌颜色
        return AbstractCardEnum.Witch_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return SILVER;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new FireBall();
    }

    @Override
    public Color getCardTrailColor() {
        return SILVER;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //角色选中时触发

        CardCrawlGame.sound.playA(YiBaHelper.MakeSoundPath("DaMie"), 0.0F);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);

    }

    public void updateOrb(int orbCount) {
        this.energyOrb.updateOrb(orbCount);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //选中音效
        return YiBaHelper.MakeSoundPath("DaMie");
    }

    @Override
    public String getLocalizedCharacterName() {
        String char_name;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            char_name = "魔女";
        } else if (Settings.language == Settings.GameLanguage.ZHT) {
            char_name = "魔女";
        } else {
            char_name = "魔女";
        }
        return char_name;
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Witch(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return SpireHeart.DESCRIPTIONS[10];
    }

    @Override
    public Color getSlashAttackColor() {
        return SILVER;
    }

    @Override
    public String getVampireText() {

        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect(){
        return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL };
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }
}
