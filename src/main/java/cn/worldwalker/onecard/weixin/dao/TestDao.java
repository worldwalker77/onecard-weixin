package cn.worldwalker.onecard.weixin.dao;

import java.util.List;
import java.util.Map;

import cn.worldwalker.onecard.weixin.domain.TestModel;

public interface TestDao {
	
	public List<TestModel> test(Map<String, Object> map);
	
}
