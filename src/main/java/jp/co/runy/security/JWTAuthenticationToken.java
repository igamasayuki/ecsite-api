package jp.co.runy.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import jp.co.runy.domain.LoginUser;

/**
 * JWT認証トークン.
 * 
 * @author igamasayuki
 */
public class JWTAuthenticationToken extends AbstractAuthenticationToken {
	
    private static final long serialVersionUID = -7149021886414850692L;
    
	// @AuthenticationPrincipalで参照されるPrincipal
    private LoginUser principal;

    public JWTAuthenticationToken(Collection<? extends GrantedAuthority> authorities, LoginUser principal) {
        // ここで渡したAuthority(文字列のRole)が、hasRole(),hasAuthority()のチェック対象となる
        super(authorities);
        this.principal = principal;
        this.setAuthenticated(true);
    }

    // Authenticationインタフェースで用意されているgetPrincipal()を@Override
    @Override
    public LoginUser getPrincipal() {
        return principal;
    }

    // Authenticationインタフェースで用意されているgetCredentials()を@Override
    @Override
    public Object getCredentials() {
        return null;
    }
}
