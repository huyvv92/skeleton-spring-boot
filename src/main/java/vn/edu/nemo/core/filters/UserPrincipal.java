package vn.edu.nemo.core.filters;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipal extends org.springframework.security.core.userdetails.User {

    //add new property for principal
    private String companyId;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities, String companyId) {
        super(username, password, authorities);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
