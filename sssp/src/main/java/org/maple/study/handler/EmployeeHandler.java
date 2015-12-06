package org.maple.study.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.maple.study.entity.Employee;
import org.maple.study.service.DepartmentService;
import org.maple.study.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by AUROGON on 2015/12/6.
 */
@Controller
public class EmployeeHandler {

    Logger log = Logger.getLogger(EmployeeHandler.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 分页显示数据
     * @param pageNoStr
     * @param map
     * @return
     */
    @RequestMapping("/emps")
    public String list(@RequestParam(value = "pageNo", required = false, defaultValue = "1") String pageNoStr,
                       Map<String, Object> map) {
        int pageNo = 1;
        try {
            log.info(String.format("Current page: %s", pageNoStr));
            pageNo = Integer.parseInt(pageNoStr);
            if (pageNo < 1) {
                pageNo = 1;
            }
        } catch (NumberFormatException e) {
        }

        Page<Employee> page = employeeService.getPage(pageNo, 5);
        map.put("page", page);
        return "emp/list";
    }

    /**
     * 准备添加页面的数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.GET)
    public String input(Map<String, Object> map) {
        map.put("departments", departmentService.getAllCached());
        map.put("employee", new Employee());
        return "emp/input";
    }

    /**
     * 添加数据
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    public String save(Employee employee) {
        employeeService.save(employee);
        return "redirect:/emps";
    }

    /**
     * 回显数据的时候需要调用，否则不能显示 Department 列表
     * 更新的时候需要设置 Department 为空
     * @param id
     * @param map
     */
    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) Integer id, Map<String, Object> map) {
        log.info(String.format("ModelAttribute method getEmployee: %s", id));
        if (id != null) {
            Employee employee = employeeService.get(id);
            employee.setDepartment(null);   // 必须加上此句，否则会更改 Department 的 ID， 而造成不能更新
            map.put("employee", employee);
        }
    }

    /**
     * 更新数据
     * @param employee
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.PUT)
    public String update(Employee employee) {
        employeeService.save(employee);
        return "redirect:/emps";
    }

    /**
     * Ajax 验证用户名
     * 插入与更新都需要
     */
    @ResponseBody
    @RequestMapping(value = "/ajaxValidateLastName", method = RequestMethod.POST)
    public String validateLastName(@RequestParam(value = "lastName", required = true) String lastName) {
        log.info(String.format("Validate Last Name: %s", lastName));
        Employee employee = employeeService.getByLastName(lastName);
        if (employee == null) {
            return "0";
        }
        return "1";
    }

    /**
     * 回显数据
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    public String input(@PathVariable("id") Integer id, Map<String, Object> map) {
        Employee employee = employeeService.get(id);
        map.put("employee", employee);
        map.put("departments", departmentService.getAllCached());
        return "emp/input";
    }

    /**
     * 删除记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return "redirect:/emps";
    }
}
