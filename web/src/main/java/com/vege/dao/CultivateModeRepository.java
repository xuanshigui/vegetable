package com.vege.dao;

import com.vege.model.CultivateMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CultivateModeRepository extends JpaRepository<CultivateMode, Integer> {

    CultivateMode findByCmId(Integer cmId);

    Page<CultivateMode> findAll(Pageable pageable);

    Page<CultivateMode> findAllByCultivateModeNameLike(String cultivateModeName,Pageable pageable);

}
