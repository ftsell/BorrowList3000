package me.finnthorben.thingpeoplelist.security;

import java.io.Serializable;

public record SessionInfo(
        String ipAddress,
        String userAgent
        ) implements Serializable {
    /**
     * A session index that contains the sessions SessionInfo object
     */
    public static String SESSION_INFO_INDEX_NAME = "info";
}
