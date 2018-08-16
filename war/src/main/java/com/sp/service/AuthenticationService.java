package com.sp.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.dao.ActiveDirectoryDao;
import com.sp.model.ActiveDirectoryDetails;
import com.sp.model.Employee;
import com.sp.utils.LDAPConstants;

/**
 * AuthenticationService.
 *
 */
@Service
public class AuthenticationService {

    @Autowired
    private ActiveDirectoryDao activeDirectoryDao;

    private final Logger logger = Logger.getLogger(AuthenticationService.class.getName());
    private Long ldapId = 1L;

    /**
     * getConnectionContext.
     * @param activeDirectoryDetails activeDirectoryDetails
     * @return DirContext authContext
     * @throws Exception Exception
     */
     private DirContext getConnectionContext(ActiveDirectoryDetails activeDirectoryDetails) throws Exception {
        DirContext authContext = null;
        HashMap<String, String> authEnv = new HashMap<String, String>();
        String ldapURL = "ldaps://" + activeDirectoryDetails.getServerName() + ":" + activeDirectoryDetails.getPort();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        authEnv.put(Context.PROVIDER_URL, ldapURL);
        authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
        authEnv.put(Context.SECURITY_PRINCIPAL, activeDirectoryDetails.getSecurityPrincipal());
        authEnv.put(Context.SECURITY_CREDENTIALS, activeDirectoryDetails.getSecurityCredentials());
        authContext = new InitialDirContext(new Hashtable<String, String>(authEnv));

        return authContext;
    }

    /**
     * authenticateUser.
     *
     * @param activeDirectoryDetails activeDirectoryDetails
     * @return DirContext authContext
     * @throws Exception Exception
     */
    public DirContext authenticateUser(ActiveDirectoryDetails activeDirectoryDetails) throws Exception {
        DirContext authContext = null;
        if (activeDirectoryDetails.getSecurityPrincipal().trim().length() != 0
                 && activeDirectoryDetails.getSecurityCredentials().trim().length() != 0) {
            authContext = getConnectionContext(activeDirectoryDetails);
        } else {
            authContext = null;
        }
        return authContext;
    }

    /**
     * searchUser.
     *
     * @param email email
     * @param password password
     * @return Boolean userExists
     * @throws Exception Exception
     */
    public Employee searchUser(String email, String password) throws Exception {
        DirContext authContext = null;
        String userPrincipalName = "";
        String lockoutTime = null;
        String name = null;
		String title = null;
        String response = null;
        Employee employee = new Employee();
        ActiveDirectoryDetails activeDirectoryDetails = activeDirectoryDao.getAciveDirectoryDetails(ldapId);

        try {
            authContext = authenticateUser(activeDirectoryDetails);

            NamingEnumeration<SearchResult> result = getSearchResults(email, activeDirectoryDetails, authContext);
            // Loop through the search results
            while (result.hasMoreElements()) {
                SearchResult searchResult = (SearchResult) result.next();
                Attributes attrs = searchResult.getAttributes();
                userPrincipalName = attrs.get("userPrincipalName").toString().split("userPrincipalName:")[1].trim();
                lockoutTime = attrs.get("lockoutTime").toString().split("lockoutTime:")[1].trim();
                name = attrs.get("name").toString().split("name:")[1].trim();
                title = attrs.get("title").toString().split("title:")[1].trim();
            }
            if (userPrincipalName.length() >= 1) {
                if (lockoutTime != null && lockoutTime.equals("0")) {
                    // Checking user authentication
                    activeDirectoryDetails.setSecurityPrincipal(userPrincipalName);
                    activeDirectoryDetails.setSecurityCredentials(password);

                    if (authenticateUser(activeDirectoryDetails) != null) {
                        response = LDAPConstants.VALID_USER;
                        employee.setId(220);
                        employee.setName(name);
                        employee.setDesignation(title);
                        employee.setEmail(userPrincipalName);
                        employee.setValid(true);
                        employee.setEmail(email);
                    } else {
                        response = LDAPConstants.INVALID_USER;
                    }
                } else {
                    response = LDAPConstants.LOCKED_USER;
                }
            } else {
                response = LDAPConstants.INVALID_USER;
            }
        } catch (AuthenticationException authEx) {
            response = LDAPConstants.INVALID_CREDENTIALS;
        } catch (NamingException namEx) {
            logger.log(Level.SEVERE, namEx.getMessage());
            throw new Exception(namEx.getMessage());
        } finally {
            try {
                if (authContext != null) {
                    authContext.close();
                }
            } catch (NamingException e) {
                logger.log(Level.SEVERE, e.getMessage());
                throw new Exception(e.getMessage());
            }
        }
        return employee;
    }

    /**
     * getSearchResults.
     *
     * @param email email
     * @param activeDirectoryDetails activeDirectoryDetails
     * @param authContext authContext
     * @return NamingEnumeration<SearchResult>
     * @throws Exception Exception
     */
    private NamingEnumeration<SearchResult> getSearchResults(String email,
            ActiveDirectoryDetails activeDirectoryDetails, DirContext authContext) throws Exception {

        String ldapGroup = "CN=APP_HOAColorTool,OU=Application Groups,OU=Groups,DC=Masco_Coatings,DC=com";

        // Create the search controls
        SearchControls searchCtls = new SearchControls();

        // Specify the attributes to return
        String atttrList = activeDirectoryDetails.getReturnedAtts();
        List<String> returnedAtts = LDAPConstants.getLdapAttributes(atttrList);
        searchCtls.setReturningAttributes(returnedAtts.toArray(new String[0]));

        // Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        // specify the LDAP search filter
        String searchFilter = "(&(objectClass=user)&(mail=" + email + ")&(memberof=" + ldapGroup + "))";

        // Search for objects using the filter
        return authContext.search(activeDirectoryDetails.getSearchBase(), searchFilter, searchCtls);
    }
}
