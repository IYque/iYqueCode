package cn.iyque.service;

import cn.iyque.domain.IYqueConfig;
import me.chanjar.weixin.cp.api.WxCpService;

public interface IYqueConfigService {

    IYqueConfig findIYqueConfig();

    void saveOrUpdate(IYqueConfig iYqueConfig);

    WxCpService findWxcpservice();
}
