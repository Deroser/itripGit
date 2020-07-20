package com.kgc.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.kgc.beans.model.ItripHotelOrder;
import com.kgc.config.AlipayConfig;
import com.kgc.service.OrderService;
import com.kgc.utils.EmptyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * PaymentController
 * 李文俊
 * 2020.7.20
 */
@Controller
@RequestMapping("/api")
public class PaymentController {
    private Logger logger = Logger.getLogger(PaymentController.class);
    @Resource
    private AlipayConfig alipayConfig;
    @Resource
    private OrderService orderService;
    @RequestMapping(value = "/prepay/{orderNo}",method = RequestMethod.GET)
    public String prepay(@PathVariable String orderNo, ModelMap model){
        try {
            ItripHotelOrder itripHotelOrder = orderService.loadItripHotelOrder(orderNo);
            if (EmptyUtils.isNotEmpty(itripHotelOrder)){
                model.addAttribute("orderNo",itripHotelOrder.getOrderNo());
                model.addAttribute("hotelName",itripHotelOrder.getHotelName());
                model.addAttribute("payAmount",itripHotelOrder.getPayAmount());
                model.addAttribute("count",itripHotelOrder.getCount());
                model.addAttribute("roomId",itripHotelOrder.getRoomId());
                return "pay";
            }else {
                return "notfound";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value ="/pay",method = RequestMethod.POST)
    public void pay(@RequestParam(value = "WIDout_trade_no") String WIDout_trade_no,
                    @RequestParam(value = "WIDsubject") String WIDsubject,
                    @RequestParam(value = "WIDtotal_amount") String WIDtotal_amount,
                    HttpServletResponse response) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getUrl(), alipayConfig.getAppId(), alipayConfig.getRsaPrivateKey(), alipayConfig.getFormat(),alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturn_url());
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());

        String body = "酒店房间商品";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ WIDout_trade_no +"\","
                + "\"total_amount\":\""+ WIDtotal_amount +"\","
                + "\"subject\":\""+ WIDsubject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        try {
            // 调用SDK生成表单
            response.setContentType("text/html;charset=" + alipayConfig.getCharset());
            response.getWriter().write(result);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*异步通知*/
    @RequestMapping(value ="/notify",method = RequestMethod.POST)
    public void trackPaymentStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* *
         * 功能：支付宝服务器异步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
         * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
         * 如果没有收到该页面返回的 success
         * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
         */

        //获取支付宝POST过来反馈信息
        System.out.println("异步------------------------------------------");
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(),
                alipayConfig.getCharset(),alipayConfig.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        if(signVerified) {//验证成功

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                /*执行自己的业务代码*/
                if (!orderService.processed(out_trade_no)){
                    orderService.paySuccess(out_trade_no,2,trade_no);
                }
                logger.info("订单"+out_trade_no+"交易完成");
                System.out.println("完成------------------------------------------");
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                if (!orderService.processed(out_trade_no)){
                    orderService.paySuccess(out_trade_no,2,trade_no);
                }
                logger.info("订单"+out_trade_no+"交易成功");
                System.out.println("成功------------------------------------------");
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            response.getWriter().println("success");

        }else {//验证失败
            System.out.println("失败------------------------------------------");
            orderService.payFailed(out_trade_no,1,trade_no);
            response.getWriter().println("fail");
            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        //——请在这里编写您的程序（以上代码仅作参考）——
    }

    /*支付宝页面跳转同步通知页面*/
    @RequestMapping(value ="/return",method = RequestMethod.GET)
    public void callBack(HttpServletRequest request,HttpServletResponse response) throws Exception {
        /* *
         * 功能：支付宝服务器同步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
         */

        //获取支付宝GET过来反馈信息
        System.out.println("同步------------------------------------------");
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        System.out.println(alipayConfig.getAlipayPublicKey());
        System.out.println(alipayConfig.getCharset());
        System.out.println(alipayConfig.getSignType());
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(),
                alipayConfig.getCharset(),alipayConfig.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        System.out.println("----------------------------"+signVerified);
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            ItripHotelOrder itripHotelOrder = orderService.loadItripHotelOrder(out_trade_no);
            //提示支付成功
            response.sendRedirect(String.format(alipayConfig.getPaymentSuccessUrl(),out_trade_no,itripHotelOrder.getId()));
        }else {
            response.sendRedirect(String.format(alipayConfig.getPaymentFailUrl()));
        }
        //——请在这里编写您的程序（以上代码仅作参考）——

    }
}
