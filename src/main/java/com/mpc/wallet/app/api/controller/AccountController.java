package com.mpc.wallet.app.api.controller;

import com.mpc.wallet.app.api.common.response.Result;
import com.mpc.wallet.app.api.model.req.AuthReq;
import com.mpc.wallet.app.api.model.res.AuthRes;
import com.mpc.wallet.app.api.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "API")
@RestController
@RequestMapping("/v1/account")
public class AccountController {

    @Resource
    private IAccountService accountService;

    @PostMapping("/auth")
    @Operation(summary = "Auth API")
    Result<AuthRes> auth(@Valid @RequestBody AuthReq req) {
        return Result.success(accountService.auth(req));
    }

}