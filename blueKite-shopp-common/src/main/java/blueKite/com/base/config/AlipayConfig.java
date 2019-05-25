package blueKite.com.base.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016092900622944";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCEVVDnZcnEkycbOs1tXfPZ17LPe3dgW+b7Ll8Mu+xnn4w1ztpEiDa13bcnB0TQHvi72hvKMet/fH6QXIBZ2pA1s05sCBPmcV+gKmGB2TQqOjr4gevEsSM5qk/JB4Xuy/69snlIX34qWlvp1g+ZgaGduBnRBtAkQxjWi1jXD9fE+LuDwiO7xs8FAHSmjnpDQX/HOE104YdJGkKZtIyGWQJeo/eF6oRx8EIXYvwOCpGA7+3ejfT0xdQZ5OVj+Cx50cnUn4aDHwAl4AZG2Wp9VWAbVB2MIkDfTHSwrDJK0k70dcXGitrMqv2ufPJpkjzLoZN1Rpvg8xH1nKxZtX5Lafr/AgMBAAECggEAEOvH2I7Pae9XBMhUFDP7RlOqbZVgAlY+Bsg7JymwZN+l0guNJU41kLO1A6yOQ/5x17Nf8k7HyhllJZhuYQ7tebBfyriNst20WIqSxUO8w9mGSNh0CUaNNoZshUwM+CflfuQfVf2/6DJ9pjzU6dAFlmFAC9ZPLxPVTTmsNnO0J19nl8nfEXwNOVj10jgVTSsCMz32HZk1dy2FJIKgKcowYPD/jZcUOz2j6up6UxI2DnWziQqBz9fCZEq3+CqGUMfoe/kN4EDFJsI5X3rKuZYqrgbwdhRgKsDeAU5CiEbUi4qspRyliB5rLH7Y2QVT3o5pIgvS2aQCQdV58zbV3k5h4QKBgQDCFHujwY+DArbe/5T0QWVCpQ4srqwMVHEvDPRjTl8eERIxOMF6tpDe5aJcDF+T41l61f1mvJQd7sUqfMlu1CIkyvuPmDNagEMTHE4oGZJGSkD8yfS54fq+0qhZB023M8vlxWGIfTx61l60kBCZ446VdMt84pXmft8CZ6c1WpGvkwKBgQCujahKmPmgpdc8G50JNQ0DofnxAlmOfsXIVmFwUujXIedL54kHN5alSQTFt7khhUc0oT81T7refdEHL+9eHIO83pSiMjOzGlQ9SXRXZVLLe7uPUIKhCzfIIVY2qny/RYOfP1HPSwi+Yv4vb0B6leOW+hj4dQ0DNITZdWRmWucyZQKBgDtYmBYS8hdVlSXLcXjfJl+iqRrwrpt2lsWUaIaVSHChN7blbARiHxqJjYQoJHHMa2gsVzg7Ie+lO5UqY04IgAhQKWxXVhyua9REK4IHj7r+qbCi8LqGZ25CbEgM+66xn/+6w4BBC2Vkd8Eu7GSiOM9qz254uu2ojfjmSTL/Zt0RAoGBAI1HcpMORd1U3WR1JSEyoxnxnA8Jy9qdmQ+ResQjFvopIsA44pvIzlFxTD+LeHg9G2jpqfNIWQOD6g9bKO6PPcE8lijBYT7xbIU+Ur/57o1r1F8m1tPp9HgzI0iTrOxTIzldUqXpIWaXUQyrixCtCMxGGLfgZWvWskAf1+dQZBhRAoGATSWGhjlgUiiv5m2CYGseUxJXm7e8RJNp0YilfRbBohR+IgiIfKdRvCgtjAwopYiq4iBOuaDAkBdP3Zvrf6RCE1vD2K/EV5XvTFvXGx9aNnk+xCe4fm404gO1Ekiajxulok0ZrOtCv++tPB9aJG7/nb6fvtrIHZoLP+b23TdIe6k=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4Ds1HIDVbWtm+fLYUB60JBykghDbwx1w2uAl6lFPlkNKT0sk0N89xLKZfgkdkTEHLQDmPqrkzClEEYjpbUOhsXcaOUQ5tTZrsq3o8W0U7Rcc9iybk11ah2mWgI+i3qnHbmiXnB3m32EixOJ68urhe0y6E1+UimWRtcU1zm77bEPa3qJFSH5r5+yBxXtZ11hE4MHrPRXosT9C53CaKC9TO2kHLMdaAbGyDVTsSnWpBJKGL+pGDlrAtBM749ty4X+MAbkAxQbHKnOy5NRw3ApkNW8oV1Y229hVjzKG13U4MHmPygkW4UPt3wC39HKlS4jJR6hJ4NY5hbv+Sei9cHhvrQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://bluekite.natapp1.cc/alipayAsyncCallBack";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://bluekite.natapp1.cc/alipaySynCallBack";

	//post请求封装回调参数
	public static String returnBackUrl = "http://bluekite.natapp1.cc/alipayReturnBack";
	
	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

