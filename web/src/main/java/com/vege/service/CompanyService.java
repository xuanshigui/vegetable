package com.vege.service;

import com.vege.model.Company;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface CompanyService {

    boolean add(Company data);

    boolean delete(String companyId);

    boolean update(Company data);

    Page<Company> query(Map<String, String> condition);

    long queryTotal(Map<String, String> condition);

    Company queryById(String companyId);
}
