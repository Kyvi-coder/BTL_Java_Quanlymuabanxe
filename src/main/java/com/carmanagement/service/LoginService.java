package com.carmanagement.service;

import com.carmanagement.entity.Employee;

public class LoginService {
    private final AccountService accountService = new AccountService();

    public boolean login(String username, String password) {
        return accountService.login(username, password) != null;
    }

    public Employee getEmployeeByAccount(String username, String password) {
        return accountService.getEmployeeByLogin(username, password);
    }
}
