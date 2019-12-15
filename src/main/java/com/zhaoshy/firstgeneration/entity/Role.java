package com.zhaoshy.firstgeneration.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/****************************************
 * @author : zhaoshy
 * @description : 用户角色
 * @create_time : 2019/12/15 12:04
 ****************************************
 */
@Alias("role")
@Getter
@Setter
public class Role {
    private Integer roleId;
    private String roleCode;
    private String roleName;
}
