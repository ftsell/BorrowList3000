package me.finnthorben.thingpeoplelist.lists.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchThingListRequest {
    @Nullable
    String name;
}
