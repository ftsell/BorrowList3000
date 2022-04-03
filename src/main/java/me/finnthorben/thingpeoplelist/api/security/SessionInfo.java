package me.finnthorben.thingpeoplelist.api.security;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

public record SessionInfo(
        @NotBlank String ipAddress,
        @NotNull LocalDateTime loginDate
        ) implements Serializable {
    /**
     * A session index that contains the sessions SessionInfo object
     */
    public static String SESSION_INFO_INDEX_NAME = "info";
}
