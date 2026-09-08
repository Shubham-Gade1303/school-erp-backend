package com.schooleERP.repository;

import com.schooleERP.entity.SchoolInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface SchoolInformationRepo extends JpaRepository<SchoolInformation, Long> {


}
