package relics;
//反物质
import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class Antimatter extends CustomRelic {
    public static final String ID = "Antimatter";

    private static final String IMG = "img/relics/Antimatter.png";

    private static final String IMG_OTL = "img/relics/outline/Antimatter.png";

    public Antimatter() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @SpireInsertPatch(rloc = 0, localvars = {"c", "monster", "energyOnUse"})
    public static void AntimatterOnUseCard(@ByRef AbstractCard[] ___c, @ByRef AbstractMonster[] ___monster, @ByRef int[] ___energyOnUse){
        if (AbstractDungeon.player.hasRelic("Antimatter")){
            if (___c[0].magicNumber <= 0){
                return;
            }
            AbstractDungeon.player.getRelic("Antimatter").flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player,AbstractDungeon.player.getRelic("Antimatter")));
            ___c[0].baseMagicNumber += 1;
            ___c[0].magicNumber += 1;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
