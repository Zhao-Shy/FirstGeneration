package com.zhaoshy.firstgeneration.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.List;

/****************************************
 * @author : zhaoshy
 * @description : 权限
 * @create_time : 2019/12/15 12:32
 ****************************************
 */
@Alias("auth")
@Getter
@Setter
public class Authority {
    private Integer authId;
    private String authName;
    private String authUrl;
    private Integer parentId;
    private List<Role> roles;
    private List<Authority> authChild;
}
