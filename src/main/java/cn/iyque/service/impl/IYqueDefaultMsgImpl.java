package cn.iyque.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.iyque.constant.IYqueContant;
import cn.iyque.dao.IYqueDefaultMsgDao;
import cn.iyque.dao.IYqueUserCodeDao;
import cn.iyque.domain.IYqueCallBackBaseMsg;
import cn.iyque.domain.IYqueDefaultMsg;
import cn.iyque.domain.IYqueUserCode;
import cn.iyque.service.IYqueConfigService;
import cn.iyque.service.IYqueDefaultMsgService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.cp.api.WxCpExternalContactService;
import me.chanjar.weixin.cp.bean.external.WxCpWelcomeMsg;
import me.chanjar.weixin.cp.bean.external.contact.WxCpExternalContactInfo;
import me.chanjar.weixin.cp.bean.external.msg.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class IYqueDefaultMsgImpl implements IYqueDefaultMsgService {

    @Autowired
    IYqueDefaultMsgDao iYqueDefaultMsgDao;

    @Autowired
    IYqueUserCodeDao iYqueUserCodeDao;


    @Autowired
    private IYqueConfigService iYqueConfigService;



    @Override
    public IYqueDefaultMsg findDefaultMsg() {
        List<IYqueDefaultMsg> iYqueDefaultMsgs = iYqueDefaultMsgDao.findAll();
        if(CollectionUtil.isEmpty(iYqueDefaultMsgs)){
            return new IYqueDefaultMsg();
        }
        return iYqueDefaultMsgs.stream().findFirst().get();
    }

    @Override
    public void saveOrUpdate(IYqueDefaultMsg iYqueDefaultMsg) {
        iYqueDefaultMsgDao.saveAndFlush(iYqueDefaultMsg);
    }

    @Override
    public void sendWelcomeMsg(IYqueCallBackBaseMsg callBackBaseMsg) {

        StringBuilder tagIds=new StringBuilder();
        try {
            WxCpWelcomeMsg wxCpWelcomeMsg=new WxCpWelcomeMsg();
            wxCpWelcomeMsg.setWelcomeCode(callBackBaseMsg.getWelcomeCode());
            Text text = new Text();

            if(StrUtil.isEmpty(callBackBaseMsg.getState())){
                this.setDefaultMsg(text);
            }else{
                IYqueUserCode iYqueUserCode = iYqueUserCodeDao.findByCodeState(callBackBaseMsg.getState());
                if(null != iYqueUserCode){
                    //标签
                    tagIds.append(iYqueUserCode.getTagId());
                    if(StrUtil.isNotEmpty(iYqueUserCode.getWeclomeMsg())){
                        text.setContent(iYqueUserCode.getWeclomeMsg());
                    }else{
                        this.setDefaultMsg(text);
                    }

                }else{
                    this.setDefaultMsg(text);
                }
            }

            WxCpExternalContactService externalContactService
                    = iYqueConfigService.findWxcpservice().getExternalContactService();

            if(StrUtil.isNotEmpty(text.getContent())){
                //替换为真实用户名
                if(text.getContent().contains(IYqueContant.USER_NICKNAME_TPL)){
                    WxCpExternalContactInfo contactDetail = externalContactService.getContactDetail(callBackBaseMsg.getExternalUserID(), null);
                    log.info("获取客户信息:"+contactDetail);
                    if(null != contactDetail){
                        text.setContent(
                                text.getContent().replace(IYqueContant.USER_NICKNAME_TPL,  contactDetail.getExternalContact().getName())
                        );
                    }
                }

            }

            wxCpWelcomeMsg.setText(text);

            externalContactService.sendWelcomeMsg(wxCpWelcomeMsg);

        }catch (Exception e){
            log.error("欢迎语发送失败:"+e.getMessage());
        }finally {

            if(StrUtil.isNotEmpty(tagIds.toString())){

                try {
                    iYqueConfigService.findWxcpservice().getExternalContactService()
                            .markTag(callBackBaseMsg.getUserID(),callBackBaseMsg.getExternalUserID(),tagIds.toString().split(","),null);
                }catch (Exception e){
                    log.error("未客户打标签失败:"+e.getMessage());
                }


            }

        }

    }


    //设置全局欢迎语
    public void setDefaultMsg(Text text){
        IYqueDefaultMsg defaultMsg = findDefaultMsg();
        if(null != defaultMsg){
            text.setContent(defaultMsg.getDefaultContent());
        }
    }

}
