package me.Abhigya.core.util.reflection.general;

import me.Abhigya.core.util.reflection.DataType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class for reflecting methods
 */
public class MethodReflection {

    /**
     * Gets the method with the provided name from the provided class.
     * <p>
     *
     * @param clazz           the class that holds the method
     * @param name            the name of the method
     * @param declared        whether the desired method is declared or not
     * @param parameter_types parameterTypes the list of parameters
     * @return the {@code Method} object that matches the specified {@code name} and
     * {@code parameterTypes}
     * @throws NoSuchMethodException if a matching method is not found or if the
     *                               name is "&lt;init&gt;"or "&lt;clinit&gt;".
     * @throws SecurityException     If a security manager, <i>s</i>, is present and
     *                               any of the following conditions is met:
     *
     *                               <ul>
     *
     *                               <li>the caller's class loader is not the same
     *                               as the class loader of this class and
     *                               invocation of
     *                               {@link SecurityManager#checkPermission
     *                               s.checkPermission} method with
     *                               {@code RuntimePermission("accessDeclaredMembers")}
     *                               denies access to the declared method
     *
     *                               <li>the caller's class loader is not the same
     *                               as or an ancestor of the class loader for the
     *                               current class and invocation of
     *                               {@link SecurityManager#checkPackageAccess
     *                               s.checkPackageAccess()} denies access to the
     *                               package of this class
     *
     *                               </ul>
     */
    public static Method get(Class<?> clazz, String name, boolean declared, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        return declared ? clazz.getDeclaredMethod(name, parameter_types) : clazz.getMethod(name, parameter_types);
    }

    /**
     * Gets the method with the provided name (<strong>No matter if declared or
     * not</strong>) from the provided class.
     * <p>
     *
     * @param clazz           the class that holds the method
     * @param name            the name of the method
     * @param parameter_types parameterTypes the list of parameters
     * @return the {@code Method} object that matches the specified {@code name} and
     * {@code parameterTypes}
     * @throws NoSuchMethodException if a matching method is not found or if the
     *                               name is "&lt;init&gt;"or "&lt;clinit&gt;".
     * @throws SecurityException     If a security manager, <i>s</i>, is present and
     *                               any of the following conditions is met:
     *
     *                               <ul>
     *
     *                               <li>the caller's class loader is not the same
     *                               as the class loader of this class and
     *                               invocation of
     *                               {@link SecurityManager#checkPermission
     *                               s.checkPermission} method with
     *                               {@code RuntimePermission("accessDeclaredMembers")}
     *                               denies access to the declared method
     *
     *                               <li>the caller's class loader is not the same
     *                               as or an ancestor of the class loader for the
     *                               current class and invocation of
     *                               {@link SecurityManager#checkPackageAccess
     *                               s.checkPackageAccess()} denies access to the
     *                               package of this class
     *
     *                               </ul>
     * @see #get(Class, String, boolean, Class...)
     */
    public static Method get(Class<?> clazz, String name, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        try {
            return get(clazz, name, false, parameter_types);
        } catch (NoSuchMethodException ex) {
            return get(clazz, name, true, parameter_types);
        }
    }

    /**
     * Gets the method with the provided name from the provided class, and sets it
     * accessible.
     * <p>
     *
     * @param clazz           the class that holds the method
     * @param name            the name of the method
     * @param declared        whether the desired method is declared or not
     * @param parameter_types parameterTypes the list of parameters
     * @return the {@code Method} object that matches the specified {@code name} and
     * {@code parameterTypes}
     * @throws NoSuchMethodException if a matching method is not found or if the
     *                               name is "&lt;init&gt;"or "&lt;clinit&gt;".
     * @throws SecurityException     If a security manager, <i>s</i>, is present and
     *                               any of the following conditions is met:
     *
     *                               <ul>
     *
     *                               <li>the caller's class loader is not the same
     *                               as the class loader of this class and
     *                               invocation of
     *                               {@link SecurityManager#checkPermission
     *                               s.checkPermission} method with
     *                               {@code RuntimePermission("accessDeclaredMembers")}
     *                               denies access to the declared method
     *
     *                               <li>the caller's class loader is not the same
     *                               as or an ancestor of the class loader for the
     *                               current class and invocation of
     *                               {@link SecurityManager#checkPackageAccess
     *                               s.checkPackageAccess()} denies access to the
     *                               package of this class
     *
     *                               </ul>
     */
    public static Method getAccessible(Class<?> clazz, String name, boolean declared, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        final Method method = get(clazz, name, declared, parameter_types);
        method.setAccessible(true);
        return method;
    }

