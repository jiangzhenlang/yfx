package Utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yifengxin.entity.ResultVO;

/**
 * 参数 解析
 * @author huangpeiyuan
 * @date 2018年3月7日
 */
public class WebUtil {
	/**
	 * 从web请求中获取参数
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> getParam(HttpServletRequest request) throws UnsupportedEncodingException {
		HashMap<String,Object> map=new HashMap<String, Object>();
		String param=request.getParameter("param");
		if(!StringUtils.isEmpty(param)) {
			map=JSON.parseObject(param, HashMap.class);
		}
		return map;
	}
	
	/** 
     * 获取当前网络ip 
     * @param request 
     * @return 
     */  
    public static String getIpAddr(HttpServletRequest request){  
        String ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
            return ipAddress;   
    }
    
    /**
     * 获取随机数
     * @param min
     * @param max
     * @return
     */
    public static String getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);

    }
	
    public static String getR9() {
    	return getRandom(0, 99999);
    }
    
    
    /**  
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。  
     * @return  
     */  
    public static String getUUID() {   
        return UUID.randomUUID().toString().replace("-", "");  
    }
    
	/**
	 * 调用成功，返回该对象
	 * @param data
	 * @return
	 */
	public static ResultVO getSuccess(Object data) {
		ResultVO ResultVO=new ResultVO();
		ResultVO.setCode(0);
		ResultVO.setMsg("");
		ResultVO.setData(data);
		return ResultVO;
	}
	
	/**
	 * 出现错误时，返回该对象
	 * @param msg
	 * @return
	 */
	public static ResultVO getError(String msg) {
		ResultVO ResultVO=new ResultVO();
		ResultVO.setCode(1);
		ResultVO.setMsg(msg);
		ResultVO.setData("");
		return ResultVO;
	}
	
}
