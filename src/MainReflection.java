import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume test = new Resume("test");
        Method method = test.getClass().getMethod("toString");
        System.out.println(method.invoke(test));
    }
}