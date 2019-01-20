package realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jesse
 * @date 2019/1/20 12:18
 * 自定义Realm
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String,String> userMap = new HashMap<String, String>(16);
    Set<String> roles = new HashSet<String>();
    Set<String> permissions = new HashSet<String>();

    {
        userMap.put("jesse", "8c4fb7bf681156b52fea93442c7dffc9");

        roles.add("admin");
        roles.add("user");

        permissions.add("user:delete");
        permissions.add("user:update");

        // 设置Realm名
        super.setName("customRealm");
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        //获取角色
        Set<String> roles = getRolesByUserName(username);
        //获取权限
        Set<String> permissions = getPermissionsByUserName(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1、从主体传过来的认证信息中获得用户名
        String username = (String)token.getPrincipal();

        //2、通过用户名到数据库中获取凭证（密码）
        String password =getPasswordByUserName(username);
        if(password == null){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("jesse",password,"customRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));

        return simpleAuthenticationInfo;
    }


    /**
     * 模拟数据库查询权限数据
     *
     * @return
     */
    private Set<String> getPermissionsByUserName(String username) {
        return permissions;
    }

    /**
     * 模拟数据库查询角色数据
     *
     * @param username
     * @return
     */
    private Set<String> getRolesByUserName(String username) {
        return roles;
    }


    /**
     * 模拟数据库查询凭证（密码）
     *
     * @param username
     * @return
     */
    private String getPasswordByUserName(String username) {
        return userMap.get(username);
    }


}
