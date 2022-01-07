package com.tunbor.beye.utility;

import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
public interface AppConstants {
    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final UUID SYSTEM_ID = UUID.nameUUIDFromBytes(new byte[0]);      //d41d8cd9-8f00-3204-a980-0998ecf8427e

    public static final int VERSION = 1;

    public static final String VERSION_URL = "/api/v" + AppConstants.VERSION;
}
