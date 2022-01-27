package com.tunbor.beye.controller;

//import com.tunbor.beye.service.UserService;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//import static org.mockito.Mockito.when;
//import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Olakunle Awotunbo
 */

@WebMvcTest(TestController.class)
public class TestControllerTest extends AbstractControllerTest {

    private static final String CONTROLLER_PATH = "/test";

//    @MockBean
//    private UserService userService;

    @Test
    public void testMe() throws Exception {
        this.mockMvc.perform(get(CONTROLLER_PATH + "/testMe").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("This is a test"));
    }
}
