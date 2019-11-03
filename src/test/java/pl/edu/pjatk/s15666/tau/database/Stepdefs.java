package pl.edu.pjatk.s15666.tau.database;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pl.edu.pjatk.s15666.tau.domain.Sensor;

import java.util.ArrayList;
import java.util.List;

public class Stepdefs {

    private DbObject object;
    private List<DbObjectHolder> db = new ArrayList<>();
    private DbAccess dbAccess = new DbAccess(db, new DbLocalDateTimeProvider());

    @Given("object is created")
    public void object_is_created() {
        object = new Sensor("", false);
    }

    @When("object is added to the database")
    public void object_is_added_to_the_database() throws NotEnoughSpaceException {
        dbAccess.create(object);
    }

    @Then("object can be read from the database")
    public void object_can_be_read_from_the_database() {
        Assert.assertEquals(object, dbAccess.readAll().get(0).getDbObject());
    }

    @Then("object can be removed from the database")
    public void object_can_be_removed_from_the_database() throws NotFoundException {
        dbAccess.delete(object);
        Assert.assertTrue(dbAccess.readAll().isEmpty());
    }

}
