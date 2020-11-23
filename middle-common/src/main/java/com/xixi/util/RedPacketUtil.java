package com.xixi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author : xiaoyu
 * @version V1.0
 * @Project: xixi
 * @Package com.xixi.util
 * @Description: red util
 * @date Date : 2020年11月20日 1:50 下午
 */
public class RedPacketUtil {


    /**
     * @param totalAmount
     * @param totalPeople
     * @return
     */
    public static List<Integer> divideRedPacket(Integer totalAmount, Integer totalPeople) {
        List<Integer> redLists = new ArrayList<>();
        if (totalAmount <= 0 && totalPeople <= 0) {
            throw new RuntimeException("valid params");
        }
        Integer resetAmount = totalAmount;
        Integer restPeople = totalPeople;
        Random random = new Random();

        for (int i = 0; i < totalPeople - 1; i++) {
            int randomAmount = random.nextInt(resetAmount / restPeople * 2 - 1) + 1;
            resetAmount -= randomAmount;
            restPeople--;
            redLists.add(randomAmount);
        }
        redLists.add(resetAmount);
        return redLists;
    }
}
