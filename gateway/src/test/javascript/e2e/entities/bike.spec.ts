import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Bike e2e test', () => {

    let navBarPage: NavBarPage;
    let bikeDialogPage: BikeDialogPage;
    let bikeComponentsPage: BikeComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Bikes', () => {
        navBarPage.goToEntity('bike');
        bikeComponentsPage = new BikeComponentsPage();
        expect(bikeComponentsPage.getTitle())
            .toMatch(/Bikes/);

    });

    it('should load create Bike dialog', () => {
        bikeComponentsPage.clickOnCreateButton();
        bikeDialogPage = new BikeDialogPage();
        expect(bikeDialogPage.getModalTitle())
            .toMatch(/Create or edit a Bike/);
        bikeDialogPage.close();
    });

    it('should create and save Bikes', () => {
        bikeComponentsPage.clickOnCreateButton();
        bikeDialogPage.setModelInput('model');
        expect(bikeDialogPage.getModelInput()).toMatch('model');
        bikeDialogPage.setPriceInput('5');
        expect(bikeDialogPage.getPriceInput()).toMatch('5');
        bikeDialogPage.setManufacturerInput('manufacturer');
        expect(bikeDialogPage.getManufacturerInput()).toMatch('manufacturer');
        bikeDialogPage.save();
        expect(bikeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BikeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-bike div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class BikeDialogPage {
    modalTitle = element(by.css('h4#myBikeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    modelInput = element(by.css('input#field_model'));
    priceInput = element(by.css('input#field_price'));
    manufacturerInput = element(by.css('input#field_manufacturer'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setModelInput = function(model) {
        this.modelInput.sendKeys(model);
    };

    getModelInput = function() {
        return this.modelInput.getAttribute('value');
    };

    setPriceInput = function(price) {
        this.priceInput.sendKeys(price);
    };

    getPriceInput = function() {
        return this.priceInput.getAttribute('value');
    };

    setManufacturerInput = function(manufacturer) {
        this.manufacturerInput.sendKeys(manufacturer);
    };

    getManufacturerInput = function() {
        return this.manufacturerInput.getAttribute('value');
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
