package org.springframework.samples.petclinic.web;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.springframework.samples.petclinic.web.OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

    }

    @AfterEach
    void tearDown() {
        reset(clinicService); // enusures new
    }

    @Test
    void initCreationForm() throws Exception {

        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processFindForm() throws Exception {
        mockMvc.perform(get("/owners")
                .param("lastName", "Dont find ME!")) //lastname is bound to owner::: remember json only see fields
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void processFindFormReturnList() throws Exception{
        given(clinicService.findOwnerByLastName("")).willReturn(Arrays.asList(new Owner(), new Owner()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));

        //testing the argument of no param (lastName =null)
        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
    }

    @Test
    void processFindFormOneResult() throws Exception{
        final String oneData = "oneData";

        Owner owner = new Owner();
        owner.setId(1);
        owner.setLastName(oneData);
        given(clinicService.findOwnerByLastName(oneData)).willReturn(List.of(owner));

        mockMvc.perform(get("/owners")
                        .param("lastName", oneData))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        //testing the argument
        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase(oneData);
    }

    @Test
    void processCreationForm() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Jimmy")
                .param("lastName", "Buffett")
                .param("address", "123 Duval St")
                .param("city", "Key West")
                .param("telephone", "315315315"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/null"));
    }

    @Test
    void processCreationFormPostNotValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffett")
                        .param("city", "Key West"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address", "telephone"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));


    }

    @Test
    void processUpdateOwnerForm() throws Exception{
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffett")
                        .param("address", "123 Duval St")
                        .param("city", "Key West")
                        .param("telephone", "315315315"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));

    }

    @Test
    void processUpdateOwnerFormInvalidOwner() throws Exception{
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                        .param("firstName", "Jimmy")
                        .param("address", "123 Duval St")
                        .param("telephone", "315315315"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "lastName", "city"))
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM));

    }
}