package com.zzz.controller;

import com.zzz.model.HistoryDatas.BranchHistoryData;
import com.zzz.model.OnlineDatas.BranchOnlineData;
import com.zzz.model.SysUser;
import com.zzz.result.ResponseCode;
import com.zzz.result.Results;
import com.zzz.service.BranchDataService;
import com.zzz.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Slf4j
@RequestMapping("/branchData")
public class BranchDataController {

    @Autowired
    private BranchDataService branchDataService;

    @Autowired
    private PermissionService permissionService;


    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public Results<BranchOnlineData> getBranchOnlineData(@RequestParam int branchId){

        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(!permissionService.hasBranchPermission(user,branchId)){
            return Results.failure(ResponseCode.FORBIDDEN);
        }

        return Results.success(ResponseCode.SUCCESS, branchDataService.getBranchOnlineData(branchId));
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public Results<BranchHistoryData> getBranchHistoryData(@RequestParam int branchId, @RequestParam String dateStr){
        Date date;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            date = simpleDateFormat.parse(dateStr);
        }catch (ParseException e){
            return Results.failure(ResponseCode.BAD_DATE_FORMAT);
        }

        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(!permissionService.hasBranchPermission(user,branchId)){
            return Results.failure(ResponseCode.FORBIDDEN);
        }

        return Results.success(ResponseCode.SUCCESS, branchDataService.getBranchHistoryData(branchId, date));
    }
    @RequestMapping(value = "/historys", method = RequestMethod.GET)
    public Results<BranchHistoryData> getBranchHistoryData(@RequestParam int branchId,
                                                     @RequestParam String dateBeginStr,
                                                     @RequestParam String dateEndStr2,
                                                     @RequestParam(name="page", defaultValue = "1") int page,
                                                     @RequestParam(name="pageSize", defaultValue = "30") int pageSize){
        Date begin;
        Date end;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            begin = simpleDateFormat.parse(dateBeginStr);
            end = simpleDateFormat.parse(dateEndStr2);
        }catch (ParseException e){
            return Results.failure(ResponseCode.BAD_DATE_FORMAT);
        }

        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(!permissionService.hasBranchPermission(user,branchId)){
            return Results.failure(ResponseCode.FORBIDDEN);
        }

        int total = branchDataService.getHistoryDataNum(branchId, begin, end);
        return Results.success(ResponseCode.SUCCESS, total, branchDataService.getBranchHistoryData(branchId, begin, end, page, pageSize));
    }
}