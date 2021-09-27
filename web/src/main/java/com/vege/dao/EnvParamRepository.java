package com.vege.dao;

import com.vege.model.EnvParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvParamRepository extends JpaRepository<EnvParam, Integer> {

    EnvParam findByEpId(Integer envParamId);

    Page<EnvParam> findAll(Pageable pageable);

    Page<EnvParam> findAllByParaNameLike(String paraName,Pageable pageable);

    Page<EnvParam> findAllByBreedStage_StageNameLike(String breedStageName, Pageable pageable);

    Page<EnvParam> findAllByParaNameLikeAndBreedStage_StageNameLike(String paraName, String breedStageName,Pageable pageable);

    //@Query(value = "select tb_environmentparameter.* from tb_breedstage,tb_environmentparameter where tb_breedstage.bsid = tb_environmentparameter.bsid and tb_environmentparameter.paraname like '%?1%' and tb_breedstage.stagename like '%?2%' limit ?3,?4",nativeQuery = true)
    //List<EnvParam> findAllByParaNameAndStageName(String paraName, String breedStageName, int offset,int size);
}
