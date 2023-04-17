package pathes;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LocalizationPathPatches {
    @SpirePatch(clz = CardCrawlGame.class, method = "create")
    public static class LocalizedStringsPatch {
        @SpireInsertPatch(rloc = 67)
        public static void Insert() {
            Pattern pattern = Pattern.compile("[觉绝决爵倔掘诀珏嚼]");
                    String[] targets = {
                    "monsters", "powers", "cards", "relics", "events", "potions", "credits", "tutorials", "keywords", "scoreBonuses",
                    "characters", "ui", "orb", "stance", "mod", "blights", "achievements" };
            for (String target : targets) {
                Map<String, Object> dict = ReflectionHacks.getPrivateStatic(LocalizedStrings.class, target);
                LocalizationPathPatches.patchDict(dict, pattern);
            }
            LocalizationPathPatches.patchKeyword(ReflectionHacks.getPrivateStatic(GameDictionary.class, "keywords"), pattern, true);
            LocalizationPathPatches.patchKeyword(ReflectionHacks.getPrivateStatic(GameDictionary.class, "parentWord"), pattern, true);
            LocalizationPathPatches.patchKeyword(ReflectionHacks.getPrivateStatic(BaseMod.class, "keywordUniqueNames"), pattern, true);
            LocalizationPathPatches.patchKeyword(ReflectionHacks.getPrivateStatic(BaseMod.class, "keywordUniquePrefixes"), pattern, false);
            LocalizationPathPatches.patchKeyword(ReflectionHacks.getPrivateStatic(BaseMod.class, "keywordProperNames"), pattern, false);
        }
    }

    private static void patchKeyword(Map<String, String> keywords, Pattern pattern, boolean patchValue) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : keywords.entrySet()) {
            String newKey = pattern.matcher(entry.getKey()).replaceAll("撅");
                    String newValue = patchValue ? pattern.matcher(entry.getValue()).replaceAll("撅") : entry.getValue();
            if (!newKey.equals(entry.getKey()) || !newValue.equals(entry.getValue()))
                result.put(newKey, newValue);
        }
        keywords.putAll(result);
    }

    private static void patchDict(Map<String, Object> dict, Pattern pattern) {
        for (Map.Entry<String, Object> entry : dict.entrySet()) {
            Object value = entry.getValue();
            try {
                for (Field field : value.getClass().getFields()) {
                    if (field.getType().equals(String.class)) {
                        String result = (String)field.get(value);
                        if (result != null)
                            field.set(value, pattern.matcher(result).replaceAll("撅"));
                    } else if (field.getType().equals(String[].class)) {
                        String[] result = (String[])field.get(value);
                        if (result != null)
                            for (int i = 0; i < result.length; i++) {
                                if (result[i] != null)
                                    result[i] = pattern.matcher(result[i]).replaceAll("撅");
                            }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
