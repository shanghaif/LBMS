package com.zzz.controller;

import com.zzz.exception.ForBiddenException;
import com.zzz.model.SysUser;
import com.zzz.model.SysUserDetails;
import com.zzz.result.ResponseCode;
import com.zzz.result.Results;
import com.zzz.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserDetailsController {

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Results<SysUserDetails> getUserList(@RequestParam(name="page", defaultValue = "1") int page,
                                               @RequestParam(name="pageSize", defaultValue = "30") int pageSize){

//        if(page < 1){
//            return Results.failure(ResponseCode.BAD_REQUEST);
//        }

        List<SysUserDetails> userArrayList = userService.getUsers(page, pageSize);
        int total = userService.getTotalNum();
        return Results.success(ResponseCode.SUCCESS, total, userArrayList);
    }

    @RequestMapping(value="",method = RequestMethod.GET)
    public Results<SysUserDetails> getUserDetails() throws ForBiddenException {

        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            throw new ForBiddenException("用户身份已过期，请重新登录");
        }
        int employeeId = user.getEmployeeId();

        SysUserDetails userDetails = userService.getUser(employeeId);
        return Results.success(ResponseCode.SUCCESS, userDetails);
    }

    @RequestMapping(value="",method = {RequestMethod.PUT, RequestMethod.POST})
    public Results changeUserDetails(@RequestParam(required = false) String nickname,
                                     @RequestParam(required = false) String avatar){

        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        int employeeId = user.getEmployeeId();

        SysUserDetails currentUser = userService.getUser(employeeId);
        if(!(nickname==null)){
            currentUser.setNickname(nickname);
        }
        if(!(avatar==null)){
            currentUser.setAvatar(avatar);
        }
        userService.changeDetails(employeeId,currentUser);
        return Results.success(ResponseCode.SUCCESS, "修改成功");
    }
}
