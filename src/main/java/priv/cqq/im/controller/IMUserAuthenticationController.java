package priv.cqq.im.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.cqq.openlibrary.common.domain.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import priv.cqq.im.domain.dto.IMUserLoginDTO;
import priv.cqq.im.domain.vo.IMUserLoginVO;
import priv.cqq.im.service.IMAuthenticationService;

@Tag(name = "IM用户登陆鉴权接口")
@RestController
@AllArgsConstructor
public class IMUserAuthenticationController {

    private final IMAuthenticationService authService;

    @Operation(summary = "登录")
    @PostMapping("/im/c/user/login")
    public R<IMUserLoginVO> login(@RequestBody IMUserLoginDTO authInfoDTO) {
        return R.success(authService.login(authInfoDTO));
    }
}
