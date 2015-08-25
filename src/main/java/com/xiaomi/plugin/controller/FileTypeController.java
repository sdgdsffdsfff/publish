package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.Constant;
import com.xiaomi.plugin.bean.PageResults;
import com.xiaomi.plugin.exception.SameVersionException;
import com.xiaomi.plugin.model.FileList;
import com.xiaomi.plugin.model.FileType;
import com.xiaomi.plugin.model.User;
import com.xiaomi.plugin.service.FileTypeService;
import com.xiaomi.plugin.service.TypeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 处理文件上传
 * Created by lijie on 2015-06-11.
 */
@Controller
@RequestMapping(value = "/filetype")
public class FileTypeController extends BaseController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private FileTypeService fileTypeService;


    /**
     * 所有的上传管理
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public ModelAndView appList(HttpServletRequest request) {

        ModelMap modelMap = new ModelMap();

        String tid = request.getParameter("tid");//二级栏目
        modelMap.put("tid", tid);

        String fid = request.getParameter("fid");//三级栏目
        if (fid == null) {
            fid = Constant.APP_SOFT_UPGRADE;
        }
        modelMap.put("fid", fid);


        String searchFileValue = getSearchFiledValue();
        modelMap.put("search", searchFileValue);


        if (StringUtils.isNotEmpty(searchFileValue)) {
            String hql = "from FileType t where t.sid=? and t.style=? and t.name=?";

            List<FileType> list = fileTypeService.getListByHQL(hql, tid, fid,searchFileValue);

            modelMap.put("filetype", list);

            modelMap.put("pagecount", list.size());

        } else {

            String hql = "from FileType where sid=? and style=?";

            PageResults<FileType> fileTypePageResults = fileTypeService.findPageByFetchedHql(hql, null, getPagenumber(), getPagesize(), tid, fid);

            modelMap.put("filetype", fileTypePageResults.getResults());

            modelMap.put("pagenumber", getPagenumber());
            modelMap.put("pagecount", fileTypePageResults.getPagecount());

        }

        if ("zzyw".equals(tid)) {
            return new ModelAndView("zzyw_file", modelMap);
        }

        return new ModelAndView("yyrj_file", modelMap);
    }

    /**
     * 应用软件上传 添加
     *
     * @param fileList
     * @param request
     * @return
     */
    @RequestMapping(value = "/addappsoft")
    @ResponseBody
    public Map addappsoft(FileList fileList, HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("success", "t");

        //设置上传用户
        fileList.setUser((User) request.getSession().getAttribute(Constant.CURRENT_USER));

        String oldFileTypeId = fileList.getFileTypeId();

        String name = request.getParameter("name");//当前软件名称
        String fid = request.getParameter("fid");//style
        String tid = request.getParameter("tid");//sid

        try {
            FileType fileType = fileTypeService.saveFile(fileList, name, tid, fid);
            //如果是更新软件 更新了软件的类型 更新原来类型 如果类型下面没有软件就删除
            if (oldFileTypeId != null && !"".equals(oldFileTypeId) && !(fileType.getId().toString().equals(oldFileTypeId))) {
                FileType oldFileType = fileTypeService.get(Integer.valueOf(oldFileTypeId));
                if (oldFileType.getFileListSet().size() < 1) {
                    fileTypeService.delete(oldFileType);
                }
            }
        } catch (SameVersionException e) {
            map.put("success", "f");
            map.put("msg", e.getMessage());
        }

        return map;
    }


    @RequestMapping(value = "/deleteappsoft")
    public String deleteappsoft(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        String fid = request.getParameter("fid");//当前软件属于哪个类别
        String tid = request.getParameter("tid");//当前软件属于哪个栏目
        fileTypeService.deleteFile(ids, tid, fid);
        return "redirect:/filetype/list?tid=" + tid + "&fid=" + fid;
    }

    @RequestMapping(value = "/ajax")
    @ResponseBody
    public String get(String name) {
        String hql = "from FileType where name=?";
        FileType fileType = fileTypeService.getByHQL(hql, name);
        if (fileType != null) {
            Set<FileList> fileListSet = fileType.getFileListSet();
            if (fileListSet.size() > 0) {
                FileList fileList = fileListSet.iterator().next();
                return fileList.getPlus();
            }
        }
        return null;
    }

}
