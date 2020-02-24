package io.peacemakr.crypto.tools;

import io.peacemakr.annotations.Encrypted;
import io.peacemakr.crypto.ICrypto;
import io.peacemakr.crypto.exception.PeacemakrException;

import java.lang.reflect.Field;

public class Encryptor<T> {
    private Class<T> klass;
    private ICrypto peacemakrCtx;

    public Encryptor(Class<T> klass, ICrypto ctx) throws PeacemakrException {
        this.klass = klass;
        this.peacemakrCtx = ctx;

        this.peacemakrCtx.register();
    }

    private boolean invalidFieldType(Class<?> type) {
        return !type.isAssignableFrom(byte[].class) && !type.isAssignableFrom(String.class);
    }

    public void encrypt(T entity) throws IllegalAccessException, PeacemakrException {
        for (Field field : klass.getFields()) {
            // If field is annotated with @Encrypted
            if (field.isAnnotationPresent(Encrypted.class)) {

                // Make sure the field type is byte[] or String
                if (invalidFieldType(field.getType())) {
                    throw new PeacemakrException("Field must be byte[] or String, was neither");
                }

                // Do the encrypt and write it back
                if (field.getType().isAssignableFrom(byte[].class)) {
                    byte[] fieldData = (byte[]) field.get(entity);
                    field.set(entity, peacemakrCtx.encrypt(fieldData));
                } else if (field.getType().isAssignableFrom(String.class)) {
                    String fieldData = (String) field.get(entity);
                    byte[] encrypted = peacemakrCtx.encrypt(fieldData.getBytes());
                    field.set(entity, new String(encrypted));
                }
            }
        }
    }

    public void decrypt(T entity) throws IllegalAccessException, PeacemakrException {
        for (Field field : klass.getFields()) {
            // If field is annotated with @Encrypted
            if (field.isAnnotationPresent(Encrypted.class)) {

                // Make sure the field type is byte[] or String
                if (invalidFieldType(field.getType())) {
                    throw new PeacemakrException("Field must be byte[] or String, was neither");
                }

                // Do the decrypt and write it back
                if (field.getType().isAssignableFrom(byte[].class)) {
                    byte[] fieldData = (byte[]) field.get(entity);
                    field.set(entity, peacemakrCtx.decrypt(fieldData));
                } else if (field.getType().isAssignableFrom(String.class)) {
                    String fieldData = (String) field.get(entity);
                    byte[] decrypted = peacemakrCtx.decrypt(fieldData.getBytes());
                    field.set(entity, new String(decrypted));
                }
            }
        }
    }
}
