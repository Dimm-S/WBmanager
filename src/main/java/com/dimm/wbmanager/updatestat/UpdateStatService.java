package com.dimm.wbmanager.updatestat;

public interface UpdateStatService {
    void addRecord(String tableName, Boolean isUpdate, Long odid);
}
