package com.luo90.campaign.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;

import com.luo90.campaign.entity.CampaignNodeInfo;

public class CampaignCache {
	private JdbcTemplate jdbcTemplate;
	public CampaignCache(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	public List<CampaignNodeInfo> getCampaignNodeInfoByNode(String node){
		List<Map> list = (List)jdbcTemplate.queryForList("select id, name, node_id, add_time, start_time, end_time, expression, result, remark, status from campaign_node_info where status='1' and node_id in(select id from campaign_node where avi_flag='1' and node =?)",node);
		List<CampaignNodeInfo> infoList = new ArrayList<CampaignNodeInfo>();
		for(Map map:list){
			CampaignNodeInfo info = new CampaignNodeInfo();
			info.setId((Integer)map.get("id"));
			info.setName((String)map.get("name"));
			info.setNodeId((Integer)map.get("node_id"));
			info.setAddTime((Date)map.get("add_time"));
			info.setStartTime((Date)map.get("start_time"));
			info.setEndTime((Date)map.get("end_time"));
			info.setExpression((String)map.get("expression"));
			info.setResult((String)map.get("result"));
			info.setRemark((String)map.get("remark"));
			info.setStatus((String)map.get("status"));
			infoList.add(info);
		}
		return infoList;
	}
}
