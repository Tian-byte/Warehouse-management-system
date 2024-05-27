package com.wms.service.imp;

import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tian
 * @since 2024-05-27
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

}
