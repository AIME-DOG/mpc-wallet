package com.mpc.wallet.app.api.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "AuthReq", description = "User Auth Param")
public class AuthRes implements Serializable {

    @Schema(name = "status", description = "Status")
    private String status;

    @Schema(name = "status", description = "Status")
    private String message;

    @Schema(name = "accountId", description = "Account ID")
    private Long accountId;

    @Schema(name = "privyUserId", description = "Privy User ID")
    private String privyUserId;

}