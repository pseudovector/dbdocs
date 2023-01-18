package com.pseudovector.dbdocs.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pseudovector.dbdocs.service.DataSourceDetailService;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * @ClassName：TestControler
 * @Description：生成数据库表设计文档
 * @Author：lv617
 * @Date：2018/9/4 11:29
 * @Version：1.0
 **/
@Slf4j
@RestController
@RequestMapping("/myTest")
public class DataSourceDetailControler {

    @Autowired
    private DataSourceDetailService dataSourceDetailService;

    /**
     *  描述：生成数据库表设计文档
     *
     *@创建人  lv617
     *@创建时间  2018/9/4 16:52
     */
    @RequestMapping("/getDbDetail")
    public String getDbDetail(List<String> dbNames){
        try {
            String fileName = "./output/dbDetail_" + Timestamp.from(Instant.now()).getTime() + ".doc";
            String title = "数据库表设计文档";
            Path filePath = Paths.get(getClass().getClassLoader().getResource("output").toURI()).resolve(fileName);
            this.dataSourceDetailService.generateDoc(filePath, title, dbNames);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("生成数据库表设计文档失败", e); 
            }
            return "生成数据库表设计文档失败";
        }
        return "生成数据库表设计文档成功";
    }
}
