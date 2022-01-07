package com.tunbor.beye.utility;

import com.tunbor.beye.utility.exception.AppRuntimeException;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
public class ServiceUtils {

    public static AppRuntimeException wrongIdException(String modelName, UUID id) {
        return new AppRuntimeException(String.format("%s with Id '%s' not found", modelName, id));
    }


    public static void throwWrongIdException(String modelName, UUID id) {
        throw wrongIdException(modelName, id);
    }

    public static AppRuntimeException noAccessIdException(String modelName, UUID id) {
        return new AppRuntimeException(String.format("You do not have access to %s with id '%s'", modelName, id));
    }


    public static void throwNoAccessIdException(String modelName, UUID id) {
        throw noAccessIdException(modelName, id);
    }


    public static AppRuntimeException duplicateNameException(String modelName, String name) {
        return new AppRuntimeException(String.format("%s with name '%s' already exists", modelName, name));
    }


    public static void throwDuplicateNameException(String modelName, String name) {
        throw duplicateNameException(modelName, name);
    }


    public static AppRuntimeException duplicateEmailException(String modelName, String email) {
        return new AppRuntimeException(String.format("%s with email '%s' already exists", modelName, email));
    }


    public static void throwDuplicateEmailException(String modelName, String email) {
        throw duplicateEmailException(modelName, email);
    }


    public static AppRuntimeException fieldNotExistException(String field, Collection<String> validFields) {
        return new AppRuntimeException(String.format("%s is not a valid field. Valid fields: %s",
                field, String.join(", ", validFields)));
    }

    public static void throwFieldNotExistException(String field, Collection<String> validFields) {
        throw fieldNotExistException(field, validFields);
    }
}
