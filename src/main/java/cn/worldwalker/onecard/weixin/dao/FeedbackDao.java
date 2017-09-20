package cn.worldwalker.onecard.weixin.dao;

import java.util.List;

import cn.worldwalker.onecard.weixin.domain.FeedBackModel;

public interface FeedbackDao {
	
	public List<FeedBackModel> selectFeedbacks(FeedBackModel model);
	
	public FeedBackModel selectFeedbackDetail(Long id);
	
	public Integer insertFeedback(FeedBackModel model);
}
