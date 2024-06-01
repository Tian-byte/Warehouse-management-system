package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.User;
import com.wms.service.imp.UserService;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tian
 * @since 2024-05-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMOd(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    //查询（模糊,匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(user.getName())){
            lambdaQueryWrapper.like(User::getName, user.getName());
        }
       return Result.success(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
//    public List<User> listPage(@RequestBody HashMap map){
    public List<User> listPage(@RequestBody QueryPageParam query) {
        //System.out.println(query);
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        // System.out.println("num====" + query.getPageNum() + " "+ "size ===" + query.getPageSize());
//        HashMap param = query.getParam();
//        System.out.println("name===" + (String) param.get("name"));
//        System.out.println("no===" + (String) param.get("no"));
        Page<User> page = new Page(1, 2);
        page.setPages(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total=== "+result.getTotal());
        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query) {
        //System.out.println(query);
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        Page<User> page = new Page(1, 2);
        page.setPages(query.getPageNum());
        page.setSize(query.getPageSize());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);
       //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page, lambdaQueryWrapper);
        System.out.println("total=== "+result.getTotal());
        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        //System.out.println(query);
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        Page<User> page = new Page();
        page.setPages(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex, sex);
        }

        //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page, lambdaQueryWrapper);
        System.out.println("total=== "+result.getTotal());
        return Result.success(result.getRecords(),result.getTotal());
    }
}
