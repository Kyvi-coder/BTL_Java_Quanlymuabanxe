package com.carmanagement.service;

import com.carmanagement.dao.accountDAO;
import com.carmanagement.dao.employeeDAO;
import com.carmanagement.entity.Account;
import com.carmanagement.entity.Employee;

public class AccountService {
    private final accountDAO accountDao = new accountDAO();
    private final employeeDAO employeeDao = new employeeDAO();

    public Account login(String username, String password) {
        return accountDao.getAccountByCredentials(username, password);
    }

    public Employee getEmployeeByLogin(String username, String password) {
        Account account = login(username, password);
        if (account == null || account.getId_employee() == null) {
            return null;
        }
        return employeeDao.getEmployeeById(account.getId_employee());
    }
}
