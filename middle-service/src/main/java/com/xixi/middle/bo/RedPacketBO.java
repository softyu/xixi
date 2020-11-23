package com.xixi.middle.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.middle.req
 * @Description: red
 * @date Date : 2020年11月23日 6:00 下午
 */
@Data
public class RedPacketBO {

    private Long userId;

    private Integer totalPeople;

    private Integer amountMoney;
}
