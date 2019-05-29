package com.ley.competition;

import com.google.common.collect.Lists;
import com.ley.competition.bean.SpecialStudent;
import com.ley.competition.bean.Student;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectionTest {

    @Test
    public void testReflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(getAllFieldsVersion1(SpecialStudent.class));
        System.out.println(getAllFieldsVersion2(Student.class));
        System.out.println(getAllClasses(Student.class));

        List<Field> fields = getAllFieldsVersion1(SpecialStudent.class);
        SpecialStudent student = new SpecialStudent();
        for (Field field : fields) {
            System.out.println(field.getType());
            Method method = student.getClass().getMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
            if (field.getName().equalsIgnoreCase("age")) {
                method.invoke(student, 20);
            }else{
                method.invoke(student,"我的世界");
            }
        }
        System.out.println(student);
    }


    /**
     * 获取所有的类属性
     **/
    private static List<Field> getAllFieldsVersion1(Class<?> clazz) {
        List<Field> fieldList = Lists.newArrayListWithCapacity(16);

        if (clazz != Object.class) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }

        boolean endFlag = true;
        Class<?> tmp = clazz;
        while (endFlag && tmp != Object.class) {
            Class<?> superClass = tmp.getSuperclass();
            if (superClass != null) {
                fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
                tmp = superClass;
            }
        }
        return fieldList;
    }


    private static List<Field> getAllFieldsVersion2(Class<?> clazz) {
        List<Field> fieldList = Lists.newArrayListWithCapacity(16);
        List<Class<?>> classes = getAllClasses(clazz);
        if (!CollectionUtils.isEmpty(classes)) {
            for (Class<?> tmp : classes) {
                fieldList.addAll(Arrays.asList(tmp.getDeclaredFields()));
            }
        }
        return fieldList;
    }


    private static List<Class<?>> getAllClasses(Class<?> clazz) {
        LinkedList<Class<?>> classList = new LinkedList<>();
        Class<?> tmp = clazz;
        while (tmp != null && tmp != Object.class) {
            classList.addLast(tmp);
            tmp = tmp.getSuperclass();
        }
        return classList;
    }

}
