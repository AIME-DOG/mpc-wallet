package com.mpc.wallet.app.api.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class AuthReq implements Serializable {

    @Schema(name = "accessToken", description = "Privy Token", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String accessToken;

}