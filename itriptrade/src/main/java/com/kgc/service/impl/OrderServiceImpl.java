package com.kgc.service.impl;

import com.kgc.beans.model.ItripHotelOrder;
import com.kgc.beans.model.ItripTradeEnds;
import com.kgc.dao.ItripHotelOrderMapper;
import com.kgc.dao.ItripTradeEndsMapper;
import com.kgc.service.OrderService;
import com.kgc.utils.EmptyUtils;
import com.kgc.utils.SystemConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    private ItripHotelOrderMapper itripHotelOrderMapper;
    @Resource
    private ItripTradeEndsMapper itripTradeEndsMapper;
    @Resource
    private SystemConfig systemConfig;
    @Override
    public ItripHotelOrder loadItripHotelOrder(String orderNo) throws Exception {
        Map<String ,Object> param = new HashMap<>();
        param.put("orderNo",orderNo);
        List<ItripHotelOrder> listByMap = itripHotelOrderMapper.getListByMap(param);
        if (EmptyUtils.isNotEmpty(listByMap)){
            return listByMap.get(0);
        }
        return null;
    }

    @Override
    public void paySuccess(String orderNo, int payType, String tradeNo) throws Exception {
        ItripHotelOrder itripHotelOrder = this.loadItripHotelOrder(orderNo);
        itripHotelOrder.setOrderStatus(2);
        itripHotelOrder.setPayType(payType);
        itripHotelOrder.setTradeNo(tradeNo);
        itripHotelOrderMapper.modify(itripHotelOrder);
        //增加订单后去处理
        ItripTradeEnds itripTradeEnds = new ItripTradeEnds();
        itripTradeEnds.setId(itripHotelOrder.getId());
        itripTradeEnds.setOrderNo(orderNo);
        itripTradeEnds.setFlag("0");
        itripTradeEndsMapper.save(itripTradeEnds);
        //通知biz处理后续工作
        SendGet(systemConfig.getTradeEndUrl(),"orderNo="+orderNo);
    }

    private void SendGet(String url,String orderNo) {
        URLConnection urlConnection;
        try {
            URL realUrl = new URL(url);
            //打开链接发送请求
            if (systemConfig.getTradeUseProxy()){
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                        systemConfig.getTradeProxyHost(), systemConfig.getTradeProxyPort()
                ));
                urlConnection = realUrl.openConnection(proxy);

            }else {
                urlConnection = realUrl.openConnection();
            }
            //设置属性
            urlConnection.setRequestProperty("accept","*/*");
            urlConnection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36");
            urlConnection.setRequestProperty("connection","Keep-Alive");
            //建立实际链接
            urlConnection.connect();
            System.out.println(urlConnection.getContentLength()+"==================");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void payFailed(String orderNo, int payType, String tradeNo) throws Exception {
        ItripHotelOrder itripHotelOrder = this.loadItripHotelOrder(orderNo);
        itripHotelOrder.setOrderStatus(1);
        itripHotelOrder.setPayType(payType);
        itripHotelOrder.setTradeNo(tradeNo);
        itripHotelOrderMapper.modify(itripHotelOrder);
    }

    @Override
    public boolean processed(String orderNo) throws Exception {
        ItripHotelOrder itripHotelOrder = this.loadItripHotelOrder(orderNo);
        return itripHotelOrder.getOrderStatus().equals(2) && EmptyUtils.isNotEmpty(itripHotelOrder.getTradeNo());
    }
}
