package com.kgc.config;

public class AlipayConfig {
	// 商户appid
	private  String appId ;
	// 私钥 pkcs8格式的
	private  String rsaPrivateKey;
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	private  String notify_url;
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	private  String return_url;
	// 请求网关地址
	private  String url;
	// 编码
	private  String charset;
	// 返回格式
	private  String format;
	// 支付宝公钥
	private  String alipayPrivateKey;
	// 日志记录目录
	private  String logPath;
	// RSA2
	private  String signType;

	private String paymentSuccessUrl;

	private String paymentFailUrl;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	public void setRsaPrivateKey(String rsaPrivateKey) {
		this.rsaPrivateKey = rsaPrivateKey;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAlipayPrivateKey() {
		return alipayPrivateKey;
	}

	public void setAlipayPrivateKey(String alipauPrivateKey) {
		this.alipayPrivateKey = alipauPrivateKey;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaymentSuccessUrl() {
		return paymentSuccessUrl;
	}

	public void setPaymentSuccessUrl(String paymentSuccessUrl) {
		this.paymentSuccessUrl = paymentSuccessUrl;
	}

	public String getPaymentFailUrl() {
		return paymentFailUrl;
	}

	public void setPaymentFailUrl(String paymentFailUrl) {
		this.paymentFailUrl = paymentFailUrl;
	}
}
