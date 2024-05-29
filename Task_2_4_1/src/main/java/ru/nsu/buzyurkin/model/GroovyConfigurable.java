package ru.nsu.buzyurkin.model;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.GroovyShell;
import groovy.lang.MetaProperty;
import groovy.util.DelegatingScript;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.buzyurkin.Main;


/**
 * The GroovyConfigurable class allows configuration of its properties from a Groovy script file.
 */
public class GroovyConfigurable extends GroovyObjectSupport {

    /**
     * Configures the properties of the current object from the specified Groovy script file.
     *
     * @param file the Groovy script file to configure from.
     * @throws IOException if an I/O error occurs reading the file.
     * @throws NoSuchFieldException if a field is not found during reflection.
     * @throws InvocationTargetException if an exception occurs during method invocation.
     * @throws InstantiationException if an error occurs during instantiation.
     * @throws IllegalAccessException if an error occurs accessing a method or field.
     * @throws NoSuchMethodException if a method is not found during reflection.
     */
    public void configureFromFile(File file) throws
            IOException, NoSuchFieldException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchMethodException {

        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Main.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = (DelegatingScript) sh.parse(file);
        script.setDelegate(this);
        script.run();
        configureProperties();
    }

    private void configureProperties() throws
            NoSuchFieldException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (MetaProperty property : getMetaClass().getProperties()) {
            Object value = getProperty(property.getName());
            if (Collection.class.isAssignableFrom(property.getType()) && value instanceof Collection) {
                setProperty(property.getName(), configureCollection(property, value));
            }
        }
    }

    private Object configureCollection(MetaProperty prop, Object value) throws
            NoSuchFieldException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        ParameterizedType collectionType = (ParameterizedType)
                getClass().getDeclaredField(prop.getName()).getGenericType();

        Class itemClass = (Class) collectionType.getActualTypeArguments()[0];
        if (GroovyConfigurable.class.isAssignableFrom(itemClass)) {
            Collection collection = (Collection) value;
            Collection newValue = collection.getClass().getConstructor().newInstance();
            for (Object item : collection) {
                newValue.add(configureCollectionItem(itemClass, item));
            }
            return newValue;
        }
        return value;
    }

    private Object configureCollectionItem(Class itemClass, Object value) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException, NoSuchFieldException {

        if (value instanceof Closure) {
            Object newValue = itemClass.getConstructor().newInstance();
            Closure c = (Closure) value;
            c.setDelegate(newValue);
            c.setResolveStrategy(Closure.DELEGATE_FIRST);
            c.setDelegate(newValue);
            c.call();
            ((GroovyConfigurable) newValue).configureProperties();
            return newValue;
        }
        return value;
    }
}
