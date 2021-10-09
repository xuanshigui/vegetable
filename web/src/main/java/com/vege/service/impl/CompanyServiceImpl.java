package com.vege.service.impl;

import com.vege.dao.CompanyRepository;
import com.vege.model.Company;
import com.vege.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {


    @Autowired
    CompanyRepository companyRepository;

    @Override
    public boolean add(Company company) {
        try {
            companyRepository.save(company);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String companyId) {
        try {
            Company company = companyRepository.findByCompanyId(Integer.parseInt(companyId));
            companyRepository.delete(company);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(Company company) {
        try {
            companyRepository.save(company);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Company> query(Map<String, String> condition) {

        String companyNameStr = condition.get("companyName");
        if(companyNameStr != null && !companyNameStr.equals("")){
            companyNameStr = "%" + companyNameStr + "%";
            return companyRepository.findAllByCompanyNameLike(companyNameStr,getPageable(condition));
        }
        return companyRepository.findAll(getPageable(condition));
    }

    @Override
    public long queryTotal(Map<String, String> condition) {
        return companyRepository.count();
    }

    @Override
    public Company queryById(String companyId) {
        return companyRepository.findByCompanyId(Integer.parseInt(companyId));
    }


}
