package me.krft.api.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link me.krft.api.domain.ApplicationUserBadge} entity.
 */
@Schema(description = "Association entity between users and badges")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApplicationUserBadgeDTO implements Serializable {

    private Long id;

    /**
     * Date the user obtained the badge
     */
    @NotNull
    @Schema(description = "Date the user obtained the badge", required = true)
    private Instant obtainedDate;

    private ApplicationUserDTO user;

    private BadgeDTO badge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getObtainedDate() {
        return obtainedDate;
    }

    public void setObtainedDate(Instant obtainedDate) {
        this.obtainedDate = obtainedDate;
    }

    public ApplicationUserDTO getUser() {
        return user;
    }

    public void setUser(ApplicationUserDTO user) {
        this.user = user;
    }

    public BadgeDTO getBadge() {
        return badge;
    }

    public void setBadge(BadgeDTO badge) {
        this.badge = badge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUserBadgeDTO)) {
            return false;
        }

        ApplicationUserBadgeDTO applicationUserBadgeDTO = (ApplicationUserBadgeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, applicationUserBadgeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUserBadgeDTO{" +
            "id=" + getId() +
            ", obtainedDate='" + getObtainedDate() + "'" +
            ", user=" + getUser() +
            ", badge=" + getBadge() +
            "}";
    }
}
