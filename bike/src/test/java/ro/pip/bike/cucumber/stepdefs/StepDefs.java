package ro.pip.bike.cucumber.stepdefs;

import ro.pip.bike.BikeApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = BikeApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
