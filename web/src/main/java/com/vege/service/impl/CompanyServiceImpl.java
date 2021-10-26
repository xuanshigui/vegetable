package com.vege.service.impl;

import com.vege.dao.CompanyRepository;
import com.vege.dao.ImageRepository;
import com.vege.model.Company;
import com.vege.model.Image;
import com.vege.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {


    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public boolean add(Company company) {
        try {
            companyRepository.save(company);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(String companyId) {
        try {
            Company company = companyRepository.findByCompanyId(Integer.parseInt(companyId));
            companyRepository.delete(company);
            if(company.getImgUuid()!=null){
                Image image = imageRepository.findByUuid(company.getImgUuid());
                imageRepository.delete(image);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
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
