package com.sp.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * LDAPConstants.
 *
 */
public final class LDAPConstants {
    /**
     * VALID_USER.
     */
    public static final String VALID_USER = "Valid User";

    /**
     * INVALID_USER.
     */
    public static final String INVALID_USER = "Invalid User";
    /**
     * LOCKED_USER.
     */
    public static final String LOCKED_USER = "Locked User";
    /**
     * LOCKED_USER_MSG.
     */
    public static final String LOCKED_USER_MSG = "You have reached the maximum attempts to login and now your "
                            + "account is locked out. Please call x2370 for assistance.";
    /**
     * INVALID_USER_MSG.
    */
    public static final String INVALID_USER_MSG = "You are not authorized to access this tool. "
                            + "If you require access, please contact your supervisor.";

    /**
     * INVALID_CREDENTIALS.
     */
    public static final String INVALID_CREDENTIALS = "We are unable to log you in with the email and/or "
                            + "password provided. Please make sure you entered them correctly and try again.";
    /**
     * INTERNAL_SERVER_ERROR.
     */
    public static final String INTERNAL_SERVER_ERROR = "The service is not currently "
                            + "available. Please try again later.";
    /**
     * DELETED.
     */
    public static final String DELETED = "Deleted";
    /**
     * ADDED.
     */
    public static final String ADDED = "Added";
    private LDAPConstants() {
        // Prevent instantiation
    }

    /**
     * getLdapAttributes.
     *
     * @param atttrList atttrList
     * @return List<String> ldapAttr
     */
    public static List<String> getLdapAttributes(String atttrList) {
        List<String> ldapAttr = new ArrayList<String>();
        for (String attr : atttrList.split(",")) {
            ldapAttr.add(attr.trim());
        }
        ldapAttr.add("name");
    	ldapAttr.add("title");
        return ldapAttr;
    }
}
