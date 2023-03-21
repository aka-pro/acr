import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class ActiveDirectoryLookup {
    public static void main(String[] args) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        String adminName = "username@domain.com";
        String adminPassword = "password";
        String ldapURL = "ldap://domain.com:389";
        String searchBase = "DC=domain,DC=com";
        String searchFilter = "(&(objectClass=user)(mail=*))";
        String returnedAtts[] = { "sn", "givenName", "mail" };
        String searchBy = "email";
        String searchValue = "user@domain.com";
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String searchFilter = "(&(objectClass=user)(" + searchBy + "=" + searchValue + "))";
            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                Attributes attrs = sr.getAttributes();
                if (attrs != null) {
                    for (String returnedAttr : returnedAtts) {
                        Attribute attribute = attrs.get(returnedAttr);
                        if (attribute != null) {
                            String attributeVal = (String) attribute.get();
                            System.out.println(returnedAttr + ": " + attributeVal);
                        }
                    }
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
