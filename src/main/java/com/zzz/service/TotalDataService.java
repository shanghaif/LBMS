package com.zzz.service;

import com.zzz.model.HistoryDatas.BranchHistoryData;
import com.zzz.model.HistoryDatas.TotalHistoryData;
import com.zzz.model.OnlineDatas.BranchOnlineData;
import com.zzz.model.OnlineDatas.TotalOnlineData;

import java.util.Date;
import java.util.List;

public interface TotalDataService {

    List<TotalOnlineData> getTotalOnlineData(int totalId, Date datetime);

    TotalHistoryData getTotalHistoryData(int totalId, Date date);

    List<TotalHistoryData> getTotalHistoryData(int totalId, Date begin, Date end, int page, int pageSize);

    Integer getHistoryDataNum(int totalId, Date begin, Date end);

    TotalOnlineData calTotalRealtimeDataFromBranchs(int totalId, Date datetime);

    Integer getSumWatch();

    TotalOnlineData getLastOnlineData();

    List<BranchOnlineData> getBranchOnlineRank(Integer totalId);

    List<BranchHistoryData> getBranchHistoryRank(Date begin, Date end, int levelId);

    TotalHistoryData calHistory(int totalId, Date datetime);
}
