package com.netcorner.test.model.entity;
import com.netcorner.sqlmapper.entity.Entity;
import com.netcorner.sqlmapper.entity.Table;
import java.util.Date;

/**
 * Created by netcorner.
 * 111
 */
@Table("Jobmate.b")
public class B extends Entity<B> {


    
                




        private Integer id;
        /**
         * 得到 AA
         * @return
         */
        public Integer getid() {
            return id;
        }
        /**
         * 设置 AA
         * @param _id
         */
        public void setid(Integer _id) {
            this.id = _id;
        }
    
                




        private String a;
        /**
         * 得到 测试字段 
         * @return
         */
        public String geta() {
            return a;
        }
        /**
         * 设置 测试字段 
         * @param _a
         */
        public void seta(String _a) {
            this.a = _a;
        }
    
                




        private Integer c;
        /**
         * 得到 xx哈
         * @return
         */
        public Integer getc() {
            return c;
        }
        /**
         * 设置 xx哈
         * @param _c
         */
        public void setc(Integer _c) {
            this.c = _c;
        }
    }
