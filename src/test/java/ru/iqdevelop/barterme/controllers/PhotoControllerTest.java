//package ru.iqdevelop.barterme.controllers;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import ru.iqdevelop.barterme.configuration.AppConfig;
//import ru.iqdevelop.barterme.configuration.WebConfig;
//import ru.iqdevelop.barterme.services.PhotoService;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes=WebConfig.class, loader=AnnotationConfigContextLoader.class)
//public class PhotoControllerTest {
//
//    private MockMvc mvc;
//
//    @Mock
//    private PhotoService photoService;
//
//    @InjectMocks
//    private PhotoController photoController;
//
//    @Before
//    public void init(){
//        MockitoAnnotations.initMocks(this);
//        mvc = MockMvcBuilders
//                .standaloneSetup(photoService)
//                .build();
//    }
//
//    @Test
//    public void uploadPhoto() throws Exception {
//        mvc.perform(post("/photos/upload")).andExpect(status().isOk());
//    }
//
//}