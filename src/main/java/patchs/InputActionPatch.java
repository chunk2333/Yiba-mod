package patchs;

import YibaMod.YibaMod;
import actions.PlayRandomCardAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.helpers.input.ScrollInputProcessor;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(clz = InputHelper.class, method = "initialize")
public class InputActionPatch extends InputAdapter{

    public static String keys = "";

    @SpireInsertPatch(loc = 38, localvars = {"processor"})
    public static void PreFix(ScrollInputProcessor ___processor){
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(___processor);
        multiplexer.addProcessor(new InputActionPatch());
        YibaMod.logger.info("监听输入事件...");
        Gdx.input.setInputProcessor(multiplexer);
        //Gdx.input.setInputProcessor(new InputActionPatch());
    }

    @Override
    public boolean keyDown(int keycode) {
        // 处理按键按下事件
//        if (keycode == Input.Keys.A) {
//            // 如果按下的是A键
//            YibaMod.logger.info("A键被按下");
//        }
        String key1 = "19192020212221223029"; //↑↑↓↓←→←→BA
        //YibaMod.logger.info(Input.Keys.toString(keycode) + "键被按下");
        keys += Integer.toString(keycode);
        if (keys.contains(key1)){
            //YibaMod.logger.info("触发了秘技");
            keys = "";
            AbstractRoom currentRoom;
            try{
                currentRoom = AbstractDungeon.getCurrRoom();
            } catch (NullPointerException e){
                return true;
            }

            if (currentRoom == null){
                return true;
            }

            if (AbstractDungeon.actionManager.turnHasEnded &&
                    (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT){
                return true;
            }

            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    public void update() {
                        addToBot(new PlayRandomCardAction(

                                (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
                        this.isDone = true;
                    }
                });
            }

            return true;

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }

}
//
//@SpirePatch2(clz = InputHelper.class, method = "regainInputFocus")
//class InputAcitonRegainInputFocus{
//
//    @SpireInsertPatch(loc = 46, localvars = {"processor", "ignoreOneCycle"})
//    public static void fix(ScrollInputProcessor ___processor, @ByRef boolean[] ___ignoreOneCycle){
//        InputMultiplexer multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(___processor);
//        multiplexer.addProcessor(new InputActionPatch());
//        Gdx.input.setInputProcessor(multiplexer);
//        ___ignoreOneCycle[0] = true;
//        SpireReturn.Return();
//    }
//}