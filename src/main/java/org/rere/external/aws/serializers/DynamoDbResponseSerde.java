package org.rere.external.aws.serializers;

import org.rere.core.serde.PrimitiveSerde;
import org.rere.core.serde.ReReSerde;
import org.rere.core.serde.exceptions.SerializationException;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbResponse;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DynamoDbResponseSerde implements ReReSerde {
    private static final PrimitiveSerde primitiveSerde = new PrimitiveSerde();

    @Override
    public boolean accept(Class<?> clazz) {
        return DynamoDbResponse.class.isAssignableFrom(clazz);
    }

    @Override
    public String serialize(Object o) throws SerializationException {
        DynamoDbResponse dynamoDbResponse = (DynamoDbResponse) o;
        Class<?> clazz = dynamoDbResponse.getClass();
        Map<String, Object> m = new HashMap<>();
        try {
            List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                    .filter(f -> !Modifier.isStatic(f.getModifiers()))
                    .collect(Collectors.toList());
            for (Field field : fields) {
                field.setAccessible(true);
                Object ob = field.get(dynamoDbResponse);
                if (ob == null) {
                    continue;
                }
                m.put(field.getName(), ob);
            }
        } catch (Exception e) {
            return null;
        }
        DataHolder dataHolder = new DataHolder(clazz.getName(), m);

        return primitiveSerde.serialize(dataHolder);
    }

    @Override
    public DynamoDbResponse deserialize(String s) {
        DataHolder dataHolder = (DataHolder) primitiveSerde.deserialize(s);
        Class<?> clazz;
        try {
            clazz = Class.forName(dataHolder.className);
            Class<?> builderClazz = Arrays.stream(clazz.getDeclaredClasses())
                    .filter(c -> c.getName().endsWith("Builder"))
                    .findFirst()
                    .get();
            Object builder = clazz.getMethod("builder").invoke(null);
            for (String fieldName : dataHolder.m.keySet()) {
                Object o = dataHolder.m.get(fieldName);
                if (o == null) {
                    continue;
                }
                builder = findMethod(builderClazz, fieldName, o.getClass()).invoke(builder, o);
            }
            return (DynamoDbResponse) builderClazz.getMethod("build").invoke(builder);
        } catch (Exception e) {
            return null;
        }
    }

    Method findMethod(Class<?> clazz, String methodName, Class<?> argType) {
        for (Method method : clazz.getMethods()) {
            if (method.getName()
                    .equals(methodName) && method.getParameterTypes().length == 1 && method.getParameterTypes()[0].isAssignableFrom(
                    argType)) {
                return method;
            }
        }
        return null;
    }

    public static class DataHolder implements Serializable {
        private final Map<String, Object> m;
        private final String className;

        public DataHolder(String className, Map<String, Object> m) {
            this.m = m;
            this.className = className;
        }
    }
}
