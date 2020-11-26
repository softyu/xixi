package com.xixi.middle.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.req
 * @Description: rob
 * @date Date : 2020年11月23日 6:00 下午
 */
@Data
public class RobPacketBO {

    @NotNull
    private Long userId;

    @NotBlank
    private String redId;

}
