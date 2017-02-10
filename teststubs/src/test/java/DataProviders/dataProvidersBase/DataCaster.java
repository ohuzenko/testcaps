package DataProviders.dataProvidersBase;

import java.util.Date;

/**
 * Created by sunny_IT on 2/1/2017.
 */
public class DataCaster {
    public  static Object castDataType(Class<?> paramType, String value) {

        String typeName = paramType.getSimpleName();
        try {
            if (typeName.equals("byte")) return Byte.parseByte(value);
            if (typeName.equals("short")) return Short.parseShort(value);
            if (typeName.equals("int")) return Integer.parseInt(value);
            if (typeName.equals("float")) return Float.parseFloat(value);
            if (typeName.equals("Date")) return new Date(value);
            if (typeName.equals("long")) return Long.parseLong(value);
            if (typeName.equals("double")) return Double.parseDouble(value);
            if (typeName.equals("boolean")) return Boolean.parseBoolean(value);
            if (typeName.equals("char")) return value.charAt(0);
        }catch(Exception e){
            //TODO exception processing
            return null;

        }

        return value;
    }
    public  static Object castDataType(Class<?> paramType, Object value) {

        String tmp = value.toString();

        return castDataType(paramType, tmp);
    }

}
