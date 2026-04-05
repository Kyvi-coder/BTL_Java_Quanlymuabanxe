package com.carmanagement.service;

import com.carmanagement.dao.warrantyDAO;
import com.carmanagement.entity.Warranty;

import java.time.LocalDate;
import java.util.List;

public class WarrantyService {
    private final warrantyDAO warrantyDao = new warrantyDAO();

    public List<Warranty> getAllWarranty() {
        return warrantyDao.getAllWarranty();
    }

    public Warranty getWarrantyInfoByVIN(String vin) {
        return warrantyDao.getWarrantyInfoByVIN(vin);
    }

    public boolean insertWarranty(String id, String vin, String startDate, String endDate) {
        Warranty warranty = new Warranty();
        warranty.setId_warranty(id);
        warranty.setVIN(vin);
        warranty.setStart_date_warranty(LocalDate.parse(startDate));
        warranty.setEnd_date_warranty(LocalDate.parse(endDate));
        return warrantyDao.insertWarranty(warranty);
    }

    public void deleteExpiredWarranty() {
        warrantyDao.deleteExpiredWarranty();
    }
}
