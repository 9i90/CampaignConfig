package com.luo90.campaign.aop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.wltea.expression.ExpressionEvaluator;
import org.wltea.expression.datameta.Variable;
import com.luo90.campaign.cache.CampaignCache;
import com.luo90.campaign.entity.CampaignNodeInfo;

@Aspect
public class CampaignAspect {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("进入方法---环绕通知"); 
		
		Object o = pjp.proceed();

		String nodeName = pjp.getSignature().getName();
		System.out.println("节点名称："+nodeName);
		// 获取活动配置
		Object[] obj = pjp.getArgs();
		System.out.println("param:" + obj);

		CampaignCache campaign = new CampaignCache(jdbcTemplate);
		List<CampaignNodeInfo> list = campaign
				.getCampaignNodeInfoByNode(nodeName);
		System.out.println("取得活动："+list.size());
		for (CampaignNodeInfo info : list) {
			List<Variable> variables = new ArrayList<Variable>();
//			variables.add(Variable.createVariable("userId", obj[0]));
			// 执行表达式
			Object result = ExpressionEvaluator.evaluate(info.getExpression(), variables);
			System.out.println("Result = " + result+";info.getResult():"+info.getResult());
			if(result.toString().equals(info.getResult())){
				System.out.println("---------达到预期，执行活动---------");
				jdbcTemplate.update("insert into confignode_info(node_code,node_name,addtime,remark)values(?,?,?,?)", nodeName,info.getName(),new Date(),result);
			}else{
				System.out.println("---------未达到预期，不执行活动---------");
			}
		}

		System.out.println("退出方法---环绕通知");
		return o;
	}
}
