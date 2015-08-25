package com.xiaomi.plugin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.plugin.bean.PageResults;
import com.xiaomi.plugin.model.Agent;
import com.xiaomi.plugin.service.AgentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 代理商管理
 * Created by lijie on 2015-06-12.
 */
@Controller
@RequestMapping(value = "/agent")
public class AgentController extends BaseController {
    @Autowired
    private AgentService agentService;

    @RequestMapping(value = "/get")
    @ResponseBody
    public String get() {
        String hql = "from Agent";
        List<Agent> list = agentService.getListByHQL(hql);
        String commonAgentJson = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            commonAgentJson = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return commonAgentJson;
    }

    @RequestMapping(value = "/list")
    public ModelAndView list() {

        ModelMap modelMap = new ModelMap();

        String searchFileValue = getSearchFiledValue();

        String hql = "from Agent order by orderNo";

        if (StringUtils.isNotEmpty(searchFileValue)) {
            hql = "from Agent where text like '%" + searchFileValue + "%' or value like '%"+searchFileValue+"%' order by orderNo";
        }

        PageResults<Agent> agentPageResults = agentService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize());

        modelMap.put("agentlist", agentPageResults.getResults());

        modelMap.put("pagenumber", getPagenumber());
        modelMap.put("pagecount", agentPageResults.getPagecount());

        return new ModelAndView("agent", modelMap);
    }

    @RequestMapping(value = "/add")
    public String add(Agent agent) {
        Agent entity;
        if (agent.getId() != null) {
            entity = agentService.get(agent.getId());
            entity.setText(agent.getText());
            entity.setValue(agent.getValue());
            entity.setOrderNo(agent.getOrderNo());
        } else {
            entity = agent;
        }
        agentService.saveOrUpdate(entity);
        return "redirect:/agent/list";
    }

    @RequestMapping(value = "/delete")
    public String delete(String ids) {
        List<Integer> idsList = new ArrayList<Integer>();
        for (String id : ids.split(",")) {
            if (StringUtils.isNotEmpty(id)) {
                idsList.add(Integer.valueOf(id));
            }
        }
        agentService.deleteByIds(idsList);
        return "redirect:/agent/list";
    }


}
