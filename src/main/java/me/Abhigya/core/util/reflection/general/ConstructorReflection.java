package me.Abhigya.core.util.reflection.general;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/** Class for reflecting constructor */
public class ConstructorReflection {

    /**
     * Returns a {@code Constructor} object that reflects the specified public constructor of the
     * class represented by the provided {@code Class} object. The {@code parameter_types} parameter
     * is an array of {@code Class} objects that identify the constructor's formal parameter types,
     * in declared order.
     *
     * <p>
     *
     * @param clazz Class that holds the constructor
     * @param declared Whether or not the constructor is declared
     * @param parameter_types Parameter array
     * @return {@code Constructor} object that matches the specified {@code parameter_types}
     * @throws NoSuchMethodException if a matching constructor is not found
     * @throws SecurityException if a security manager, <i>s</i>, is present and any of the
     *     following conditions is met:
     *     <ul>
     *       <li>the caller's class loader is not the same as the class loader of this class and
     *           invocation of {@link SecurityManager#checkPermission s.checkPermission} method with
     *           {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared
     *           constructor
     *       <li>the caller's class loader is not the same as or an ancestor of the class loader for
     *           the current class and invocation of {@link SecurityManager#checkPackageAccess
     *           s.checkPackageAccess()} denies access to the package of this class
     *     </ul>
     */
    public static Constructor<?> get(Class<?> clazz, boolean declared, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        return declared
                ? clazz.getDeclaredConstructor(parameter_types)
                : clazz.getConstructor(parameter_types);
    }

    /**
     * Returns a {@code Constructor} object (<strong>No matter if declared or not</strong>) that
     * reflects the specified public constructor of the class represented by the provided {@code
     * Class} object. The {@code parameter_types} parameter is an array of {@code Class} objects
     * that identify the constructor's formal parameter types, in declared order.
     *
     * <p>
     *
     * @param clazz Class that holds the constructor
     * @param parameter_types Parameter array
     * @return {@code Constructor} object that matches the specified {@code parameter_types}
     * @throws NoSuchMethodException if a matching constructor is not found
     * @throws SecurityException if a security manager, <i>s</i>, is present and any of the
     *     following conditions is met:
     *     <ul>
     *       <li>the caller's class loader is not the same as the class loader of this class and
     *           invocation of {@link SecurityManager#checkPermission s.checkPermission} method with
     *           {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared
     *           constructor
     *       <li>the caller's class loader is not the same as or an ancestor of the class loader for
     *           the current class and invocation of {@link SecurityManager#checkPackageAccess
     *           s.checkPackageAccess()} denies access to the package of this class
     *     </ul>
     *
     * @see #get(Class, boolean, Class...)
     */
    public static Constructor<?> get(Class<?> clazz, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        try {
            return get(clazz, false, parameter_types);
        } catch (NoSuchMethodException ex) {
            return get(clazz, true, parameter_types);
        }
    }

    /**
     * Returns a {@code Constructor} object that reflects the specified public constructor of the
     * class represented by the provided {@code Class} object. The {@code parameter_types} parameter
     * is an array of {@code Class} objects that identify the constructor's formal parameter types,
     * in declared order.
     *
     * <p>
     *
     * @param clazz Class that holds the constructor
     * @param declared Whether or not the constructor is declared
     * @param parameter_types Parameter array
     * @return {@code Constructor} object that matches the specified {@code parameter_types}
     * @throws NoSuchMethodException if a matching constructor is not found
     * @throws SecurityException if a security manager, <i>s</i>, is present and any of the
     *     following conditions is met:
     *     <ul>
     *       <li>the caller's class loader is not the same as the class loader of this class and
     *           invocation of {@link SecurityManager#checkPermission s.checkPermission} method with
     *           {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared
     *           constructor
     *       <li>the caller's class loader is not the same as or an ancestor of the class loader for
     *           the current class and invocation of {@link SecurityManager#checkPackageAccess
     *           s.checkPackageAccess()} denies access to the package of this class
     *     </ul>
     */
    public static Constructor<?> getAccessible(
            Class<?> clazz, boolean declared, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        final Constructor<?> constructor = get(clazz, declared, parameter_types);
        constructor.setAccessible(true);
        return constructor;
    }

