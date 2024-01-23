package patchs;
//邪教徒头套菜单
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

@SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfCombatLogic")
public class CultistMaskEasterEggPatch {

    @SpirePrefixPatch
    public static void PerFix(){
        if(AbstractDungeon.player.hasRelic("CultistMask")){
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (m.id.equals("AwakenedOne")){
                    return;
                }
            }

            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (m.id.equals("Cultist")){
                    if (!m.isDead){
                        AbstractDungeon.actionManager.addToBottom(new TalkAction(m, "自己人，自己人！", 0.3F, 2.5F));
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(m.hb.cX, m.hb.cY)));
                        AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
                    }
                }
            }

        }
    }
}
