package com.example.zuccbiosphere.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.ZuccBiosphereApplication;
import com.example.zuccbiosphere.dto.postInfoDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.xml.ws.Action;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class postBarControllerTest {


//    @Autowired
//    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

//    @Before
//    public void setup(){
////        mockMvc = MockMvcBuilders.standaloneSetup(new postBarController()).build();
//        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//    }

    @Test
    void testloadPost() throws Exception {
        JSONObject data = new JSONObject();
        data.put("user_ID","a4384c8b488baf812739acc730a24957");
        System.out.println(data.toJSONString());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/postBar/loadPost")
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data.toJSONString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("output" + mvcResult.getResponse().getContentAsString());

    }

    @Test
    void loadPostDetail() throws Exception {
        JSONObject data = new JSONObject();
        data.put("post_ID","80");
        System.out.println(data.toJSONString());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/postBar/loadPostDetail")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data.toJSONString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("output" + mvcResult.getResponse().getContentAsString());

    }

    @Test
    void loadPostComment() throws Exception {
        JSONObject data = new JSONObject();
        data.put("post_ID","80");
        System.out.println(data.toJSONString());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/postBar/loadPostComment")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data.toJSONString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("output" + mvcResult.getResponse().getContentAsString());

    }




    @Test
    void loadHotPost() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/postBar/loadHotPost")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("user_ID","a4384c8b488baf812739acc730a24957")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("output" + mvcResult.getResponse().getContentAsString());
    }


//    @Test
//    void loadMyPost() throws Exception {
//        JSONObject data = new JSONObject();
//        data.put("user_ID","a4384c8b488baf812739acc730a24957");
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/postBar/loadMyPost")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .header("x-auth-token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mzk0Nzc4NzQsImlhdCI6MTYzOTQ3NDI3NCwidXNlcm5hbWUiOiJhNDM4NGM4YjQ4OGJhZjgxMjczOWFjYzczMGEyNDk1NyJ9.E8TpTA0L156QRzEYxNYB_5Kf9-1tgaN5XvfEXg8THZ8")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(data.toJSONString())
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print()).andReturn();
//        System.out.println("output" + mvcResult.getResponse().getContentAsString());
//
//    }

    @Test
    void releasePost() {

    }

    @Test
    void modifyLike() {
    }

    @Test
    void pushComment() {
    }

    @Test
    void getAllIsLike() {
    }

    /*protected void sendRequest(String rurl,int waitSeconds) throws Exception{
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(rurl);
        HttpResponse response = client.execute(httpget);
        client.getConnectionManager().shutdown();

        Thread.sleep(waitSeconds*1000);
    }*/
}