    /**
     * Returns a {@code Constructor} object (<strong>No matter if declared or not</strong>) that
     * reflects the specified public constructor of the class represented by the provided {@code
     * Class} object. The {@code parameter_types} parameter is an array of {@code Class} objects
     * that identify the constructor's formal parameter types, in declared order.
     *
     * <p>
     *
     * @param clazz Class that holds the constructor
     * @param parameter_types Parameter array
     * @return {@code Constructor} object that matches the specified {@code parameter_types}
     * @throws NoSuchMethodException if a matching constructor is not found
     * @throws SecurityException if a security manager, <i>s</i>, is present and any of the
     *     following conditions is met:
     *     <ul>
     *       <li>the caller's class loader is not the same as the class loader of this class and
     *           invocation of {@link SecurityManager#checkPermission s.checkPermission} method with
     *           {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared
     *           constructor
     *       <li>the caller's class loader is not the same as or an ancestor of the class loader for
     *           the current class and invocation of {@link SecurityManager#checkPackageAccess
     *           s.checkPackageAccess()} denies access to the package of this class
     *     </ul>
     */
    public static Constructor<?> getAccessible(Class<?> clazz, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        try {
            return getAccessible(clazz, false, parameter_types);
        } catch (NoSuchMethodException ex) {
            return getAccessible(clazz, true, parameter_types);
        }
    }

