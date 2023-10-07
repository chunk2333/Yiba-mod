package patchs;

import YibaMod.YibaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

@SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CONSTRUCTOR, paramtypez=
        {String.class, String.class, String.class, int.class, String.class, AbstractCard.CardType.class, AbstractCard.CardColor.class,
        AbstractCard.CardRarity.class, AbstractCard.CardTarget.class, DamageInfo.DamageType.class})
public class NonePicCard {
    @SpirePostfixPatch
    public static void clear(AbstractCard __instance) {
        //YibaMod.logger.info("强制替换卡牌封面:" + name);
        //name = "阿巴巴";
        //YibaMod.logger.info("强制替换卡牌封面__instance:" + __instance.assetUrl);
        //__instance.assetUrl = "img/cards/witch/test.png";
    }
}
