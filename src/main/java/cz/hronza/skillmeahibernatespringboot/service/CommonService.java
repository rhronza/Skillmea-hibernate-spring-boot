package cz.hronza.skillmeahibernatespringboot.service;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Service
public class CommonService {
    
    public <S, T> T updateInstanceByAnotherInstanceTheSameClass(@NotNull S sourceInstance, @NotNull T targetInstance, @NotNull List<String> excludedFields) throws InvocationTargetException, IllegalAccessException {
        Field[] fieldsArray = Objects.requireNonNull(sourceInstance).getClass().getDeclaredFields();
        List<Field> fields = Arrays.stream(fieldsArray).sorted(Comparator.comparing(Field::getName)).toList();
        Method[] declaredMethods = Objects.requireNonNull(sourceInstance).getClass().getDeclaredMethods();
        List<Method> methods = Arrays.stream(declaredMethods).toList();
        List<Method> getters = methods.stream().filter(m -> m.getName().startsWith("get")).sorted(Comparator.comparing(Method::getName)).toList();
        List<Method> setters = methods.stream().filter(m -> m.getName().startsWith("set")).sorted(Comparator.comparing(Method::getName)).toList();
        if ((setters.size() == getters.size()) && (setters.size() == fields.size())) {
            for (int i = 0; i < getters.size(); i++) {

                //TODO vypustit transient settery a gettery
                Object value = getters.get(i).invoke(sourceInstance);
                if (value != null && !excludedFields.contains(fields.get(i).getName()))
                    setters.get(i).invoke(targetInstance, value);
            }
        }
        return targetInstance;
    }

}
