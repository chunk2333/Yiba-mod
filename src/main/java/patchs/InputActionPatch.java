package patchs;

import YibaMod.YibaMod;
import actions.PlayRandomCardAction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(clz = InputHelper.class, method = "initialize")
public class InputActionPatch extends InputAdapter{

    public static String keys = "";

    @SpirePostfixPatch
    public static void PostFix(){
        YibaMod.logger.info("监听输入事件...");
        Gdx.input.setInputProcessor(new InputActionPatch());
    }

    @Override
    public boolean keyDown(int keycode) {
        // 处理按键按下事件
//        if (keycode == Input.Keys.A) {
//            // 如果按下的是A键
//            YibaMod.logger.info("A键被按下");
//        }
        String key1 = "19192020212221223029"; //↑↑↓↓←→←→BA
        YibaMod.logger.info(Input.Keys.toString(keycode) + "键被按下");
        keys += Integer.toString(keycode);
        if (keys.contains(key1)){
            YibaMod.logger.info("触发了秘技");
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

}