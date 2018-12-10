package util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class BeanUtil
{
    public static void copyBeans(Object objFrom, Object objTo, String[] excludeFields)
    {
        if (objFrom != null)
        {
            Class<? extends Object> classFrom = objFrom.getClass();
            Class<? extends Object> classTo = objTo.getClass();
            List<Field> fieldsFrom = getFildsArray(classFrom);
            List<Field> fieldsTo = getFildsArray(classTo);
            for (int i = 0; i < fieldsTo.size(); i++)
            {
                Field fieldTo = fieldsTo.get(i);
                for (int j = 0; j < fieldsFrom.size(); j++)
                {
                    Field fieldFrom = fieldsFrom.get(j);
                    if (fieldFrom.getName().equals(fieldTo.getName()))
                    {
                        boolean isExclude = false;
                        if (excludeFields != null)
                        {
                            for (int k = 0; k < excludeFields.length; k++)
                            {
                                String excludeField = excludeFields[k];
                                if (fieldTo.getName().equals(excludeField))
                                {
                                    isExclude = true;
                                    break;
                                }
                            }
                        }
                        if (isExclude) break;
                        Method getMethod = getMethod(classFrom, fieldFrom.getName(), "get");
                        if (getMethod == null) break;
                        try
                        {
                            Object value = getMethod.invoke(objFrom);
                            if (value == null) break;
                            Method setMethod = getMethod(classTo, fieldFrom.getName(), "set");
                            if (setMethod == null || !Modifier.isPublic(setMethod.getModifiers())) break;
                            
                            if(fieldFrom.getType()==fieldTo.getType()){
                            	 setMethod.invoke(objTo, value);
                            }else{
                            	  Class<?> toClassType = fieldTo.getType();
                                  Class<?> fromClassType = fieldFrom.getType();
                                  if(toClassType == String.class){
                                      setMethod.invoke(objTo, String.valueOf(value)) ;
                                  }else if((toClassType == Integer.class || toClassType == int.class) 
                                          && (fromClassType == Integer.class || fromClassType == int.class)){
                                      setMethod.invoke(objTo, value) ;
                                  }else if((toClassType == Long.class || toClassType == long.class) 
                                          && (fromClassType == Long.class || fromClassType == long.class)){
                                      setMethod.invoke(objTo, value) ;
                                  }else if((toClassType == Double.class || toClassType == double.class) 
                                          && (fromClassType == Double.class || fromClassType == double.class)){
                                      setMethod.invoke(objTo, value) ;
                                  }else if((toClassType == Float.class || toClassType == float.class) 
                                          && (fromClassType == Float.class || fromClassType == float.class)){
                                      setMethod.invoke(objTo, value) ;
                                  }else if((toClassType == Character.class || toClassType == char.class) 
                                          && (fromClassType == Character.class || fromClassType == char.class)){
                                      setMethod.invoke(objTo, value) ;
                                  }else if((toClassType == Boolean.class || toClassType == boolean.class) 
                                          && (fromClassType == Boolean.class || fromClassType == boolean.class)){
                                      setMethod.invoke(objTo, value) ;
                                  }
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.toString();
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void copyBeans(Object objFrom, Object objTo)
    {
        copyBeans(objFrom, objTo, null);
    }

    private static ArrayList<Field> getFildsArray(Class<? extends Object> objClass)
    {
        ArrayList<Field> al = null;
        Field[] fields = objClass.getDeclaredFields();
        al = new ArrayList<Field>();
        for (int i = 0; i < fields.length; i++)
        {
            al.add(fields[i]);
        }
        return al;
    }

    private static Method getMethod(Class<? extends Object> objClass, String fieldName, String prefix)
    {
        Method method = null;
        String methodName = "";
        Method[] methods = objClass.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++)
        {
            methodName = methods[i].getName();
            if (methodName.equals(prefix+upFirstChar(fieldName)))
            {
                method = methods[i];
                break;
            }
        }
        return method;
    }

    private static String upFirstChar(String str)
    {
        String first = (str.substring(0, 1)).toUpperCase();
        String other = str.substring(1);
        return first + other;
    }
    
    public static <T>T copyBean(Class<T> classType,Object objFrom){
        T objTo = null ;
        try {
            objTo = classType.newInstance() ;
            copyBeans(objFrom, objTo);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return objTo ;
    }
}