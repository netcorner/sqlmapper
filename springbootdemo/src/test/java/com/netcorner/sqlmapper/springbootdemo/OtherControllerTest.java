package com.netcorner.sqlmapper.springbootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class OtherControllerTest extends AbstractSpringMvcTest {


  @Test
  public void testAdd() throws Exception {
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

    MvcResult ret = mockMvc.perform(
        post("/testOtherMutile1")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            //.content(paramsJson)
            .params(parameters)
    ).andReturn();

    System.out.println(ret.getResponse().getContentAsString());
  }

}
