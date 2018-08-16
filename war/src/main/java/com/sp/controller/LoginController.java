package com.sp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.model.Employee;
import com.sp.service.AuthenticationService;
import com.sp.utils.LDAPConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * LoginController.
 *
 */
@Controller
@RequestMapping(value = "/mcrc")
public class LoginController {
    private final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private AuthenticationService authenticationService;


    /**
     * login.
     *
     * @param data data
     * @return Map<String, Object> result
     */
    @RequestMapping(value = "/login", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestBody String data) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(data);
            String email = om.convertValue(node.get("email"), String.class);
            String password = om.convertValue(node.get("password"), String.class);
            Employee searchResponse = authenticationService.searchUser(email, password);
            if (searchResponse.isValid()) {
                result.put("response", "success");
                result.put("success", true);
                result.put("error", "");
                result.put("employee", searchResponse);
            } else if (searchResponse.equals(LDAPConstants.LOCKED_USER)) {
                result.put("response", "success");
                result.put("success", true);
                result.put("error", LDAPConstants.LOCKED_USER_MSG);
            } else if (searchResponse.equals(LDAPConstants.INVALID_CREDENTIALS)) {
                result.put("response", "success");
                result.put("success", true);
                result.put("error", LDAPConstants.INVALID_CREDENTIALS);
            } else {
                result.put("response", "failure");
                result.put("success", true);
                result.put("error", LDAPConstants.INVALID_USER_MSG);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            result.put("response", "Server Error");
            result.put("success", false);
            result.put("error", LDAPConstants.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
