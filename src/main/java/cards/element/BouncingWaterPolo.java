package cards.element;
//弹跳水球
import Tools.YiBaHelper;
import YibaMod.YibaMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;
import power.HydroPower;

import java.util.ArrayList;

public class BouncingWaterPolo extends CustomCard {

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("BouncingWaterPolo");

    public static final String NAME = cardStrings.NAME;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "img/cards/witch/test.png";

    private static final int COST = 1;

    public static final String ID = "BouncingWaterPolo";

    public BouncingWaterPolo() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.Witch_COLOR, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(YibaMod.ELEMENT);
        this.tags.add(YibaMod.HYDRO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //使用卡牌时触发的动作
        AbstractMonster tempMonster;
        int tempMonsterNum;
        tempMonsterNum = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        ArrayList<AbstractMonster> list = new ArrayList<>((AbstractDungeon.getCurrRoom()).monsters.monsters);
        tempMonster = list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
        //造成伤害
        //addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        //addToTop(new VFXAction(new LightningEffect(tempMonster.hb.cX, tempMonster.hb.cY)));
        addToTop(new DamageAction(tempMonster, new DamageInfo(p, tempMonsterNum, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        //给予水元素
        for(int i = 0; i < this.magicNumber; i++){
            int random = AbstractDungeon.cardRandomRng.random(list.size() - 1);
            tempMonster = list.get(random);
            list.remove(random);
            addToBot(new ApplyPowerAction(tempMonster, tempMonster, new HydroPower(tempMonster, YiBaHelper.getPlayerMystery()), 1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        //复制卡牌时触发
        return new BouncingWaterPolo();
    }

    @Override
    public void upgrade() {
        //卡牌升级后的效果
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}