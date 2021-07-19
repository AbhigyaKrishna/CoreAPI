package me.Abhigya.core.util.reflection.general;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * Class for reflecting fields
 */
public class FieldReflection {

    /**
     * Gets the field with the provided name from the provided class.
     * <p>
     *
     * @param clazz    Class that holds the field
     * @param name     Name of the field
     * @param declared Whether the desired field is declared or not
     * @return {@code Field} object of the provided class specified by
     * {@code name}
     * @throws NoSuchFieldException if a field with the specified name is not found.
     * @throws SecurityException    If a security manager, <i>s</i>, is present and
     *                              any of the following conditions is met:
     *
     *                              <ul>
     *                              <li>the caller's class loader is not the same as
     *                              the class loader of this class and invocation of
     *                              {@link SecurityManager#checkPermission
     *                              s.checkPermission} method with
     *                              {@code RuntimePermission("accessDeclaredMembers")}
     *                              denies access to the declared field</li>
     *
     *                              <li>the caller's class loader is not the same as
     *                              or an ancestor of the class loader for the
     *                              current class and invocation of
     *                              {@link SecurityManager#checkPackageAccess
     *                              s.checkPackageAccess()} denies access to the
     *                              package of this class</li>
     *                              </ul>
     */
    public static Field get(Class<?> clazz, String name, boolean declared)
            throws SecurityException, NoSuchFieldException {
        return declared ? clazz.getDeclaredField(name) : clazz.getField(name);
    }

    /**
     * Gets the field with the provided name from the provided class. (<strong>No
     * matter if declared or not</strong>).
     * <p>
     *
     * @param clazz Class that holds the field
     * @param name  Name of the field
     * @return {@code Field} object of the provided class specified by
     * {@code name}
     * @throws NoSuchFieldException if a field with the specified name is not found
     * @throws SecurityException    if a security manager, <i>s</i>, is present and
     *                              any of the following conditions is met:
     *
     *                              <ul>
     *                              <li>the caller's class loader is not the same as
     *                              the class loader of this class and invocation of
     *                              {@link SecurityManager#checkPermission
     *                              s.checkPermission} method with
     *                              {@code RuntimePermission("accessDeclaredMembers")}
     *                              denies access to the declared field</li>
     *
     *                              <li>the caller's class loader is not the same as
     *                              or an ancestor of the class loader for the
     *                              current class and invocation of
     *                              {@link SecurityManager#checkPackageAccess
     *                              s.checkPackageAccess()} denies access to the
     *                              package of this class</li>
     *                              </ul>
     * @see #get(Class, String, boolean)
     */
    public static Field get(Class<?> clazz, String name)
            throws SecurityException, NoSuchFieldException {
        try {
            return get(clazz, name, false);
        } catch (NoSuchFieldException ex) {
            return get(clazz, name, true);
        }
    }

    /**
     * Gets the field with the provided name from the provided class, and sets it
     * accessible.
     * <p>
     *
     * @param clazz    Class that holds the field
     * @param name     Name of the field
     * @param declared Whether the desired field is declared or not
     * @return {@code Field} object of the provided class specified by
     * {@code name}
     * @throws NoSuchFieldException if a field with the specified name is not found
     * @throws SecurityException    if a security manager, <i>s</i>, is present and
     *                              any of the following conditions is met:
     *
     *                              <ul>
     *                              <li>the caller's class loader is not the same as
     *                              the class loader of this class and invocation of
     *                              {@link SecurityManager#checkPermission
     *                              s.checkPermission} method with
     *                              {@code RuntimePermission("accessDeclaredMembers")}
     *                              denies access to the declared field</li>
     *
     *                              <li>the caller's class loader is not the same as
     *                              or an ancestor of the class loader for the
     *                              current class and invocation of
     *                              {@link SecurityManager#checkPackageAccess
     *                              s.checkPackageAccess()} denies access to the
     *                              package of this class</li>
     *
     *                              <li>the field cannot be made accessible.
     *                              </ul>
     */
    public static Field getAccessible(Class<?> clazz, String name, boolean declared)
            throws SecurityException, NoSuchFieldException {
        final Field field = get(clazz, name, declared);
        field.setAccessible(true);
        return field;
    }

    /**
     * Gets the field with the provided name from the provided class, and sets it
     * accessible. (<strong>No matter declared or not</strong>).
     * <p>
     *
     * @param clazz Class that holds the field
     * @param name  Name of the field
     * @return {@code Field} object of the provided class specified by
     * {@code name}
     * @throws NoSuchFieldException if a field with the specified name is not found
     * @throws SecurityException    if a security manager, <i>s</i>, is present and
     *                              any of the following conditions is met:
     *
     *                              <ul>
     *                              <li>the caller's class loader is not the same as
     *                              the class loader of this class and invocation of
     *                              {@link SecurityManager#checkPermission
     *                              s.checkPermission} method with
     *                              {@code RuntimePermission("accessDeclaredMembers")}
     *                              denies access to the declared field</li>
     *
     *                              <li>the caller's class loader is not the same as
     *                              or an ancestor of the class loader for the
     *                              current class and invocation of
     *                              {@link SecurityManager#checkPackageAccess
     *                              s.checkPackageAccess()} denies access to the
     *                              package of this class</li>
     *
     *                              <li>the field cannot be made accessible.</li>
     *                              </ul>
     * @see #getAccessible(Class, String, boolean)
     */
    public static Field getAccessible(Class<?> clazz, String name)
            throws SecurityException, NoSuchFieldException {
        final Field field = get(clazz, name);
        field.setAccessible(true);
        return field;
    }