    /**
     * Uses the matching {@code Constructor} object to create and initialize a new instance of the
     * constructor's declaring class, with the specified initialization parameters. Individual
     * parameters are automatically unwrapped to match primitive formal parameters, and both
     * primitive and reference parameters are subject to method invocation conversions as necessary.
     * <br>
     * If the number of formal parameters required by the underlying constructor is 0, the supplied
     * {@code arguments} array may be of length 0 or null. <br>
     * If the constructor's declaring class is an inner class in a non-static context, the first
     * argument to the constructor needs to be the enclosing instance; see section 15.9.3 of
     * <cite>The Java&trade; Language Specification</cite>. <br>
     * If the required access and argument checks succeed and the instantiation will proceed, the
     * constructor's declaring class is initialized if it has not already been initialized. <br>
     * If the constructor completes normally, returns the newly created and initialized instance.
     *
     * @param clazz Class that holds the constructor
     * @param declared Whether or not the constructor is declared
     * @param parameter_types Parameters of the constructor, used for matching it
     * @param arguments Array of objects to be passed as arguments to the constructor call; values
     *     of primitive types are wrapped in a wrapper object of the appropriate type (e.g. a {@code
     *     float} in a {@link java.lang.Float Float})
     * @return Object created by calling the matching constructor
     * @throws NoSuchMethodException if a matching constructor is not found
     * @throws SecurityException if a security manager, <i>s</i>, is present and any of the
     *     following conditions is met:
     *     <ul>
     *       <li>the caller's class loader is not the same as the class loader of this class and
     *           invocation of {@link SecurityManager#checkPermission s.checkPermission} method with
     *           {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared
     *           constructor
     *       <li>the caller's class loader is not the same as or an ancestor of the class loader for
     *           the current class and invocation of {@link SecurityManager#checkPackageAccess
     *           s.checkPackageAccess()} denies access to the package of this class
     *     </ul>
     *
     * @throws InstantiationException if the class that declares the underlying constructor
     *     represents an abstract class.
     * @throws IllegalAccessException if the {@code Constructor} object is enforcing Java language
     *     access control and the underlying constructor is inaccessible.
     * @throws IllegalArgumentException if the number of actual and formal parameters differ; if an
     *     unwrapping conversion for primitive arguments fails; or if, after possible unwrapping, a
     *     parameter value cannot be converted to the corresponding formal parameter type by a
     *     method invocation conversion; if this constructor pertains to an enum type.
     * @throws InvocationTargetException if the underlying constructor throws an exception.
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(
            Class<?> clazz, boolean declared, Class<?>[] parameter_types, Object... arguments)
            throws NoSuchMethodException,
                    SecurityException,
                    InstantiationException,
                    IllegalAccessException,
                    IllegalArgumentException,
                    InvocationTargetException {
        return (T) getAccessible(clazz, declared, parameter_types).newInstance(arguments);
    }

    //	/**
    //	 * Uses the matching {@code Constructor} object to create and initialize a new
    //	 * instance of the constructor's declaring class, with the specified
    //	 * initialization arguments. Individual parameters are automatically unwrapped
    //	 * to match primitive formal parameters, and both primitive and reference
    //	 * parameters are subject to method invocation conversions as necessary.
    //	 *
    //	 * <br>
    //	 * If the number of formal parameters required by the underlying constructor is
    //	 * 0, the supplied {@code arguments} array may be of length 0 or null.
    //	 *
    //	 * <br>
    //	 * If the constructor's declaring class is an inner class in a non-static
    //	 * context, the first argument to the constructor needs to be the enclosing
    //	 * instance; see section 15.9.3 of <cite>The Java&trade; Language
    //	 * Specification</cite>.
    //	 *
    //	 * <br>
    //	 * If the required access and argument checks succeed and the instantiation will
    //	 * proceed, the constructor's declaring class is initialized if it has not
    //	 * already been initialized.
    //	 *
    //	 * <br>
    //	 * If the constructor completes normally, returns the newly created and
    //	 * initialized instance.
    //	 *
    //	 * @param <T>
    //	 * @param clazz     Class that holds the constructor.
    //	 * @param declared  Whether or not the constructor is declared.
    //	 * @param arguments Array of objects to be passed as arguments to the
    //	 *                  constructor call; values of primitive types are wrapped in a
    //	 *                  wrapper object of the appropriate type (e.g. a {@code float}
    //	 *                  in a {@link java.lang.Float Float})
    //	 * @return Object created by calling the matching constructor.
    //	 * @throws NoSuchMethodException     if a matching constructor is not found.
    //	 * @throws SecurityException         if a security manager, <i>s</i>, is present
    //	 *                                   and any of the following conditions is met:
    //	 *
    //	 *                                   <ul>
    //	 *
    //	 *                                   <li>the caller's class loader is not the
    //	 *                                   same as the class loader of this class and
    //	 *                                   invocation of
    //	 *                                   {@link SecurityManager#checkPermission
    //	 *                                   s.checkPermission} method with
    //	 *                                   {@code RuntimePermission("accessDeclaredMembers")}
    //	 *                                   denies access to the declared constructor
    //	 *
    //	 *                                   <li>the caller's class loader is not the
    //	 *                                   same as or an ancestor of the class loader
    //	 *                                   for the current class and invocation of
    //	 *                                   {@link SecurityManager#checkPackageAccess
    //	 *                                   s.checkPackageAccess()} denies access to
    //	 *                                   the package of this class
    //	 *
    //	 *                                   </ul>
    //	 * @throws InstantiationException    if the class that declares the underlying
    //	 *                                   constructor represents an abstract class.
    //	 * @throws IllegalAccessException    if the {@code Constructor} object is
    //	 *                                   enforcing Java language access control and
    //	 *                                   the underlying constructor is inaccessible.
    //	 * @throws IllegalArgumentException  if the number of actual and formal
    //	 *                                   parameters differ; if an unwrapping
    //	 *                                   conversion for primitive arguments fails;
    //	 *                                   or if, after possible unwrapping, a
    //	 *                                   parameter value cannot be converted to the
    //	 *                                   corresponding formal parameter type by a
    //	 *                                   method invocation conversion; if this
    //	 *                                   constructor pertains to an enum type.
    //	 * @throws InvocationTargetException if the underlying constructor throws an
    //	 *                                   exception.
    //	 */
    //	public static <T> T newInstance ( Class < ? > clazz , boolean declared , Object... arguments
    // )
    //			throws NoSuchMethodException, SecurityException, InstantiationException,
    // IllegalAccessException,
    //			IllegalArgumentException, InvocationTargetException {
    //		return newInstance ( clazz , declared , getTypes ( arguments ), arguments );
    //	}