    /**
     * Gets the method with the provided name (<strong>No matter if declared or
     * not</strong>) from the provided class, and sets it accessible.
     * <p>
     *
     * @param clazz           the class that holds the method
     * @param name            the name of the method
     * @param parameter_types parameterTypes the list of parameters
     * @return the {@code Method} object that matches the specified {@code name} and
     * {@code parameterTypes}
     * @throws NoSuchMethodException if a matching method is not found or if the
     *                               name is "&lt;init&gt;"or "&lt;clinit&gt;".
     * @throws SecurityException     If a security manager, <i>s</i>, is present and
     *                               any of the following conditions is met:
     *
     *                               <ul>
     *
     *                               <li>the caller's class loader is not the same
     *                               as the class loader of this class and
     *                               invocation of
     *                               {@link SecurityManager#checkPermission
     *                               s.checkPermission} method with
     *                               {@code RuntimePermission("accessDeclaredMembers")}
     *                               denies access to the declared method
     *
     *                               <li>the caller's class loader is not the same
     *                               as or an ancestor of the class loader for the
     *                               current class and invocation of
     *                               {@link SecurityManager#checkPackageAccess
     *                               s.checkPackageAccess()} denies access to the
     *                               package of this class
     *
     *                               </ul>
     * @see #getAccessible(Class, String, boolean, Class...)
     */
    public static Method getAccessible(Class<?> clazz, String name, Class<?>... parameter_types)
            throws NoSuchMethodException, SecurityException {
        final Method method = get(clazz, name, parameter_types);
        method.setAccessible(true);
        return method;
    }

