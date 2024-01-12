package patchs;

import Tools.YibaXY;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import java.util.ArrayList;

@SpirePatch2(clz = MapRoomNode.class, method = "isConnectedTo")
public class MapRoomNodePatch {

    public static boolean hasEdge(ArrayList<MapEdge> edges, YibaXY xy){
        for (MapEdge edge : edges){
            if (edge.dstY == xy.Y && edge.dstX == xy.X){
                return true;
            }

        }
        return false;
    }

    @SpireInsertPatch(loc = 109, localvars = {"node", "edges"})
    public static SpireReturn<Boolean> MapFix(MapRoomNode __instance, MapRoomNode ___node, ArrayList<MapEdge> ___edges){
        if (!AbstractDungeon.player.hasRelic("MechanismScroll")){
            return SpireReturn.Continue();
        }

        for (MapEdge edge : ___edges) {
            if (___node.y == edge.dstY) {
                YibaXY xy1 = new YibaXY();
                YibaXY xy2 = new YibaXY();
                int left = ___node.x - 1;
                int right = ___node.x + 1;
                xy1.X = left;
                xy1.Y = ___node.y;
                xy2.X = right;
                xy2.Y = xy1.Y;

                if (!MapRoomNodePatch.hasEdge(___edges, xy1)){
                    left -= 1;
                }

                if (!MapRoomNodePatch.hasEdge(___edges, xy2)){
                    right += 1;
                }

                if (edge.dstX == left || edge.dstX == right){
                    return SpireReturn.Return(true);
                }
            }

        }

        return SpireReturn.Continue();
    }
}
