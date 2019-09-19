package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.Binding;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService ownerService;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController ownerController;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                   List<Owner> ownerList = new ArrayList<>();
                    String name = invocation.getArgument(0);

                   if(name.equals("%Beck%")){
                       ownerList.add(new Owner(1L, "John", "Beck"));
                       return ownerList;
                   } else if(name.equals("%NotFound%")){
                       return ownerList;
                   } else if(name.equals("%Found%")){
                       ownerList.add(new Owner(2L, "Mitch", "Zubes"));
                       ownerList.add(new Owner(3L, "Bob", "Traves"));
                       return ownerList;
                   }
                    throw new RuntimeException("Invalid Argument");
                });
    }
    @Test
    void processFindFormWildcardFound() {
        Owner owner = new Owner(1L, "John", "Found");
        InOrder inOrder = inOrder(ownerService, model);

        String viewName = ownerController.processFindForm(owner, bindingResult, model);

        assertThat("%Found%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void processFindFormWildcardString() {
        Owner owner = new Owner(1L, "John", "Beck");

        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        assertThat("%Beck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
        verifyZeroInteractions(model);
    }
    @Test
    void processFindFormWildcardNotFound() {
        Owner owner = new Owner(1L, "Joe", "NotFound");

        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        verifyNoMoreInteractions(ownerService);
        assertThat("%NotFound%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
    }

    @Test
    @DisplayName("Binding Result Has Errors")
    void processCreationFormWithErrors() {
        //given
        Owner owner = new Owner(1L, "John", "Beck");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        String viewName = ownerController.processCreationForm(owner, bindingResult);
        // then
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    @DisplayName("Binding Result Has NO Errors")
    void processCreationFormNoErrors() {
        //given
        Owner owner = new Owner(5L, "John", "Beck");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);
        //when
        String viewName = ownerController.processCreationForm(owner, bindingResult);
        // then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }
}