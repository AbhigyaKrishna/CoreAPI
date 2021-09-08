package me.Abhigya.core.util.reflection.general;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.server.Version;
import org.reflections8.Reflections;
import org.reflections8.scanners.SubTypesScanner;
import org.reflections8.util.ClasspathHelper;
import org.reflections8.util.ConfigurationBuilder;
import org.reflections8.util.FilterBuilder;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class for reflecting classes
 */
public class ClassReflection {

    public static final String CRAFT_CLASSES_PACKAGE = "org.bukkit.craftbukkit.";
    public static final String NMS_CLASSES_PACKAGE = "net.minecraft.server.";

    private static final Map<String, Class<?>> CACHED_CLASSES = new ConcurrentHashMap<>();

    /**
     * Gets the member sub class with the provided name that is hold by the provided
     * {@code root} class.
     * <p>
     *
     * @param root     Class that holds the sub class
     * @param name     Name of the sub class
     * @param declared Whether or not the sub class is declared
     * @return Member sub class
     * @throws ClassNotFoundException if the sub class doesn't exist at the
     *                                {@code root} class
     */
    public static Class<?> getSubClass(Class<?> root, String name, boolean declared) throws ClassNotFoundException {
        for (Class<?> clazz : declared ? root.getDeclaredClasses() : root.getClasses()) {
            if (clazz.getSimpleName().equals(name)) {
                return clazz;
            }
        }
        throw new ClassNotFoundException("The sub class " + name + " doesn't exist!");
    }

    /**
     * Gets the member sub class with the provided name that is hold by the provided
     * {@code root} class. (<strong>No matter if the class is declared or not</strong>)
     * <p>
     *
     * @param root Class that holds the sub class
     * @param name Name of the sub class
     * @return Member sub class
     * @throws ClassNotFoundException if the sub class doesn't exist at the
     *                                {@code root} class
     */
    public static Class<?> getSubClass(Class<?> root, String name) throws ClassNotFoundException {
        try {
            return getSubClass(root, name, true);
        } catch (ClassNotFoundException ex) {
            try {
                return getSubClass(root, name, false);
            } catch (ClassNotFoundException ignored) {
            }
        }
        throw new ClassNotFoundException("The sub class " + name + " doesn't exist!");
    }

    /**
     * Gets a class within the craftbukkit package ({@value #CRAFT_CLASSES_PACKAGE})
     * or within a sub-package of it.
     * <p>
     *
     * @param name         Name of the class to get
     * @param package_name Name of the sub-package or null if the class is not
     *                     within a sub-package
     * @return Class with the provided name
     */
    public static Class<?> getCraftClass(String name, String package_name) {
        try {
            String id = "craft-" + (StringUtils.isBlank(package_name) ? "" : package_name.toLowerCase() + ".") + name;
            if (CACHED_CLASSES.containsKey(id))
                return CACHED_CLASSES.get(id);

            Class<?> clazz = Class.forName(CRAFT_CLASSES_PACKAGE + Version.getServerVersion().name() + "."
                    + (StringUtils.isBlank(package_name) ? "" : package_name.toLowerCase() + ".") + name);
            CACHED_CLASSES.put(id, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a class within the minecraft server ({@value #NMS_CLASSES_PACKAGE}) package.
     * <p>
     *
     * @param name       Name of the class to get
     * @param v17Package Package name/path after 'net.minecraft.'
     * @return Class with the provided name
     */
    public static Class<?> getNmsClass(String name, String v17Package) {
        try {
            if (CACHED_CLASSES.containsKey(name))
                return CACHED_CLASSES.get(name);

            Class<?> clazz;
            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1))
                clazz = Class.forName("net.minecraft." +
                        (StringUtils.isBlank(v17Package) ? "" : v17Package.toLowerCase() + ".") + name);
            else
                clazz = Class.forName(NMS_CLASSES_PACKAGE + CoreAPI.getInstance().getServerVersion().name() + "." + name);

            CACHED_CLASSES.put(name, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean nmsClassExist(String name, String v17Package) {
        try {
            Class<?> clazz;
            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1))
                clazz = Class.forName("net.minecraft." +
                        (StringUtils.isBlank(v17Package) ? "" : v17Package.toLowerCase() + ".") + name);
            else
                clazz = Class.forName(NMS_CLASSES_PACKAGE + CoreAPI.getInstance().getServerVersion().name() + "." + name);
            CACHED_CLASSES.put(name, clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Scans all classes accessible from the context class loader
     * which belong to the given package and subpackages.
     * <p>
     *
     * @param packageName Base package
     * @return The classes
     */
    public static Set<Class<?>> getClasses(String packageName) {
//        Reflections ref = new Reflections(new ConfigurationBuilder()
//                .setScanners(new SubTypesScanner(false))
//                .setUrls(ClasspathHelper.forClassLoader(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader()))
//                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));
//        return ref.getSubTypesOf(Object.class);
        return getClasses(packageName, Object.class);
    }

    public static <T> Set<Class<? extends T>> getClasses(String packageName, Class<T> inherit) {
//        Reflections ref = new Reflections(new ConfigurationBuilder()
//                .setScanners(new SubTypesScanner(false))
//                .setUrls(ClasspathHelper.forPackage(packageName))
//                .filterInputsBy(new FilterBuilder().includePackage(packageName)));
        Reflections ref = new Reflections(packageName);
        return ref.getSubTypesOf(inherit);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     * <p>
     *
     * @param directory   Base directory
     * @param packageName Package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    @Deprecated
    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * Scans the names of all the classes within a package contained by the provided
     * <strong>{@code .jar}</strong>.
     * <p>
     *
     * @param jarFile     File that represents the .jar
     * @param packageName Name of the desired package that contains the classes
     *                    to get, or null to get all the classes contained by the
     *                    .jar
     * @return Set with the name of the classes
     */
    @Deprecated
    public static Set<String> getClassNames(File jarFile, String packageName) {
        Set<String> names = new HashSet<>();
        try {
            JarFile file = new JarFile(jarFile);
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements(); ) {
                JarEntry jarEntry = entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");
                if ((packageName == null || packageName.trim().isEmpty() || name.startsWith(packageName.trim()))
                        && name.endsWith(".class")) {
                    names.add(name.substring(0, name.lastIndexOf(".class")));
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }

    /**
     * Scans all the classes within a package contained by the provided
     * <strong>{@code .jar}</strong>.
     * <p>
     *
     * @param jarFile     File that represents the .jar
     * @param packageName Name of the desired package that contains the classes
     *                    to get, or null to get all the classes contained by the
     *                    .jar
     * @return Set with the scanned classes
     */
    @Deprecated
    public static Set<Class<?>> getClasses(File jarFile, String packageName) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        getClassNames(jarFile, packageName).forEach(class_name -> {
            try {
                classes.add(Class.forName(class_name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return classes;
    }
}
