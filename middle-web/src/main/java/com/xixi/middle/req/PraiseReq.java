package com.xixi.middle.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.req
 * @Description: praise
 * @date Date : 2020年12月10日 3:57 下午
 */
@Data
public class PraiseReq implements Serializable {
    @NotNull
    private Long blogId;
    @NotNull
    private Long userId;
}
