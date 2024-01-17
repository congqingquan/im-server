package priv.cqq.im.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.cqq.im.domain.po.IMUser;
import priv.cqq.im.mapper.IMUserMapper;

/**
 * im 用户信息表 Manager
 *
 * @author Qingquan.Cong
 */
@Service
public class IMUserManager extends ServiceImpl<IMUserMapper, IMUser> {

}