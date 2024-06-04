package cn.iyque.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.iyque.dao.IYqueConfigDao;
import cn.iyque.domain.IYqueConfig;
import cn.iyque.service.IYqueConfigService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class IYqueConfigServiceImpl implements IYqueConfigService {

    @Autowired
    IYqueConfigDao iYqueConfigDao;
    @Override
    public IYqueConfig findIYqueConfig() {
        List<IYqueConfig> iYqueConfigs = iYqueConfigDao.findAll();
        if(CollectionUtil.isEmpty(iYqueConfigs)){
            return new IYqueConfig();
        }
        return iYqueConfigs.stream().findFirst().get();
    }

    @Override
    public void saveOrUpdate(IYqueConfig iYqueConfig) {
        iYqueConfigDao.saveAndFlush(iYqueConfig);
    }

    @Override
    public WxCpService findWxcpservice() {

        WxCpService config = WxCpServiceFactory.createWxCpService(findIYqueConfig());

        return config;
    }
}
