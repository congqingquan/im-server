package priv.cqq.im.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * im 用户信息 DTO
 *
 * @author Qingquan.Cong
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class IMUserAuthDTO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "业务主键")
    @TableId(value = "user_id", type = IdType.ID_WORKER)
    private Long userId;

    @ApiModelProperty(value = "帐号")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别: 1男性, 2女性")
    private Integer gender;
}