package me.Abhigya.core.util.bossbar.version.v1_8_R3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;

import java.lang.reflect.Field;
import java.util.Map;

public class BossTypeRegistering {

    protected static void register(Class<? extends Entity> clazz, String name, int id) {
        try {
            Map<String, Class<? extends Entity>> c = extract("c");
            Map<Class<? extends Entity>, String> d = extract("d");
            Map<Class<? extends Entity>, Integer> f = extract("f");
            Map<String, Integer> g = extract("g");

            c.put(name, clazz);
            d.put(clazz, name);
            f.put(clazz, Integer.valueOf(id));
            g.put(name, Integer.valueOf(id));
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                | SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    protected static <K, V> Map<K, V> extract(String field_name)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Class<EntityTypes> clazz = EntityTypes.class;
        Field field = clazz.getDeclaredField(field_name);

        field.setAccessible(true);
        return (Map<K, V>) field.get(null);
    }

}
