package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class homa extends CustomRelic {
    public static final String ID = "homa";
    private static final String IMG = "img/relics_Seles/homa.png";
    private static final String IMG_OTL = "img/relics_Seles/outline/homa.png";
    private boolean isActive = false;

    //调用父类的构造方法，传参为super(遗物ID,遗物全图，遗物白底图，遗物稀有度，获得遗物时的音效)
    public homa() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        //在战斗开始时触发
        //赋值玩家指针
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        int maxhp = p.maxHealth;
        double per_maxhp;
        per_maxhp =  Math.ceil(maxhp* 0.1);
        int num;
        //转换到整数
        num = Double.valueOf(per_maxhp).intValue();
        //给予角色力量
        //addToTop(应用力量行动：动作(目标,来源,给予力量对象:(对象（人还是怪）,点数)),次数)
        addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, num), num));
        //遗物在生物上的作用
        //addToTop(遗物在生物上的作用的对象(目标，遗物对象))
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //触发一次掉血效果
        wasHPLost(0);
        }


    @Override
    //掉血时触发
    public void wasHPLost(int damageAmount){
        //判断是否在战斗中掉血
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
        AbstractPlayer p = AbstractDungeon.player;
        int maxhp = p.maxHealth;
        double per_maxhp;
        int num;
        //转换到整数
        per_maxhp =  Math.ceil(maxhp* 0.5);
        num = Double.valueOf(per_maxhp).intValue();
        //addToBot(new TalkAction(true, String.valueOf(p.maxHealth), 1.0F, 2.0F));
        //判断当前血量是否小于50%最大生命上限
        if((p.currentHealth - damageAmount) <= num && !isActive){
            int power;
            flash();
            isActive = true;
            per_maxhp =  Math.ceil(p.maxHealth* 0.1);
            power = Double.valueOf(per_maxhp).intValue();


            //同判断之前的添加力量代码
            addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, power), power));
            //addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            //kakaa面具说话代码
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new SFXAction("VO_CULTIST_1A"));
            addToBot(new TalkAction(true, "哼哼 哼~！", 1.0F, 2.0F));

        }}
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        //在用户使用牌时触发

    }

    @Override
    public void onVictory() {
        //在胜利时触发:清楚激活
        this.isActive = false;

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    //取玩家当前最大生命值上限
    public int getMaxHp(){
        //赋值玩家指针
        AbstractPlayer p = AbstractDungeon.player;
        //取最大生命
        int maxhp = p.maxHealth;
        double per_maxhp;
        //向上取整20%最大生命值
        per_maxhp =  Math.ceil( maxhp* 0.2 );
        int num;
        //转换到整数
        num = Double.valueOf(per_maxhp).intValue();
        return num;
    }


    @Override
    //拾取时触发
    public void onEquip() {
        int num = getMaxHp();
        AbstractDungeon.player.increaseMaxHp(num, true);
    }
    @Override
    public AbstractRelic makeCopy() {
        return new homa();
    }
}