    /**
     * Invokes the underlying method represented by the provided {@code Method}, on
     * the specified object with the specified parameters. Individual parameters are
     * automatically unwrapped to match primitive formal parameters, and both
     * primitive and reference parameters are subject to method invocation
     * conversions as necessary.
     * <p>
     *
     * @param method    the method to invoke
     * @param object    the object the underlying method is invoked from
     * @param arguments the arguments used for the method call
     * @return the result of dispatching the method represented by this object on
     * {@code object} with parameters {@code arguments}
     * @throws IllegalAccessException    if this {@code Method} object is
     *                                   enforcing Java language access control
     *                                   and the underlying method is
     *                                   inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and
     *                                   the specified object argument is not an
     *                                   instance of the class or interface
     *                                   declaring the underlying method (or of a
     *                                   subclass or implementor thereof); if the
     *                                   number of actual and formal parameters
     *                                   differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after
     *                                   possible unwrapping, a parameter value
     *                                   cannot be converted to the corresponding
     *                                   formal parameter type by a method
     *                                   invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an
     *                                   exception.
     */
    public static Object invoke(Method method, Object object, Object... arguments)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(object, arguments);
    }

    /**
     * Invokes the underlying method represented by the provided {@code Method}, on
     * the specified object with the specified parameters. Individual parameters are
     * automatically unwrapped to match primitive formal parameters, and both
     * primitive and reference parameters are subject to method invocation
     * conversions as necessary.
     * <p>
     * <strong>Note that the method is set as accessible before invoking</strong>.
     * <p>
     *
     * @param method    the method to invoke
     * @param object    the object the underlying method is invoked from
     * @param arguments the arguments used for the method call
     * @return the result of dispatching the method represented by this object on
     * {@code object} with parameters {@code arguments}
     * @throws IllegalAccessException    if this {@code Method} object is
     *                                   enforcing Java language access control
     *                                   and the underlying method is
     *                                   inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and
     *                                   the specified object argument is not an
     *                                   instance of the class or interface
     *                                   declaring the underlying method (or of a
     *                                   subclass or implementor thereof); if the
     *                                   number of actual and formal parameters
     *                                   differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after
     *                                   possible unwrapping, a parameter value
     *                                   cannot be converted to the corresponding
     *                                   formal parameter type by a method
     *                                   invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an
     *                                   exception.
     */
    public static Object invokeAccessible(Method method, Object object, Object... arguments)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final boolean b0 = method.isAccessible();
        try {
            return method.invoke(object, arguments);
        } catch (Throwable ex) {
            throw ex;
        } finally {
            method.setAccessible(b0);
        }
    }

    /**
     * Invokes the underlying method represented by the provided {@code name}, on
     * the specified object with the specified parameters. Individual parameters are
     * automatically unwrapped to match primitive formal parameters, and both
     * primitive and reference parameters are subject to method invocation
     * conversions as necessary.
     * <p>
     *
     * @param name            the name of the method to invoke
     * @param object          the object the underlying method is invoked from
     * @param parameter_types parameterTypes the list of parameters
     * @param arguments       the arguments used for the method call
     * @return the result of dispatching the method represented by this object on
     * {@code object} with parameters {@code arguments}
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException    if this {@code Method} object is
     *                                   enforcing Java language access control
     *                                   and the underlying method is
     *                                   inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and
     *                                   the specified object argument is not an
     *                                   instance of the class or interface
     *                                   declaring the underlying method (or of a
     *                                   subclass or implementor thereof); if the
     *                                   number of actual and formal parameters
     *                                   differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after
     *                                   possible unwrapping, a parameter value
     *                                   cannot be converted to the corresponding
     *                                   formal parameter type by a method
     *                                   invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an
     *                                   exception.
     */
    public static Object invoke(Object object, String name, Class<?>[] parameter_types, Object... arguments)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return invoke(get(object.getClass(), name, parameter_types), object, arguments);
    }

    /**
     * Invokes the underlying method represented by the provided {@code name}, on
     * the specified object with the specified parameters. Individual parameters are
     * automatically unwrapped to match primitive formal parameters, and both
     * primitive and reference parameters are subject to method invocation
     * conversions as necessary.
     * <p>
     * <strong>Note that the method is set as accessible before invoking</strong>.
     * <p>
     *
     * @param name            the name of the method to invoke
     * @param object          the object the underlying method is invoked from
     * @param parameter_types parameterTypes the list of parameters
     * @param arguments       the arguments used for the method call
     * @return the result of dispatching the method represented by this object on
     * {@code object} with parameters {@code arguments}
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException    if this {@code Method} object is
     *                                   enforcing Java language access control
     *                                   and the underlying method is
     *                                   inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and
     *                                   the specified object argument is not an
     *                                   instance of the class or interface
     *                                   declaring the underlying method (or of a
     *                                   subclass or implementor thereof); if the
     *                                   number of actual and formal parameters
     *                                   differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after
     *                                   possible unwrapping, a parameter value
     *                                   cannot be converted to the corresponding
     *                                   formal parameter type by a method
     *                                   invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an
     *                                   exception.
     */
    public static Object invokeAccessible(Object object, String name, Class<?>[] parameter_types, Object... arguments)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return invokeAccessible(get(object.getClass(), name, parameter_types), object, arguments);
    }

    /**
     * Invokes the underlying method represented by the provided {@code name}, on
     * the specified object with the specified parameters. Individual parameters are
     * automatically unwrapped to match primitive formal parameters, and both
     * primitive and reference parameters are subject to method invocation
     * conversions as necessary.
     * <p>
     *
     * @param name      the name of the method to invoke
     * @param object    the object the underlying method is invoked from
     * @param arguments the arguments used for the method call
     * @return the result of dispatching the method represented by this object on
     * {@code object} with parameters {@code arguments}
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException    if this {@code Method} object is
     *                                   enforcing Java language access control
     *                                   and the underlying method is
     *                                   inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and
     *                                   the specified object argument is not an
     *                                   instance of the class or interface
     *                                   declaring the underlying method (or of a
     *                                   subclass or implementor thereof); if the
     *                                   number of actual and formal parameters
     *                                   differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after
     *                                   possible unwrapping, a parameter value
     *                                   cannot be converted to the corresponding
     *                                   formal parameter type by a method
     *                                   invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an
     *                                   exception.
     */
    public static Object invoke(Object object, String name, Object... arguments)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return invoke(get(object.getClass(), name, DataType.getPrimitive(arguments)), object, arguments);
    }

    /**
     * Invokes the underlying method represented by the provided {@code name}, on
     * the specified object with the specified parameters. Individual parameters are
     * automatically unwrapped to match primitive formal parameters, and both
     * primitive and reference parameters are subject to method invocation
     * conversions as necessary.
     * <p>
     * <strong>Note that the method is set as accessible before invoking</strong>.
     * <p>
     *
     * @param name      the name of the method to invoke.
     * @param object    the object the underlying method is invoked from
     * @param arguments the arguments used for the method call
     * @return the result of dispatching the method represented by this object on
     * {@code object} with parameters {@code arguments}
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException    if this {@code Method} object is
     *                                   enforcing Java language access control
     *                                   and the underlying method is
     *                                   inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and
     *                                   the specified object argument is not an
     *                                   instance of the class or interface
     *                                   declaring the underlying method (or of a
     *                                   subclass or implementor thereof); if the
     *                                   number of actual and formal parameters
     *                                   differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after
     *                                   possible unwrapping, a parameter value
     *                                   cannot be converted to the corresponding
     *                                   formal parameter type by a method
     *                                   invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an
     *                                   exception.
     */
    public static Object invokeAccessible(Object object, String name, Object... arguments)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return invokeAccessible(get(object.getClass(), name, DataType.getPrimitive(arguments)), object, arguments);
    }

    /**
     * Sets the provided method as accessible/inaccessible.
     * <p>
     *
     * @param method     the method to set
     * @param accessible whether or not the method is accessible
     * @return the same Object, for chaining
     * @throws SecurityException if the request is denied
     */
    public static Method setAccessible(Method method, boolean accessible) throws SecurityException {
        method.setAccessible(accessible);
        if (accessible) {
            try {
                Field modifiersField = Method.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(method, method.getModifiers() & ~Modifier.FINAL);
                modifiersField.setAccessible(false);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return method;
    }

    /**
     * Sets the provided method as accessible.
     * <p>
     *
     * @param method the method to set
     * @return the same Object, for chaining
     * @throws SecurityException if the request is denied
     * @see #setAccessible(Method, boolean)
     */
    public static Method setAccessible(Method method)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        return setAccessible(method, true);
    }
}
