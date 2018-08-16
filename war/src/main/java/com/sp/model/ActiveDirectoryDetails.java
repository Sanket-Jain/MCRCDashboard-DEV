package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ActiveDirectoryDetails.
 *
 */
@Entity
@Table(name = "active_directory_details")
public class ActiveDirectoryDetails {

    @Id
    @Column(name = "ldap_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ldapId;

    @Column(name = "server_name")
    private String serverName;

    @Column(name = "port")
    private String port;

    @Column(name = "security_principal")
    private String securityPrincipal;

    @Column(name = "security_credentials")
    private String securityCredentials;

    @Column(name = "search_base")
    private String searchBase;

    @Column(name = "returned_atts")
    private String returnedAtts;

   /**
     * getServerName.
     *
     * @return String serverName
     */
    public String getServerName() {
        return serverName;
    }

   /**
     * setServerName.
     *
     * @param serverName serverName
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

   /**
     * getPort.
     *
     * @return String port
     */
    public String getPort() {
        return port;
    }

   /**
     * setPort.
     *
     * @param port port
     */
    public void setPort(String port) {
        this.port = port;
    }

   /**
     * getSecurityPrincipal.
     *
     * @return String securityPrincipal
     */
    public String getSecurityPrincipal() {
        return securityPrincipal;
    }

   /**
     * setSecurityPrincipal.
     *
     * @param securityPrincipal securityPrincipal
     */
    public void setSecurityPrincipal(String securityPrincipal) {
        this.securityPrincipal = securityPrincipal;
    }

   /**
     * getSecurityCredentials.
     *
     * @return String securityCredentials
     */
    public String getSecurityCredentials() {
        return securityCredentials;
    }

   /**
     * setSecurityCredentials.
     *
     * @param securityCredentials securityCredentials
     */
    public void setSecurityCredentials(String securityCredentials) {
        this.securityCredentials = securityCredentials;
    }

   /**
     * getSearchBase.
     *
     * @return String searchBase
     */
    public String getSearchBase() {
        return searchBase;
    }

   /**
     * setSearchBase.
     *
     * @param searchBase searchBase
     */
    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

   /**
     * getReturnedAtts.
     *
     * @return String returnedAtts
     */
    public String getReturnedAtts() {
        return returnedAtts;
    }

   /**
     * setReturnedAtts.
     *
     * @param returnedAtts returnedAtts
     */
    public void setReturnedAtts(String returnedAtts) {
        this.returnedAtts = returnedAtts;
    }

    @Override
    public String toString() {
        return "ActiveDirectoryDetails [serverName=" + serverName + ", port=" + port + ", securityPrincipal="
               + securityPrincipal + ", securityCredentials=" + securityCredentials + ", searchBase=" + searchBase
               + ", returnedAtts=" + returnedAtts + "]";
    }
}