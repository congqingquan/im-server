package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import org.apache.commons.lang3.ObjectUtils;
import org.cqq.openlibrary.common.exception.BusinessException;
import org.cqq.openlibrary.common.util.EnumUtils;
import org.cqq.openlibrary.common.util.JSONUtils;
import org.springframework.stereotype.Component;
import priv.cqq.im.netty.entity.message.Message;
import priv.cqq.im.netty.enums.MessageCategoryEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息处理器管理器
 *
 * @author CongQingquan
 */
@Component
public class MessageHandlerManager {
    
    private final Map<MessageCategoryEnum, List<MessageHandler<? extends Message>>> messageHandlerMap;
    
    public MessageHandlerManager(List<MessageHandler<? extends Message>> messageHandlers) {
        this.messageHandlerMap =
                ObjectUtils.defaultIfNull(messageHandlers, new ArrayList<MessageHandler<? extends Message>>())
                .stream()
                .collect(Collectors.groupingBy(MessageHandler::supportedCategory));
    }
    
    public void handleMessage(Channel channel, String messageString) throws ClassCastException {
        // 1. 获取消息类型
        Message message = JSONUtils.parseObject(messageString, Message.class);
        MessageCategoryEnum messageCategoryEnum =
                EnumUtils.equalMatch(MessageCategoryEnum.values(), MessageCategoryEnum::name, message.getCategory())
                        .orElseThrow(() -> new BusinessException("No supported message type"));
        // 2. 根据消息类型获取对应的消息实体，并读取完整的消息体
        List<MessageHandler<? extends Message>> messageHandlers = messageHandlerMap.get(messageCategoryEnum);
        for (MessageHandler<? extends Message> messageHandler : messageHandlers) {
            Message fullMessage = JSONUtils.parseObject(messageString, messageHandler.supportedMessageClass());
            messageHandler.handleMessage(channel, fullMessage);
        }
    }
}