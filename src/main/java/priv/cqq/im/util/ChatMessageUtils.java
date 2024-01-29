package priv.cqq.im.util;

/**
 * Chat message utils
 *
 * @author CongQingquan
 */
public class ChatMessageUtils {
    
    private ChatMessageUtils() {}
    
    public static String genFromTargetUserKey(Long from, Long target) {
        return from < target ? from + "-" + target : target + "-" + from;
    }
}
