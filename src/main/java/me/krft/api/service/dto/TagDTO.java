package me.krft.api.service.dto;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
public class TagDTO extends AbstractDTO implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("label")
    private String label;
}
