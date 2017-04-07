package com.alipay.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.config.AlipayConfig;

public class AlipayApiUtil {
	
	/**
	 * 根据请求参数，生成提交到alipay的表单，返回表单字符串，
	 * @param request
	 * @return
	 */
	public static String generateSubmitFormToAlipay(HttpServletRequest request) throws Exception{
		//商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //订单名称，必填
        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
        //付款金额，必填 （格式如：1.00,请精确到分）
        String total_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"),"UTF-8");
        //商品描述，可空
        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
        
		return generateSubmitFormToAlipay(out_trade_no,subject,total_fee,body);
	}
	
	/**
	 * 根据参数生成提交的表单字符串
	 * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填
	 * @param subject 订单名称，必填
	 * @param total_fee 付款金额，必填 （格式如：1.00,请精确到分）
	 * @param body 商品描述，可空
	 * @return
	 */
	public static String generateSubmitFormToAlipay(String out_trade_no,String subject,String total_fee,String body){
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", AlipayConfig.notify_url);
		sParaTemp.put("return_url", AlipayConfig.return_url);
		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		//其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.O9yorI&treeId=62&articleId=103740&docType=1
        //如sParaTemp.put("参数名","参数值");
		
		//生成请求表单
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		return sHtmlText;
	}
	
	/**
	 *  从request获取支付需要的参数，生成表单并返回请求
	 * @param request 需包含：<br>
	 * 	1）out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填<br>
	 * 	2）subject 订单名称，必填<br>
	 * 	3）total_fee 付款金额，必填 （格式如：1.00,请精确到分）<br>
	 * 	4）body 商品描述，可空<br>
	 * @param response
	 * @throws Exception
	 */
	public static void sendToAlipay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String submitFormHtmlText = generateSubmitFormToAlipay(request);
		PrintWriter writer = response.getWriter();
		writer.write(submitFormHtmlText);
		writer.flush();
		writer.close();
		
	}
	/**
	 * 根据支付参数，生成表单并返回请求
	 * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填
	 * @param subject 订单名称，必填
	 * @param total_fee 付款金额，必填 （格式如：1.00,请精确到分）
	 * @param body 商品描述，可空
	 * @param response
	 * @throws Exception
	 */
	public static void sendToAlipay(String out_trade_no,String subject,String total_fee,String body,HttpServletResponse response) throws Exception{
		String submitFormHtmlText =generateSubmitFormToAlipay(out_trade_no,subject,total_fee,body);
		PrintWriter writer = response.getWriter();
		writer.write(submitFormHtmlText);
		writer.flush();
		writer.close();
		
		
	}
	
	/**
	 *  
	 * @param params 该参数需包含：
	 *  1）out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填<br>
	 * 	2）subject 订单名称，必填<br>
	 * 	3）total_fee 付款金额，必填 （格式如：1.00,请精确到分）<br>
	 * 	4）body 商品描述，可空<br>
	 * @param response
	 * @throws Exception
	 */
	public static void sendToAlipay(Map<String,String> params,HttpServletResponse response) throws Exception{
		String submitFormHtmlText =generateSubmitFormToAlipay(params.get("out_trade_no"),params.get("subject"),params.get("total_fee"),params.get("body"));
		PrintWriter writer = response.getWriter();
		writer.write(submitFormHtmlText);
		writer.flush();
		writer.close();
		
		
	}

}
