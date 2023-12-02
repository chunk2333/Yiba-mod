package patchs;

import Tools.YiBaHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.splash.SplashScreen;

@SpirePatch2(clz = SplashScreen.class, method = SpirePatch.CONSTRUCTOR)
public class YuanShenStart {
    @SpireInsertPatch(loc = 39, localvars={"img"})
    public static void StartYuanShen(@ByRef Texture[] ___img){
        if (YiBaHelper.YuanShenStartBool){
            ___img[0] = ImageMaster.loadImage("img/ui/yuanshen-black.png");
        } else {
            ___img[0] = ImageMaster.loadImage("img/ui/yuanshen.png");
        }

    }
}


@SpirePatch2(clz = SplashScreen.class, method = "render")
class YuanShenStartBlack{

    @SpireInsertPatch(loc = 118, localvars={"bgColor"})
    public static void StartYuanShenBlack (@ByRef Color[] ___bgColor){
        if (YiBaHelper.YuanShenStartBool){
            ___bgColor[0] = new Color(255.0F, 255.0F, 255.0F, 1.0F);
        }
    }

    @SpireInsertPatch(loc = 120, localvars={"shadowColor"})
    public static void StartYuanShenBlackFix01(@ByRef Color[] ___shadowColor){
        if (YiBaHelper.YuanShenStartBool){
            ___shadowColor[0] = new Color(255.0F, 255.0F, 255.0F, 1.0F);
        }

    }
    @SpireInsertPatch(loc = 139, localvars={"s"})
    public static void StartYuanShenBlackFix02(@ByRef Color[] ___s){
        if (YiBaHelper.YuanShenStartBool){
            ___s[0] = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        }

    }

}
@SpirePatch2(clz = SplashScreen.class, method = "update")
class YuanShenStartUpdate{


    public enum Phase {
        INIT, BOUNCE, FADE, WAIT, FADE_OUT;
    }

    public static Phase phase = Phase.INIT;

    @SpireInsertPatch(loc = 42, localvars={"timer", "img", "phase", "color", "y", "playSfx", "sfxKey", "sfxId"})
    public static SpireReturn<Void> fixUpdate(SplashScreen __instance, @ByRef float[] ___timer, @ByRef Texture[] ___img, @ByRef Color[] ___color, @ByRef float[] ___y, @ByRef boolean[] ___playSfx, @ByRef String[] ___sfxKey, @ByRef long[] ___sfxId){

        if (!YiBaHelper.YuanShenStartBool){
            return SpireReturn.Continue();
        }

        if ((InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) &&
                phase != Phase.FADE_OUT) {
            phase = Phase.FADE_OUT;
            ___timer[0] = 1.0F;
            if (___sfxKey[0] != null)
                CardCrawlGame.sound.fadeOut(___sfxKey[0], ___sfxId[0]);
        }
        switch (phase){
            case INIT:
                ___timer[0] -= Gdx.graphics.getDeltaTime();
                if (___timer[0] < 0.0F) {
                    phase = Phase.BOUNCE;
                    ___timer[0] = 1.2F;
                }
                break;
            case BOUNCE:
                ___timer[0] -= Gdx.graphics.getDeltaTime();
                //___color[0].a = Interpolation.fade.apply(1.0F, 0.0F, ___timer[0] / 1.2F);
                ___color[0].a = 1.0F;
                //___y[0] = Interpolation.elasticIn.apply(Settings.HEIGHT / 2.0F, Settings.HEIGHT / 2.0F - 200.0F * Settings.scale, ___timer[0] / 1.2F);
                ___y[0] = Settings.HEIGHT / 2.0F;
                if (___timer[0] < 0.96000004F && !___playSfx[0]) {
                    ___playSfx[0] = true;
                    ___sfxId[0] = CardCrawlGame.sound.play("YuanShenStartBgm");
                }
                if (___timer[0] < 0.0F) {
                    phase = Phase.FADE;
                    ___timer[0] = 6.5F;
                }
                break;
            case FADE:
                ___timer[0] -= Gdx.graphics.getDeltaTime();
                if (___timer[0] < 0.0F) {
                    phase = Phase.WAIT;
                    ___timer[0] = 1.5F;
                }
                break;
            case WAIT:
                ___timer[0] -= Gdx.graphics.getDeltaTime();
                if (___timer[0] < 0.0F) {
                    phase = Phase.FADE_OUT;
                    ___timer[0] = 5.0F;
                }
                break;
            case FADE_OUT:
                ___timer[0] -= Gdx.graphics.getDeltaTime();
                if (___timer[0] < 0.0F) {
                    ___img[0].dispose();
                    CardCrawlGame.sound.fadeOut("YuanShenStartBgm", ___sfxId[0]);
                    __instance.isDone = true;
                }
                break;
        }
        return SpireReturn.Return();

    }
}
