package Security.LogIn.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static Security.LogIn.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),
    STUDENT(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),

    ADMINTRAINEE(Sets.newHashSet(COURSE_READ,STUDENT_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