    /**
     * Uses the matching {@code Constructor} object (<strong>No matter if declared or not</strong>)
     * to create and initialize a new instance of the constructor's declaring class, with the
     * specified initialization parameters. Individual parameters are automatically unwrapped to
     * match primitive formal parameters, and both primitive and reference parameters are subject to
     * method invocation conversions as necessary. <br>
     * If the number of formal parameters required by the underlying constructor is 0, the supplied
     * {@code arguments} array may be of length 0 or null. <br>
     * If the constructor's declaring class is an inner class in a non-static context, the first
     * argument to the constructor needs to be the enclosing instance; see section 15.9.3 of
     * <cite>The Java&trade; Language Specification</cite>. <br>
     * If the required access and argument checks succeed and the instantiation will proceed, the
     * constructor's declaring class is initialized if it has not already been initialized. <br>
     * If the constructor completes normally, returns the newly created and initialized instance.
     *
     * @param clazz Class that holds the constructor
     * @param parameter_types Parameters of the constructor, used for matching it
     * @param arguments Array of objects to be passed as arguments to the constructor call; values
     *     of primitive types are wrapped in a wrapper object of the appropriate type (e.g. a {@code
     *     float} in a {@link java.lang.Float Float})
     * @return Object created by calling the matching constructor
     * @throws NoSuchMethodException if a matching constructor is not found.
     * @throws SecurityException if a security manager, <i>s</i>, is present and any of the
     *     following conditions is met:
     *     <ul>
     *       <li>the caller's class loader is not the same as the class loader of this class and
     *           invocation of {@link SecurityManager#checkPermission s.checkPermission} method with
     *           {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared
     *           constructor
     *       <li>the caller's class loader is not the same as or an ancestor of the class loader for
     *           the current class and invocation of {@link SecurityManager#checkPackageAccess
     *           s.checkPackageAccess()} denies access to the package of this class
     *     </ul>
     *
     * @throws InstantiationException if the class that declares the underlying constructor
     *     represents an abstract class.
     * @throws IllegalAccessException if the {@code Constructor} object is enforcing Java language
     *     access control and the underlying constructor is inaccessible.
     * @throws IllegalArgumentException if the number of actual and formal parameters differ; if an
     *     unwrapping conversion for primitive arguments fails; or if, after possible unwrapping, a
     *     parameter value cannot be converted to the corresponding formal parameter type by a
     *     method invocation conversion; if this constructor pertains to an enum type.
     * @throws InvocationTargetException if the underlying constructor throws an exception.
     */
    public static <T> T newInstance(Class<?> clazz, Class<?>[] parameter_types, Object... arguments)
            throws NoSuchMethodException,
                    SecurityException,
                    InstantiationException,
                    IllegalAccessException,
                    IllegalArgumentException,
                    InvocationTargetException {
        try {
            return newInstance(clazz, false, parameter_types, arguments);
        } catch (NoSuchMethodException ex) {
            return newInstance(clazz, true, parameter_types, arguments);
        }
    }

