package patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class CharcoalPatch {

    @SpireInsertPatch(rloc = 0, localvars = {"c", "monster", "energyOnUse"})
    public static void CharcoalOnUseCard(@ByRef AbstractCard[] ___c, @ByRef AbstractMonster[] ___monster, @ByRef int[] ___energyOnUse){
        if (AbstractDungeon.player.hasRelic("Charcoal")){
            if (___c[0].name.contains("火焰") || ___c[0].name.contains("燃烧") || ___c[0].name.contains("热")){
                //负责监控是否触发
                AbstractDungeon.player.getRelic("Charcoal").flash();
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player,AbstractDungeon.player.getRelic("Charcoal")));
                ___c[0].baseDamage *= 1.5;
                ___c[0].damage *= 1.5;
                ___c[0].baseMagicNumber *= 1.5;
                ___c[0].magicNumber *= 1.5;
                ___c[0].baseBlock *= 1.5;
                ___c[0].block *= 1.5;
                ___c[0].baseHeal *= 1.5;
                ___c[0].heal *= 1.5;
                ___c[0].misc *= 1.5;
            }
        }
    }
}