    /**
     * Returns the value of the field represented by the field with the provided
     * name, on the specified object. The value is automatically wrapped in an
     * object if it has a primitive type.
     * <p>
     *
     * @param <T>      Type of the object to return
     * @param object   Object from which the represented field's value is to be
     *                 extracted
     * @param name     Name of the field to get
     * @param declared Whether the field is declared or not
     * @return Value of the field
     * @throws NoSuchFieldException     if a field with the specified name is not
     *                                  found
     * @throws IllegalArgumentException if the specified object is not an instance
     *                                  of the class or interface declaring the
     *                                  underlying field (or a subclass or
     *                                  implementor thereof).
     * @throws IllegalAccessException   if the field is completely inaccessible.
     * @throws SecurityException        if a security manager, <i>s</i>, is present
     *                                  and any of the following conditions is met:
     *
     *                                  <ul>
     *                                  <li>the caller's class loader is not the
     *                                  same as the class loader of this class and
     *                                  invocation of
     *                                  {@link SecurityManager#checkPermission
     *                                  s.checkPermission} method with
     *                                  {@code RuntimePermission("accessDeclaredMembers")}
     *                                  denies access to the declared field</li>
     *
     *                                  <li>the caller's class loader is not the
     *                                  same as or an ancestor of the class loader
     *                                  for the current class and invocation of
     *                                  {@link SecurityManager#checkPackageAccess
     *                                  s.checkPackageAccess()} denies access to the
     *                                  package of this class</li>
     *
     *                                  <li>the field cannot be made accessible.</li>
     *                                  </ul>
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(Object object, String name, boolean declared)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        final Field field = getAccessible(object.getClass(), name, declared);
        final boolean b0 = field.isAccessible();

        field.setAccessible(true);
        try {
            return (T) field.get(object);
        } catch (Throwable ex) {
            throw ex;
        } finally {
            field.setAccessible(b0);
        }
    }

    /**
     * Returns the value of the field represented by the field with the provided
     * name (<strong>No matter if declared or not</strong>), on the specified
     * object. The value is automatically wrapped in an object if it has a primitive
     * type.
     * <p>
     *
     * @param <T>    Type of the object to return
     * @param object Object from which the represented field's value is to be
     *               extracted
     * @param name   Name of the field to get
     * @return Value of the field
     * @throws NoSuchFieldException     if a field with the specified name is not
     *                                  found
     * @throws IllegalArgumentException if the specified object is not an instance
     *                                  of the class or interface declaring the
     *                                  underlying field (or a subclass or
     *                                  implementor thereof).
     * @throws IllegalAccessException   if the field is completely inaccessible.
     * @throws SecurityException        if a security manager, <i>s</i>, is present
     *                                  and any of the following conditions is met:
     *
     *                                  <ul>
     *                                  <li>the caller's class loader is not the
     *                                  same as the class loader of this class and
     *                                  invocation of
     *                                  {@link SecurityManager#checkPermission
     *                                  s.checkPermission} method with
     *                                  {@code RuntimePermission("accessDeclaredMembers")}
     *                                  denies access to the declared field</li>
     *
     *                                  <li>the caller's class loader is not the
     *                                  same as or an ancestor of the class loader
     *                                  for the current class and invocation of
     *                                  {@link SecurityManager#checkPackageAccess
     *                                  s.checkPackageAccess()} denies access to the
     *                                  package of this class</li>
     *
     *                                  <li>the field cannot be made accessible.</li>
     *                                  </ul>
     * @see #getValue(Object, String, boolean)
     */
    public static <T> T getValue(Object object, String name)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        try {
            return getValue(object, name, false);
        } catch (NoSuchFieldException ex) {
            return getValue(object, name, true);
        }
    }

    /**
     * Sets the field represented by the field with the provided name, on the
     * specified object argument to the specified new value. The new value is
     * automatically unwrapped if the underlying field has a primitive type.
     * <p>
     *
     * @param object   Object whose field should be modified
     * @param name     Name of the field to set
     * @param declared Whether the field is declared or not
     * @param value    New value for the field of {@code object} being modified
     * @return Same Object, for chaining
     * @throws NoSuchFieldException   if a field with the specified name is not
     *                                found
     * @throws IllegalAccessException if the field is completely inaccessible
     * @throws SecurityException      if a security manager, <i>s</i>, is present
     *                                and any of the following conditions is met:
     *
     *                                <ul>
     *                                <li>the caller's class loader is not the same
     *                                as the class loader of this class and
     *                                invocation of
     *                                {@link SecurityManager#checkPermission
     *                                s.checkPermission} method with
     *                                {@code RuntimePermission("accessDeclaredMembers")}
     *                                denies access to the declared field</li>
     *
     *                                <li>the caller's class loader is not the same
     *                                as or an ancestor of the class loader for the
     *                                current class and invocation of
     *                                {@link SecurityManager#checkPackageAccess
     *                                s.checkPackageAccess()} denies access to the
     *                                package of this class</li>
     *
     *                                <li>the field cannot be made accessible.</li>
     *                                </ul>
     * @see Field#set(Object, Object)
     */
    public static Object setValue(Object object, String name, boolean declared, Object value)
            throws SecurityException, NoSuchFieldException, IllegalAccessException {
        final Field field = getAccessible(object.getClass(), name, declared);
        final boolean b0 = field.isAccessible();

        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (Throwable ex) {
            throw ex;
        } finally {
            field.setAccessible(b0);
        }
        return object;
    }

    /**
     * Sets the field represented by the field with the provided name (<strong>No
     * matter if declared or not</strong>), on the specified object argument to the
     * specified new value. The new value is automatically unwrapped if the
     * underlying field has a primitive type.
     * <p>
     *
     * @param object Object whose field should be modified
     * @param name   Name of the field to set
     * @param value  New value for the field of {@code object} being modified
     * @return Same Object, for chaining
     * @throws NoSuchFieldException   if a field with the specified name is not
     *                                found
     * @throws IllegalAccessException if the field is completely inaccessible
     * @throws SecurityException      if a security manager, <i>s</i>, is present
     *                                and any of the following conditions is met:
     *
     *                                <ul>
     *                                <li>the caller's class loader is not the same
     *                                as the class loader of this class and
     *                                invocation of
     *                                {@link SecurityManager#checkPermission
     *                                s.checkPermission} method with
     *                                {@code RuntimePermission("accessDeclaredMembers")}
     *                                denies access to the declared field</li>
     *
     *                                <li>the caller's class loader is not the same
     *                                as or an ancestor of the class loader for the
     *                                current class and invocation of
     *                                {@link SecurityManager#checkPackageAccess
     *                                s.checkPackageAccess()} denies access to the
     *                                package of this class</li>
     *
     *                                <li>the field cannot be made accessible.</li>
     *                                </ul>
     * @see Field#set(Object, Object)
     */
    public static Object setValue(Object object, String name, Object value)
            throws SecurityException, NoSuchFieldException, IllegalAccessException {
        try {
            return setValue(object, name, false, value);
        } catch (NoSuchFieldException ex) {
            return setValue(object, name, true, value);
        }
    }

    /**
     * Sets the provided field as accessible/inaccessible.
     * <p>
     * <strong> Note that the if the field is <code>final</code>, it will set as
     * "not final", so that the value can be changed. </strong>
     * <p>
     *
     * @param field      Field to set
     * @param accessible Whether or not the field is accessible
     * @return Same Object, for chaining
     * @throws SecurityException if the request is denied
     */
    public static Field setAccessible(Field field, boolean accessible) throws SecurityException {
        field.setAccessible(accessible);
        if (accessible) {
            try {
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//				modifiersField.setAccessible ( false );
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        return field;
    }

    /**
     * Sets the provided field as accessible.
     * <p>
     * <strong> Note that the if the field is <code>final</code>, it will set as
     * "not final", so that the value can be changed. </strong>
     * <p>
     *
     * @param field Field to set
     * @return Same Object, for chaining
     * @throws SecurityException if the request is denied
     * @see #setAccessible(Field, boolean)
     */
    public static Field setAccessible(Field field)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return setAccessible(field, true);
    }

    /**
     * Returns the classes of the types of a parameterized field, like
     * {@link Collection} or {@link Map}.
     * <p>
     * Examples: - {@code getParameterizedTypesClasses(Collection<Integer>)} (of a
     * collection field in this case) will return: [java.lang.Integer] -
     * {@code getParameterizedTypesClasses(Map<Integer, String>)} (of a map field in this
     * case) will return: [java.lang.Integer, java.lang.String]
     * <p>
     *
     * @param field Parameterized field
     * @return Classes of the types of a parameterized field
     */
    public static Class<?>[] getParameterizedTypesClasses(Field field) {
        if (!(field.getGenericType() instanceof ParameterizedType)) {
            throw new IllegalArgumentException("The field doesn't represent a parameterized type!");
        }

        ParameterizedType parameterized_type = (ParameterizedType) field.getGenericType();
        Type[] types_arguments = parameterized_type.getActualTypeArguments();
        Class<?>[] classes = new Class<?>[types_arguments.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = (Class<?>) types_arguments[i];
        }
        return classes;
    }
}
