package me.Abhigya.core.util.reflection.resolver.minecraft;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.resolver.ClassResolver;
import me.Abhigya.core.util.server.Version;

/**
 * {@link ClassResolver} for <code>net.minecraft.server.*</code> classes
 */
public class NMSClassResolver extends ClassResolver {

    @Override
    public Class resolve(String... names) throws ClassNotFoundException {
        for (int i = 0; i < names.length; i++) {
            if (names[i].startsWith("net.minecraft"))
                continue;

            if (names[i].contains(".") && !CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1)) {
                /* use class name only */
                String[] path = names[i].split("\\.");
                names[i] = ClassReflection.NMS_CLASSES_PACKAGE + CoreAPI.getInstance().getServerVersion().name() + "." + path[path.length - 1];
                continue;
            }

            /* use the whole name */
            names[i] = ClassReflection.NMS_CLASSES_PACKAGE + CoreAPI.getInstance().getServerVersion().name() + "." + names[i];
        }
        return super.resolve(names);
    }
}