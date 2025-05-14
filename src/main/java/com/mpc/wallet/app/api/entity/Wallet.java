package com.mpc.wallet.app.api.entity;

import com.mpc.wallet.app.api.common.mybatisplus.entity.BaseIntEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("wallet")
public class Wallet extends BaseIntEntity {

    /**
     * 用户ID
     */
    private Long accountId;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

}