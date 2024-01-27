package priv.cqq.im.controller.im;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.cqq.oplibrary.web.entity.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import priv.cqq.im.annotation.Authentication;
import priv.cqq.im.domain.dto.IMUserLoginDTO;
import priv.cqq.im.domain.vo.IMUserLoginVO;
import priv.cqq.im.service.im.IMAuthService;

/**
 * 认证鉴权接口
 *
 * @author CongQingquan
 */
@Api(value = "认证鉴权接口", tags = {"模块: 认证鉴权"})
@RestController
@AllArgsConstructor
public class IMUserAuthController {

    private final IMAuthService authService;

    @ApiOperation("登录")
    @Authentication(require = false)
    @PostMapping("/im/c/user/login")
    public R<IMUserLoginVO> login(@RequestBody IMUserLoginDTO authInfoDTO) {
        return R.success(authService.login(authInfoDTO));
    }
}
