package me.krft.api.service.dto;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuperBuilder
@EqualsAndHashCode
@ToString
@Getter
public class AbstractDTO implements Serializable {
    @SerializedName("messages")
    private Map<AbstractDTOSeverity, List<String>> messages;
}