    //	/**
    //	 * Uses the matching {@code Constructor} object (<strong>No matter if declared
    //	 * or not</strong>) to create and initialize a new instance of the constructor's
    //	 * declaring class, with the specified initialization arguments. Individual
    //	 * parameters are automatically unwrapped to match primitive formal parameters,
    //	 * and both primitive and reference parameters are subject to method invocation
    //	 * conversions as necessary.
    //	 * <p>
    //	 * Note that <strong>{@code null}</strong> arguments are unsupported.
    //	 * <p>
    //	 * If the number of formal parameters required by the underlying constructor is
    //	 * 0, the supplied {@code arguments} array may be of length 0 or null.
    //	 *
    //	 * <p>
    //	 * If the constructor's declaring class is an inner class in a non-static
    //	 * context, the first argument to the constructor needs to be the enclosing
    //	 * instance; see section 15.9.3 of <cite>The Java&trade; Language
    //	 * Specification</cite>.
    //	 *
    //	 * <p>
    //	 * If the required access and argument checks succeed and the instantiation will
    //	 * proceed, the constructor's declaring class is initialized if it has not
    //	 * already been initialized.
    //	 *
    //	 * <p>
    //	 * If the constructor completes normally, returns the newly created and
    //	 * initialized instance.
    //	 *
    //	 * @param <T>
    //	 * @param clazz     the class that holds the constructor.
    //	 * @param arguments array of objects to be passed as arguments to the
    //	 *                  constructor call; values of primitive types are wrapped in a
    //	 *                  wrapper object of the appropriate type (e.g. a {@code float}
    //	 *                  in a {@link java.lang.Float Float})
    //	 * @return a new object created by calling the matching constructor.
    //	 * @throws NoSuchMethodException     if a matching constructor is not found.
    //	 * @throws SecurityException         SecurityException If a security manager,
    //	 *                                   <i>s</i>, is present and any of the
    //	 *                                   following conditions is met:
    //	 *
    //	 *                                   <ul>
    //	 *
    //	 *                                   <li>the caller's class loader is not the
    //	 *                                   same as the class loader of this class and
    //	 *                                   invocation of
    //	 *                                   {@link SecurityManager#checkPermission
    //	 *                                   s.checkPermission} method with
    //	 *                                   {@code RuntimePermission("accessDeclaredMembers")}
    //	 *                                   denies access to the declared constructor
    //	 *
    //	 *                                   <li>the caller's class loader is not the
    //	 *                                   same as or an ancestor of the class loader
    //	 *                                   for the current class and invocation of
    //	 *                                   {@link SecurityManager#checkPackageAccess
    //	 *                                   s.checkPackageAccess()} denies access to
    //	 *                                   the package of this class
    //	 *
    //	 *                                   </ul>
    //	 * @throws InstantiationException    if the class that declares the underlying
    //	 *                                   constructor represents an abstract class.
    //	 * @throws IllegalAccessException    if the {@code Constructor} object is
    //	 *                                   enforcing Java language access control and
    //	 *                                   the underlying constructor is inaccessible.
    //	 * @throws IllegalArgumentException  if the number of actual and formal
    //	 *                                   parameters differ; if an unwrapping
    //	 *                                   conversion for primitive arguments fails;
    //	 *                                   or if, after possible unwrapping, a
    //	 *                                   parameter value cannot be converted to the
    //	 *                                   corresponding formal parameter type by a
    //	 *                                   method invocation conversion; if this
    //	 *                                   constructor pertains to an enum type.
    //	 * @throws InvocationTargetException if the underlying constructor throws an
    //	 *                                   exception.
    //	 */
    //	public static <T> T newInstance ( Class < ? > clazz , Object... arguments )
    //			throws NoSuchMethodException, SecurityException, InstantiationException,
    // IllegalAccessException,
    //			IllegalArgumentException, InvocationTargetException {
    //		try {
    //			return newInstance ( clazz , false , getTypes ( arguments ) , arguments );
    //		} catch ( NoSuchMethodException ex ) {
    //			return newInstance ( clazz , true , getTypes ( arguments ) , arguments );
    //		}
    //	}

    /**
     * Sets the provided constructor as accessible/inaccessible.
     *
     * @param constructor Constructor to set
     * @param accessible Whether or not the method is accessible
     * @return Object, for chaining
     * @throws SecurityException if the request is denied
     */
    public static <T> Constructor<T> setAccessible(Constructor<T> constructor, boolean accessible)
            throws SecurityException {
        constructor.setAccessible(accessible);
        if (accessible) {
            try {
                Field modifiersField = Constructor.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(constructor, constructor.getModifiers() & ~Modifier.FINAL);
                modifiersField.setAccessible(false);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return constructor;
    }

    /**
     * Sets the provided constructor as accessible.
     *
     * @param constructor Constructor to set
     * @return Object, for chaining
     * @throws SecurityException if the request is denied
     * @see #setAccessible(Constructor, boolean)
     */
    public static <T> Constructor<T> setAccessible(Constructor<T> constructor)
            throws NoSuchFieldException,
                    SecurityException,
                    IllegalArgumentException,
                    IllegalAccessException {
        return setAccessible(constructor, true);
    }

    //	/**
    //	 * Gets an array containing the classes of the provided arguments.
    //	 * <p>
    //	 * @param arguments the objects to get.
    //	 * @return an array containing the types.
    //	 */
    //	private static Class < ? > [] getTypes ( Object... arguments ) {
    //		final Class < ? > [] types = new Class < ? > [ arguments.length ];
    //		for ( int i = 0 ; i < types.length ; i ++ ) {
    //			types [ i ] = arguments [ i ].getClass();
    //		}
    //		return types;
    //	}
}
