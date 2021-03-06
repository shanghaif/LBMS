package com.zzz.dao;

import com.zzz.model.SysUser;
import com.zzz.model.SysUserDetails;
import com.zzz.model.SysUserSettings;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserDao {
    List<SysUserDetails> getUserWithPagination(int offset, int limit);

    Integer getTotalNum();

    SysUserDetails getUserById(int employeeId);

    SysUser getSimpleUserById(int employeeId);

    void changePassword(int employeeId, String newPassword);

    void changeDetails(int employeeId, String nickname, String avatar);

    SysUserSettings getSettings(int employeeId);

    void changeSettings(SysUserSettings settings);

    SysUser getSimpleUserByName(String username);
}
