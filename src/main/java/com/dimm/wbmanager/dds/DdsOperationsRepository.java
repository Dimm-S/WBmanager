package com.dimm.wbmanager.dds;

import com.dimm.wbmanager.dds.dto.DdsOperationDto;
import com.dimm.wbmanager.dds.model.DdsOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DdsOperationsRepository extends JpaRepository<DdsOperation, Long> {

    @Query(nativeQuery = true, value = "SELECT " +
            "EXTRACT(MONTH FROM oper_date) AS MONTH, " +
            "EXTRACT(YEAR FROM oper_date) AS YEAR, " +
            "SUM(CASE WHEN is_incoming = true AND type = 'oper' THEN sum ELSE 0 END) " +
                "- SUM(CASE WHEN is_incoming = false AND type = 'oper' THEN sum ELSE 0 END) as oper_saldo,\n" +
            "SUM(CASE WHEN is_incoming = true AND type = 'invest' THEN sum ELSE 0 END) " +
                "- SUM(CASE WHEN is_incoming = false AND type = 'invest' THEN sum ELSE 0 END) as invest_saldo,\n" +
            "SUM(CASE WHEN is_incoming = true AND type = 'fin' THEN sum ELSE 0 END) " +
                "- SUM(CASE WHEN is_incoming = false AND type = 'fin' THEN sum ELSE 0 END) as fin_saldo\n" +
            "FROM dds_operations as o\n" +
            "         JOIN dds_items AS i ON o.dds_item = i.id\n" +
            "WHERE type NOTNULL\n" +
            "GROUP BY MONTH, YEAR\n" +
            "ORDER BY YEAR ASC, MONTH ASC")
    List<Object[]> getDds();

    @Query(nativeQuery = true, value = "SELECT " +
            "EXTRACT(MONTH FROM oper_date) AS MONTH,\n" +
            "EXTRACT(YEAR FROM oper_date) AS YEAR,\n" +
            "SUM(CASE WHEN is_incoming = true THEN sum ELSE - sum END)\n" +
            "FROM dds_operations as o\n" +
            "JOIN dds_items AS i ON o.dds_item = i.id\n" +
            "WHERE i.id = ?1\n" +
            "GROUP BY MONTH, YEAR\n" +
            "ORDER BY YEAR ASC, MONTH ASC;")
    List<Object[]> getItemDds(Long itemId);

    @Query(nativeQuery = true, value = "SELECT SUM(CASE WHEN is_incoming = true AND EXTRACT(MONTH FROM oper_date) = ?1 AND EXTRACT(YEAR FROM oper_date) = ?2 THEN sum\n" +
            "WHEN is_incoming = false AND EXTRACT(MONTH FROM oper_date) = ?1 AND EXTRACT(YEAR FROM oper_date) = ?2 THEN - sum\n" +
            "ELSE 0 END)\n" +
            "FROM dds_items as i\n" +
            "LEFT OUTER JOIN dds_operations AS o ON i.id = o.dds_item\n" +
            "WHERE type = ?3\n" +
            "GROUP BY i.item\n" +
            "ORDER BY i.item\n")
    List<Double> getDdsByItemsForPeriod(Integer month, Integer year, String type);

    @Query("select new com.dimm.wbmanager.dds.dto.DdsOperationDto(o.operDate, i.item, o.sum, a.name, o.subject, o.description) " +
            "from DdsOperation as o " +
            "join DdsItem as i on o.ddsItem = i.id " +
            "join DdsAccount as a on o.account = a.id " +
            "where o.operDate >= ?1 and o.operDate <= ?2 " +
            "order by o.operDate")
    List<DdsOperationDto> getAllOperations(LocalDate from, LocalDate to);
}
