package priv.cqq.im.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * im 用户认证鉴权接口入参
 *
 * @author Qingquan.Cong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IMUserLoginVO {

    private String token;
}