package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.dds.model.DdsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DdsAccountRepository extends JpaRepository<DdsAccount, Long> {
    @Query(nativeQuery = true, value = "SELECT * " +
            "FROM dds_accounts;")
    List<DdsAccount> getAll();
}
