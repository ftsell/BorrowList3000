package me.finnthorben.thingpeoplelist.lists.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class PatchThingListRequest {
    @Nullable
    String name;
}
