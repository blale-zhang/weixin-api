package com.lou.weixin.sdk.bean.msg.event;

import com.lou.weixin.sdk.bean.msg.EventMessage;
import com.lou.weixin.sdk.constants.EventMsgType;
import com.lou.weixin.sdk.utils.XmlParseUtil;
import org.dom4j.Element;

/**
 * 点击菜单跳转链接时的事件推送
 *
 * @author loufeng
 * @date 2018/7/9 下午3:22.
 */
public class ViewEventMsg extends EventMessage {

    private static final long serialVersionUID = -4275122085036820729L;

    private String eventKey;// 事件KEY值，设置的跳转URL

    public ViewEventMsg() {
        this.setEventMsgType(EventMsgType.view);
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Override
    public void fromXmlNode(Element element) {
        this.eventKey = XmlParseUtil.getSubElementString(element, "eventKey");
    }

    @Override
    public void toXmlNode(Element element) {
    }

    @Override
    public String toString() {
        return "ViewEventMsg[msgId=" + this.getMsgId() + ",msgType=" + (null == this.getMsgType() ? ""
                : this.getMsgType().toString()) + ",eventMsgType=" + (null == this.getEventMsgType() ? ""
                : this.getEventMsgType().toString()) + ",eventKey=" + this.eventKey + "]";
    }
}
