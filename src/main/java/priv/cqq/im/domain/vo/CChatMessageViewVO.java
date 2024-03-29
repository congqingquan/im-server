package priv.cqq.im.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * IM消息表 详情接口出参
 *
 * @author CongQingquan
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CChatMessageViewVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "业务主键")
    private Long messageId;

    @ApiModelProperty(value = "发送用户主键")
    private Long fromUserId;

    @ApiModelProperty(value = "目标对象类型: 1用户 2群组")
    private Integer targetType;

    @ApiModelProperty(value = "目标用户主键(type为1时有值)")
    private Long targetUserId;

    @ApiModelProperty(value = "发送与目标用户的主键拼接Key(根据user_id排序后通过中横线 - 连接)，用于快速搜索私聊消息(type为1时有值)")
    private String fromTargetUserKey;

    @ApiModelProperty(value = "群聊主键(type为2时有值)")
    private Long chatGroupId;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "回复的消息主键")
    private Long replyMsgId;

    @ApiModelProperty(value = "与回复的消息间隔多少条")
    private Integer gapCount;

    @ApiModelProperty(value = "消息类型: 1正常文本, 2撤回消息")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}