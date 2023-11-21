package me.krft.api.service.dto;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class ShowcaseDTO extends AbstractDTO implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("imageId")
    private UUID imageId;
}
