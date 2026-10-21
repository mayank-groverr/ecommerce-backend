package practice.mayank.ecommerce.dto.user;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import practice.mayank.ecommerce.security.entity.PermissionEnum;
import practice.mayank.ecommerce.validation.annotations.Email;

import java.util.Collections;
import java.util.Set;

public record UpdateEmployeePermissionRequest (


        @Email
        String email,

        @Size(min = 1, message = "At least one permission is required")
        Set<@NotNull(message = "null is not allowed inside list") PermissionEnum> grant,

        @Size(min = 1, message = "At least one permission is required")
        Set<@NotNull(message = "null is not allowed inside list") PermissionEnum> revoke
) {

    @JsonIgnore
    @AssertTrue(message = "Provide at least one of grant or revoke")
    public boolean isBothSetAreEmpty() {
        return grant != null || revoke != null;
    }

    @JsonIgnore
    @AssertTrue(message = "Same permission cannot be in both grant and revoke")
    public boolean isGrantAndRevokeDisjoint() {
        if (grant == null || revoke == null) return true;
        return Collections.disjoint(grant, revoke);
    }

}
