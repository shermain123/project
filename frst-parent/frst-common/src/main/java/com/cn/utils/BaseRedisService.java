package com.cn.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component //将工具类注入到spring容器
public class BaseRedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;  
	}  
	
	//设置过期时间
	public void expire(String key,Long timeOut){  
        if(timeOut != null){  
            redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);  
        }  
    }  
	
	//
	/**
	 * 通用存放支持（String,List,Map,无序Set）
	 * @param key 键
	 * @param data 值
	 * */
	public boolean set(String key,Object data){
		
		return set(key,data,null);
	}
	/**通用存放支持（String,List,Map,无序Set）
	 * @param key 键
	 * @param data 值
	 * @param timeOut 过期时间
	 * */
	public boolean set(String key,Object data,Long timeOut){
		try{
			if(data instanceof String){
				String value = (String) data;
				stringRedisTemplate.opsForValue().set(key, value);
				if(timeOut != null){
					stringRedisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
				}
			}
			if(data instanceof Map){
				Map<String,Object> map = (Map<String,Object>) data;
				redisTemplate.opsForHash().putAll(key, map);
				expire(key,timeOut);
			}
			if(data instanceof List){
				redisTemplate.opsForList().rightPushAll(key, data);
				//redisTemplate.opsForList().rightPush(key, data);
				expire(key,timeOut);
			}
			if(data instanceof Object[]){
				redisTemplate.opsForSet().add(key, data);
				expire(key,timeOut);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取缓存
	 * @param key 键
	 * */
	public String getStr(String key){
		return key == null ? null : stringRedisTemplate.opsForValue().get(key);
	}
	
	//====================List========================
	
	/**
	 * 获取List缓存长度
	 * @return long类型
	 * */
	public long lgetListSize(String key){
		return redisTemplate.opsForList().size(key);
	}
	
	/**
	 * 通过索引获取List
	 * @param key 键
	 * @param index 索引(long类型)
	 * */
	public Object getIndex(String key, long index){
		try{
			return redisTemplate.opsForList().index(key, index);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取List缓存内容
	 * @param key 键
	 * @param start 开始(long)
	 * @param end 结束 0到-1代表所有的值 (long)
	 * */
	public Object getList(String key, long start, long end){
		try{
			return redisTemplate.opsForList().range(key, start, end);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/** 
     * 根据索引修改list中的某条数据 
     * @param key 键 
     * @param index 索引 
     * @param value 值 
     * @return 
     */  
    public boolean lUpdateIndex(String key, long index,Object value) {  
        try {  
            redisTemplate.opsForList().set(key, index, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }   

    /** 
     * 移除N个值为value  
     * @param key 键 
     * @param count 移除多少个 
     * @param value 值 
     * @return 移除的个数 
     */  
    public long lRemove(String key,long count,Object value) {  
        try {  
            Long remove = redisTemplate.opsForList().remove(key, count, value);  
            return remove;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
	
	//===================Map======================
	/**
	 * 获取Map所有键值
	 * */
	public Object getMap(String key){
		return redisTemplate.opsForHash().entries(key);
	}
	
	/**
	 * 通过键和项获取值
	 * @param key 键
	 * @param item Map项
	 * */
	public Object getMapValue(String key, String item){
		return redisTemplate.opsForHash().get(key, item);
	}
	
	/** 
     * 删除hash表中的值 
     * @param key 键 不能为null 
     * @param item 项 可以使多个 不能为null 
     */  
    public void delMap(String key, Object... item){    
        redisTemplate.opsForHash().delete(key,item);  
    }   
	
	//====================Set===========================
	/**
	 * 获取Set
	 * @param key 键
	 * */
	public Set<String> sGet(String key){
		try{
			return stringRedisTemplate.opsForSet().members(key);
			//return redisTemplate.opsForSet().members(key);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/** 
	 * 这个方法感觉有问题（不过一般用不到）
     * 根据value从一个set中查询,是否存在 
     * @param key 键 
     * @param value 值 
     * @return true 存在 false不存在 
     */  
   /* public boolean sHasKey(String key,Object value){  
        try {  
            return redisTemplate.opsForSet().isMember(key, value);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  */
    
    /** 
     * 获取set缓存的长度 
     * @param key 键 
     * @return 
     */  
    public long sGetSetSize(String key){  
        try {  
            return redisTemplate.opsForSet().size(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
    
    /** 
     * 移除值为value的 
     * @param key 键 
     * @param values 值 可以是多个 
     * @return 移除的个数 
     */  
    public long setRemove(String key, Object ...values) {  
        try {  
            Long count = redisTemplate.opsForSet().remove(key, values);  
            return count;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return 0;  
        }  
    }  
    
    //=============================common============================  
    /** 
     * 指定缓存失效时间 
     * @param key 键 
     * @param time 时间(秒) 
     * @return 
     */  
    public boolean expire(String key,long time){  
        try {  
            if(time>0){  
                redisTemplate.expire(key, time, TimeUnit.SECONDS);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /** 
     * 根据key 获取过期时间 
     * @param key 键 不能为null 
     * @return 时间(秒) 返回0代表为永久有效 
     */  
    public long getExpire(String key){  
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);  
    }  

    /** 
     * 判断key是否存在 
     * @param key 键 
     * @return true 存在 false不存在 
     */  
    public boolean hasKey(String key){  
        try {  
            return redisTemplate.hasKey(key);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  

    /**
     * 删除String类型缓存
     * @param key 键 类型(String)
     * */
    public void delete(String key){
    	if(key !=null ){
    		redisTemplate.opsForValue().getOperations().delete(key);
    	}
    }
    
    /** 
     * 删除缓存 
     * @param key 可以传一个值 或多个类型 数组 
     */  
    @SuppressWarnings("unchecked")  
    public void del(String ... key){  
        if(key!=null&&key.length>0){  
            if(key.length==1){  
                redisTemplate.delete(key[0]);  
            }else{  
                redisTemplate.delete(CollectionUtils.arrayToList(key));  
            }  
        }  
    }  

}
