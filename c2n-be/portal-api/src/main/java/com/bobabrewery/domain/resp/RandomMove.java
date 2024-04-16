package com.bobabrewery.domain.resp;

import lombok.Data;

@Data
public class RandomMove {


    public RandomMove() {
    }

    public RandomMove(Long id) {
        this.id = id;
        this.deadLine = System.currentTimeMillis() / 1000L + 180;
    }

    /**
     * TokenID
     */
    private Long id;
    /**
     * deadline
     */
    private Long deadLine = System.currentTimeMillis() / 1000L + 180;
}
