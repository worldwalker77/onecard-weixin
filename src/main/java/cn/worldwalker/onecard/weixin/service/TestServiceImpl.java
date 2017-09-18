package cn.worldwalker.onecard.weixin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.worldwalker.onecard.weixin.dao.TestDao;
import cn.worldwalker.onecard.weixin.domain.TestModel;
@Service
public class TestServiceImpl implements TestService{
	@Autowired
	private TestDao testDao;
	@Override
	public List<TestModel> getTest() {
		
		List<TestModel> list = testDao.test(new HashMap<String, Object>());
		return list;
	}
	
}
