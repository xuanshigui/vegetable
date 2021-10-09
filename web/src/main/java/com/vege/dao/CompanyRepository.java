package com.vege.dao;

import com.vege.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Integer> {


    Company findByCompanyId(Integer companyId);

    Page<Company> findAll(Pageable pageable);

    Page<Company> findAllByCompanyNameLike(String companyName, Pageable pageable);

}
