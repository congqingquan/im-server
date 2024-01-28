package priv.cqq.im.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * IM消息表 表实体类
 *
 * @author CongQingquan
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("im_chat_message")
public class ChatMessage extends Model<ChatMessage> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "业务主键")
    @TableId(value = "message_id", type = IdType.ID_WORKER)
    private Long messageId;

    @ApiModelProperty(value = "发送用户主键")
    private Long fromUserId;

    @ApiModelProperty(value = "目标对象类型: 1单聊 2群聊")
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