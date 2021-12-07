package com.example.zuccbiosphere;

import com.example.zuccbiosphere.dto.postInfoDto;
import com.example.zuccbiosphere.filter.getHoursFilter;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcSimpleDatasourceApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    public void springDataSourceTest(){
        //输出为true
        System.out.println(dataSource instanceof HikariDataSource);
        List<postInfoDto> postInfoList = new ArrayList<>();

        try{
            Connection conn = dataSource.getConnection();
            String sql = "select * from post";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                postInfoDto post = new postInfoDto();
                post.setUserID(rs.getString(2));
                post.setPostTheme(rs.getString(3));
                post.setPostContent(rs.getString(4));
                post.setPostDate(rs.getDate(5) + " " + rs.getTime(5));
                post.setPostDate(getHoursFilter.getTimeDiff(post.postDate));
                post.setPostLikeNum(rs.getInt(6));
                post.setPostCommentNum(rs.getInt(7));
                post.setPostUserAvatarUrl(rs.getString(8));
                post.setPostReportNum(rs.getInt(9));
                post.setPostIsTop(rs.getBoolean(10));
                post.setPostIsEssential(rs.getBoolean(11));
                postInfoList.add(post);
            }
            for(postInfoDto p : postInfoList){
                System.out.println(p.postDate);
            }
            pst.close();
            conn.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
