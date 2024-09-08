package priv.cqq.im.netty.handler.message;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import priv.cqq.im.domain.po.IMChatGroupMember;
import priv.cqq.im.manager.IMChatGroupManager;
import priv.cqq.im.manager.IMChatGroupMemberManager;
import priv.cqq.im.manager.IMChatMessageManager;
import priv.cqq.im.netty.constants.NettyConstants;
import priv.cqq.im.netty.entity.message.GroupMessage;
import priv.cqq.im.netty.enums.MessageCategoryEnum;
import priv.cqq.im.netty.session.SessionManager;
import priv.cqq.im.util.NettyUtils;
import priv.cqq.im.util.RedisPublisher;

import java.util.List;

/**
 * Group message handler
 *
 * @author CongQingquan
 */
@Component
public class GroupMessageHandler extends MessageHandler<GroupMessage> {
    
    public GroupMessageHandler(IMChatMessageManager chatMessageManager,
                                   IMChatGroupManager chatGroupManager,
                                   IMChatGroupMemberManager chatGroupMemberManager,
                                   RedisPublisher redisPublisher) {
        super(chatMessageManager, chatGroupManager, chatGroupMemberManager, redisPublisher);
    }
    
    @Override
    public MessageCategoryEnum supportedCategory() {
        return MessageCategoryEnum.GROUP;
    }
    
    @Override
    public Class<GroupMessage> supportedMessageClass() {
        return GroupMessage.class;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doHandleMessage(Channel channel, GroupMessage message) {
        // 1. 落库
        chatMessageManager.saveGroupMessage(message);
        // 2. 发送消息至目标对象
        Long currentUserId = NettyUtils.getAttr(channel, NettyConstants.UID);
        List<IMChatGroupMember> groupMembers = chatGroupMemberManager.selectMembersByGroupId(message.getChatGroupId(), currentUserId);
        for (IMChatGroupMember groupMember : groupMembers) {
            Long memberUserId = groupMember.getUserId();
            Channel memberChannel = SessionManager.get(memberUserId);
            if (memberChannel == null) {
                continue;
            }
            memberChannel.writeAndFlush(new TextWebSocketFrame(message.getContent()));
        }
    }
}
