package practice.mayank.ecommerce.security.entity;

public enum RoleEnum {
    ADMIN,
    EMPLOYEE,
    USER;


    /* Return role name with prepended ROLE_*/
    public String getRoleNameWithRolePrefix(){
        return "ROLE_" + this.name();
    }
}
