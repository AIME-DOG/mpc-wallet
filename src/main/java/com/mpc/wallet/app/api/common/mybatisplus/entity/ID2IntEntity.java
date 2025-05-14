package com.mpc.wallet.app.api.common.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 实体类父类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ID2IntEntity extends SuperEntity<Integer> implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

}