import grails.plugin.geb.GebTests

import pages.*
import org.junit.Test

class PersonCRUDTests extends GebTests {

	@Test
	void doSomeCrud() {
		to ListPage
		assert personRows.size() == 0
		newPersonButton.click()
		
		assert at(CreatePage)
		enabled = true 
		firstName = "Luke"
		lastName = "Daley"
		createButton.click()
		
		assert at(ShowPage)
		assert enabled.text() == "True"
		assert firstName.text() == "Luke"
		assert lastName.text() == "Daley"
		assert id.text().number
		editButton.click()
		
		assert at(EditPage)
		enabled = false
		updateButton.click()
		
		assert at(ShowPage)
		
		to ListPage
		assert personRows.size() == 1
		def row = personRow(0)
		assert row.firstName.text() == "Luke"
		assert row.lastName.text() == "Daley"
		row.showLink.click()
		
		assert at(ShowPage)
		def id = id.text()
		withConfirm { deleteButton.click() }
		
		assert at(ListPage)
		assert message.text() == "Person $id deleted"
		assert personRows.size() == 0
	}
}